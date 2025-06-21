package ru.practicum.service;

import ru.practicum.dto.hub.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;

public interface KafkaProducerService {

    void send(String topic, String key, SensorEvent sensorEvent);

    void send(String topic, String key, HubEvent hubEvent);
}
