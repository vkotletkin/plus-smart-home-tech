package ru.practicum.config;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public Producer<String, SpecificRecordBase> getKafkaProducer() {
        Properties config = new Properties();
        config.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        config.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "ru.practicum.serialization.GeneralAvroSerializer");
        return new KafkaProducer<>(config);
    }
}
