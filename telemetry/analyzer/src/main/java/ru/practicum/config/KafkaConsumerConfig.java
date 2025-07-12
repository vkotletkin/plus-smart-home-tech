package ru.practicum.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaConfig kafkaConfig;

    @Bean
    public KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer() {

        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getTelemetry().getBootstrap().getServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "analyzer-snapshot-consumer-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "ru.practicum.serialization.SensorsSnapshotDeserializer");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        return new KafkaConsumer<>(config);
    }

    @Bean
    public KafkaConsumer<String, HubEventAvro> hubEventConsumer() {

        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getTelemetry().getBootstrap().getServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "analyzer-hub-consumer-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "ru.practicum.serialization.HubEventDeserializer");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        return new KafkaConsumer<>(config);
    }
}
