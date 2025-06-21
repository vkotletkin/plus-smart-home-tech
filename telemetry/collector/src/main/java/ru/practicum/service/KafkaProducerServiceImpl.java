package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.practicum.dto.hub.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;
import ru.practicum.service.factory.AvroMessagesFactory;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl {

    private final Producer<String, SpecificRecordBase> producer;

    public void send(String topic, String key, HubEvent hubEvent) {
        SpecificRecordBase hubEventAvro = AvroMessagesFactory.createAvroHubEvent(hubEvent);
        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, key, hubEventAvro);
        producer.send(record);
    }

    public void send(String topic, String key, SensorEvent sensorEvent) {

    }
}
