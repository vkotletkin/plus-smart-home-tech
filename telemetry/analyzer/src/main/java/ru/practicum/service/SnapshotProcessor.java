package ru.practicum.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.handler.SnapshotHandler;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnapshotProcessor implements Runnable {
    private final SnapshotHandler snapshotHandler;
    private KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer;
    private volatile boolean running = true;

    @Value("${kafka.topics.snapshots}")
    private String snapshotsTopic;

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${kafka.consumer.group-id.snapshots:analyzer.snapshots}")
    private String snapshotsGroupId;

    @PostConstruct
    public void start() {
        new Thread(this::run, "SnapshotProcessorThread").start();
        log.info("Started SnapshotProcessor thread");
    }

    public void run() {
        try {
            // Инициализация KafkaConsumer
            Properties props = new Properties();
            props.put("bootstrap.servers", bootstrapServers);
            props.put("group.id", snapshotsGroupId);
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "ru.practicum.serialization.SensorsSnapshotDeserializer");
            props.put("auto.offset.reset", "earliest");
            props.put("enable.auto.commit", "false");
            snapshotConsumer = new KafkaConsumer<>(props);

            snapshotConsumer.subscribe(List.of(snapshotsTopic));
            log.info("Subscribed to snapshots topic: {}", snapshotsTopic);

            while (running) {
                try {
                    ConsumerRecords<String, SensorsSnapshotAvro> records = snapshotConsumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
                        try {
                            SensorsSnapshotAvro snapshot = record.value();
                            if (snapshot == null) {
                                log.warn("Null snapshot received at offset {} in topic {}", record.offset(), snapshotsTopic);
                                continue;
                            }
                            log.info("Processing snapshot: hubId={}, offset={}", snapshot.getHubId(), record.offset());
                            snapshotHandler.handle(snapshot);
                        } catch (Exception e) {
                            log.error("Failed to process snapshot at offset {} in topic {}: {}", record.offset(), snapshotsTopic, e.getMessage(), e);
                            snapshotConsumer.seek(new TopicPartition(record.topic(), record.partition()), record.offset() + 1);
                        }
                    }
                    snapshotConsumer.commitSync();
                } catch (Exception e) {
                    log.error("Error polling snapshots: {}", e.getMessage(), e);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Fatal error in SnapshotProcessor: {}", e.getMessage(), e);
        } finally {
            shutdown();
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down SnapshotProcessor");
        running = false;
        if (snapshotConsumer != null) {
            try {
                snapshotConsumer.wakeup();
                snapshotConsumer.close(Duration.ofSeconds(5));
                log.info("Snapshot consumer closed");
            } catch (Exception e) {
                log.error("Error closing snapshot consumer: {}", e.getMessage(), e);
            }
        }
    }
}
