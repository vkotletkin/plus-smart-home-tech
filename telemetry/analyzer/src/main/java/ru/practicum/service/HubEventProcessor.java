package ru.practicum.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.practicum.config.KafkaConfig;
import ru.practicum.handler.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubEventProcessor implements Runnable {

    private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);
    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    private final Set<HubEventHandler> handlers;

    private final KafkaConfig kafkaConfig;
    private final KafkaConsumer<String, HubEventAvro> hubEventConsumer;

    private static void manageOffsets(ConsumerRecord<String, HubEventAvro> polledRecord, int count, KafkaConsumer<String, HubEventAvro> consumer) {

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
        new Thread(this, "HubEventHandlerThread").start();
        log.info("Started HubEventProcessor thread");
    }

    @Override
    public void run() {

        try {

            Map<String, HubEventHandler> handlerMap = handlers.stream()
                    .collect(Collectors.toMap(HubEventHandler::getTypeOfPayload, Function.identity()));

            Runtime.getRuntime().addShutdownHook(new Thread(hubEventConsumer::wakeup));
            hubEventConsumer.subscribe(List.of(kafkaConfig.getHubTopic()));

            while (true) {

                ConsumerRecords<String, HubEventAvro> records = hubEventConsumer.poll(CONSUME_ATTEMPT_TIMEOUT);

                if (records.isEmpty()) {
                    continue;
                }

                int count = 0;
                for (ConsumerRecord<String, HubEventAvro> polledRecord : records) {

                    HubEventAvro event = polledRecord.value();
                    String eventType = event.getPayload().getClass().getSimpleName();

                    log.info("Processing Hub Event: hubId={}, type={}, offset={}", event.getHubId(), eventType, polledRecord.offset());

                    if (handlerMap.containsKey(eventType)) {
                        handlerMap.get(eventType).handle(event);
                    } else {
                        log.warn("No handler founded for event type: {}", eventType);
                    }

                    manageOffsets(polledRecord, count, hubEventConsumer);
                    count++;
                }

                hubEventConsumer.commitAsync();
            }
        } catch (WakeupException ignored) {
            log.info("Hub Event Consumer received wakeup signal");
        } catch (Exception e) {
            log.error("Error in processing events from hubs", e);
        } finally {
            hubEventConsumer.close();
            log.info("Hub Event Consumer is Closed");
        }
    }
}