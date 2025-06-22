package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.config.KafkaConfig;
import ru.practicum.dto.hub.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;
import ru.practicum.service.EventService;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final KafkaConfig kafkaConfig;
    private final KafkaProducerServiceImpl producerService;

    @Override
    public void collectHubEvent(HubEvent hubEvent) {
        producerService.send(kafkaConfig.getHubTopic(), hubEvent.getHubId(), hubEvent);
    }

    @Override
    public void collectSensorEvent(SensorEvent sensorEvent) {
        producerService.send(kafkaConfig.getSensorTopic(), sensorEvent.getHubId(), sensorEvent);
    }
}
