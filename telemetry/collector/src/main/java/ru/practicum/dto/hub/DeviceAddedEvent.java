package ru.practicum.dto.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.model.hub.HubEventType;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;

@Getter
@Setter
@ToString(callSuper = true)
public final class DeviceAddedEvent extends HubEvent {

    String id;
    DeviceTypeAvro deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}