package ru.practicum.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Service;
import ru.practicum.config.KafkaConfig;
import ru.practicum.handler.SnapshotHandler;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnapshotProcessor implements Runnable {

    private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);
    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    private final SnapshotHandler snapshotHandler;

    private final KafkaConfig kafkaConfig;
    private final KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer;

    private static void manageOffsets(ConsumerRecord<String, SensorsSnapshotAvro> polledRecord, int count, KafkaConsumer<String, SensorsSnapshotAvro> consumer) {

        currentOffsets.put(
                new TopicPartition(polledRecord.topic(), polledRecord.partition()),
                new OffsetAndMetadata(polledRecord.offset() + 1)
        );

        if (count % 10 == 0) {
            consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                if (exception != null) {
                    log.warn("Error in offset fixing: {}", offsets, exception);
                }
            });
        }
    }

    @PostConstruct
    public void start() {
        new Thread(this, "SnapshotProcessorThread").start();
        log.info("Started SnapshotProcessor thread");
    }

    @PreDestroy
    public void stop() {
        snapshotConsumer.commitSync();
        snapshotConsumer.close();
    }

    public void run() {

        try {

            Runtime.getRuntime().addShutdownHook(new Thread(snapshotConsumer::wakeup));
            snapshotConsumer.subscribe(List.of(kafkaConfig.getSnapshotTopic()));

            while (true) {

                ConsumerRecords<String, SensorsSnapshotAvro> records = snapshotConsumer.poll(CONSUME_ATTEMPT_TIMEOUT);

                if (records.isEmpty()) {
                    continue;
                }

                int count = 0;
                for (ConsumerRecord<String, SensorsSnapshotAvro> polledRecord : records) {

                    SensorsSnapshotAvro snapshot = polledRecord.value();

                    log.info("Processing Snapshot Event: hubId={}, offset={}", snapshot.getHubId(), polledRecord.offset());
                    snapshotHandler.handle(snapshot);

                    manageOffsets(polledRecord, count, snapshotConsumer);
                    count++;
                }

                snapshotConsumer.commitAsync();
            }
        } catch (WakeupException ignored) {
            log.info("Snapshot Consumer received wakeup signal");
        } catch (Exception e) {
            log.error("Error in processing events from snapshots", e);
        } finally {
            snapshotConsumer.close();
            log.info("Snapshot Consumer is Closed");
        }
    }
}