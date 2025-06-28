package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.practicum.config.KafkaConfig;
import ru.practicum.service.KafkaProducerService;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaConfig kafkaConfig;
    private final Producer<String, SpecificRecordBase> producer;

    public void send(String key, SpecificRecordBase recordData) {
        ProducerRecord<String, SpecificRecordBase> sendRecord = new ProducerRecord<>(
                kafkaConfig.getHubTopic(), key, recordData);
        producer.send(sendRecord);
    }
}