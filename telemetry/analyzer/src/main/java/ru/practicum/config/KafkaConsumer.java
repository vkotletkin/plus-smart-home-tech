package ru.practicum.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaConfig kafkaConfig;

    @Bean
    public org.apache.kafka.clients.consumer.KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer() {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getTelemetry().getBootstrap().getServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "analyzer-snapshot-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "ru.practicum.serialization.SensorsSnapshotDeserializer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        return new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
    }

    @Bean
    public org.apache.kafka.clients.consumer.KafkaConsumer<String, HubEventAvro> hubEventConsumer() {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getTelemetry().getBootstrap().getServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "analyzer-hub-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "ru.practicum.serialization.HubEventDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        return new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
    }
}
