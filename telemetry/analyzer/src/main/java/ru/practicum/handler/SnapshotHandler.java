package ru.practicum.handler;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface SnapshotHandler {

    void handle(SensorsSnapshotAvro sensorsSnapshotAvro);
}
