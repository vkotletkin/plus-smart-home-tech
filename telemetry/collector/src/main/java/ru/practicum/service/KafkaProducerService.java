package ru.practicum.service;

import org.apache.avro.specific.SpecificRecordBase;

public interface KafkaProducerService {

    void send(String key, SpecificRecordBase recordBase);
}
