package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.practicum.config.KafkaConfig;
import ru.practicum.dto.hub.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;
import ru.practicum.service.KafkaProducerService;
import ru.practicum.service.factory.AvroMessagesFactory;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaConfig kafkaConfig;
    private final Producer<String, SpecificRecordBase> producer;

    public void send(String topic, String key, HubEvent hubEvent) {
        SpecificRecordBase hubEventAvro = AvroMessagesFactory.createAvroHubEvent(hubEvent);
        ProducerRecord<String, SpecificRecordBase> sendRecord = new ProducerRecord<>(
                kafkaConfig.getHubTopic(), key, hubEventAvro);
        producer.send(sendRecord);
    }

    public void send(String topic, String key, SensorEvent sensorEvent) {
        SpecificRecordBase sensorEventAvro = AvroMessagesFactory.createAvroSensorEvent(sensorEvent);
        ProducerRecord<String, SpecificRecordBase> sendRecord = new ProducerRecord<>(
                kafkaConfig.getSensorTopic(), key, sensorEventAvro);
        producer.send(sendRecord);
    }
}