package ru.practicum.service;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

public interface HubEventHandler {

    HubEventProto.PayloadCase getMessageType();

    SpecificRecordBase getAvroRecord(HubEventProto event);
}
