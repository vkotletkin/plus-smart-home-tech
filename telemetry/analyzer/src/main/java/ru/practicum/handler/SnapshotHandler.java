package ru.practicum.handler;

import ru.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface SnapshotHandler {

    void handle(SensorsSnapshotAvro sensorsSnapshotAvro);
}
