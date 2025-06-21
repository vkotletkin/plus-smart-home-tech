package ru.practicum.dto.sensor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.model.sensor.SensorEventType;

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
