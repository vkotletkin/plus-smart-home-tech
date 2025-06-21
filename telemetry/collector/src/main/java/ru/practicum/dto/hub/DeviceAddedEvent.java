package ru.practicum.dto.hub;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.hub.HubEventType;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class DeviceAddedEvent extends HubEvent {

    @NotBlank
    String id;

    DeviceTypeAvro deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}