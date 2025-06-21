package ru.practicum.event.dto.sensor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.event.model.SensorEventType;

@Getter
@Setter
@ToString(callSuper = true)
public class TemperatureSensorEvent extends SensorEvent {

    private int temperatuerC;
    private int temperatuerF;

    @Override
    public SensorEventType getType() {
        return null;
    }
}
