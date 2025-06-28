package ru.practicum.service;

import org.apache.avro.specific.SpecificRecordBase;

public interface KafkaProducerService {

    void sendHub(String key, SpecificRecordBase recordBase);

    void sendSensor(String key, SpecificRecordBase recordBase);
}
