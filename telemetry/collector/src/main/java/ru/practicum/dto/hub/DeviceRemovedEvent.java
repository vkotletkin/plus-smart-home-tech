package ru.practicum.dto.hub;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.hub.HubEventType;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceRemovedEvent extends HubEvent {

    private String id;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}