package ru.practicum.service;

import ru.practicum.dto.hub.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;

public interface EventService {

    void collectHubEvent(HubEvent hubEvent);

    void collectSensorEvent(SensorEvent sensorEvent);
}
