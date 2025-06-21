package ru.practicum.service.factory;

import org.apache.avro.specific.SpecificRecordBase;
import ru.practicum.dto.hub.*;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

public class AvroMessagesFactory {

    public static SpecificRecordBase createAvroHubEvent(HubEvent event) {
        return switch (event.getType()) {
            case DEVICE_ADDED -> {
                DeviceAddedEvent deviceAddedEvent = (DeviceAddedEvent) event;
                yield DeviceAddedEventAvro.newBuilder()
                        .setId(deviceAddedEvent.getId())
                        .setType(deviceAddedEvent.getDeviceType())
                        .build();
            }
            case DEVICE_REMOVED -> {
                DeviceRemovedEvent deviceRemovedEvent = (DeviceRemovedEvent) event;
                yield DeviceRemovedEventAvro.newBuilder()
                        .setId(deviceRemovedEvent.getId())
                        .build();
            }
            case SCENARIO_ADDED -> {
                ScenarioAddedEvent scenarioAddedEvent = (ScenarioAddedEvent) event;
                yield ScenarioAddedEventAvro.newBuilder()
                        .setName(scenarioAddedEvent.getName())
                        .setActions(scenarioAddedEvent.getDeviceActions())
                        .setConditions(scenarioAddedEvent.getScenarioConditions())
                        .build();
            }
            case SCENARIO_REMOVED -> {
                ScenarioRemovedEvent scenarioRemovedEvent = (ScenarioRemovedEvent) event;
                yield ScenarioRemovedEventAvro.newBuilder()
                        .setName(scenarioRemovedEvent.getName())
                        .build();
            }
        };
    }


}
