package ru.practicum.handler;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventHandler {

    void handle(HubEventAvro hubEventAvro);

    String getTypeOfPayload();
}
