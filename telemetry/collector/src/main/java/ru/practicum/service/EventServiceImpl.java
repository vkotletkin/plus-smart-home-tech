package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.stereotype.Service;
import ru.practicum.dto.hub.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Override
    public void collectHubEvent(HubEvent hubEvent) {
    }

    @Override
    public void collectSensorEvent(SensorEvent sensorEvent) {

    }
}
