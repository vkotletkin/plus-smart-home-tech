package ru.practicum.dto.sensor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.model.sensor.SensorEventType;

@Getter
@Setter
@ToString(callSuper = true)
public class MotionSensorEvent extends SensorEvent {

    private int linkQuality;
    private boolean motion;
    private int voltage;

    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}
