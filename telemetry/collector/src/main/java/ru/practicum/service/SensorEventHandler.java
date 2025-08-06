package ru.practicum.service;

import org.apache.avro.specific.SpecificRecordBase;
import ru.practicum.grpc.telemetry.event.SensorEventProto;

public interface SensorEventHandler {
    SensorEventProto.PayloadCase getMessageType();

    SpecificRecordBase getAvroRecord(SensorEventProto event);
}
