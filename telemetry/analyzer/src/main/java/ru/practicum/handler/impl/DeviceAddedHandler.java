package ru.practicum.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.HubEventHandler;
import ru.practicum.model.Sensor;
import ru.practicum.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceAddedHandler implements HubEventHandler {

    private final SensorRepository sensorRepository;

    @Override
    @Transactional
    public void handle(HubEventAvro hubEventAvro) {

        DeviceAddedEventAvro deviceAddedEventAvro = (DeviceAddedEventAvro) hubEventAvro.getPayload();

        if (sensorRepository.existsByIdInAndHubId(List.of(deviceAddedEventAvro.getId()), hubEventAvro.getHubId())) {
            log.info("Device with id: {} already added to hub with id: {}", deviceAddedEventAvro.getId(), hubEventAvro.getHubId());
        } else {
            Sensor sensor = Sensor.builder()
                    .id(deviceAddedEventAvro.getId())
                    .hubId(hubEventAvro.getHubId())
                    .build();
            sensorRepository.save(sensor);
        }
    }

    @Override
    public String getTypeOfPayload() {
        // todo: checks
        return DeviceAddedEventAvro.class.getSimpleName();
    }
}
