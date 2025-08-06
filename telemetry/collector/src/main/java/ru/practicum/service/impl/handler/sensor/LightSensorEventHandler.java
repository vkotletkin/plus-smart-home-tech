package ru.practicum.service.impl.handler.sensor;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.practicum.service.SensorEventHandler;
import ru.practicum.grpc.telemetry.event.LightSensorProto;
import ru.practicum.grpc.telemetry.event.SensorEventProto;
import ru.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Instant;

@Component
public class LightSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    public SpecificRecordBase getAvroRecord(SensorEventProto event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .setPayload(getAvroPayload(event))
                .build();
    }

    private SpecificRecordBase getAvroPayload(SensorEventProto event) {
        LightSensorProto lightSensorProto = event.getLightSensorEvent();
        return LightSensorAvro.newBuilder()
                .setLinkQuality(lightSensorProto.getLinkQuality())
                .setLuminosity(lightSensorProto.getLuminosity())
                .build();
    }
}
