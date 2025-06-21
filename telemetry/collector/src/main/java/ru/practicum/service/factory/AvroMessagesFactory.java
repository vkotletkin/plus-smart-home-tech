package ru.practicum.service.factory;

import org.apache.avro.specific.SpecificRecordBase;
import ru.practicum.dto.hub.*;
import ru.practicum.dto.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

public class AvroMessagesFactory {

    public static SpecificRecordBase createAvroHubEvent(HubEvent event) {
        return switch (event) {
            case DeviceAddedEvent e -> DeviceAddedEventAvro.newBuilder()
                    .setId(e.getId())
                    .setType(e.getDeviceType())
                    .build();
            case DeviceRemovedEvent e -> DeviceRemovedEventAvro.newBuilder()
                    .setId(e.getId())
                    .build();
            case ScenarioAddedEvent e -> ScenarioAddedEventAvro.newBuilder()
                    .setName(e.getName())
                    .setActions(e.getDeviceActions())
                    .setConditions(e.getScenarioConditions())
                    .build();
            case ScenarioRemovedEvent e -> ScenarioRemovedEventAvro.newBuilder()
                    .setName(e.getName())
                    .build();
        };
    }

    public static SpecificRecordBase createAvroSensorEvent(SensorEvent event) {
        return switch (event) {
            case ClimateSensorEvent e -> ClimateSensorAvro.newBuilder()
                    .setTemperatureC(e.getTemperatureC())
                    .setHumidity(e.getHumidity())
                    .setCo2Level(e.getCo2Level())
                    .build();
            case LightSensorEvent e -> LightSensorAvro.newBuilder()
                    .setLinkQuality(e.getLinkQuality())
                    .setLuminosity(e.getLuminosity())
                    .build();
            case MotionSensorEvent e -> MotionSensorAvro.newBuilder()
                    .setLinkQuality(e.getLinkQuality())
                    .setMotion(e.isMotion())
                    .setVoltage(e.getVoltage())
                    .build();
            case SwitchSensorEvent e -> SwitchSensorAvro.newBuilder()
                    .setState(e.isState())
                    .build();
            case TemperatureSensorEvent e -> TemperatureSensorAvro.newBuilder()
                    .setTimestamp(e.getTimestamp())
                    .setTemperatureC(e.getTemperatuerC())
                    .setTemperatureF(e.getTemperatuerF())
                    .build();
        };
    }
}