package ru.practicum.service.impl.handler.hub;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.practicum.service.HubEventHandler;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;
import java.util.List;

@Component
public class ScenarioAddedEventHandler implements HubEventHandler {

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public SpecificRecordBase getAvroRecord(HubEventProto event) {
        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .setPayload(getAvroPayload(event))
                .build();
    }

    private SpecificRecordBase getAvroPayload(HubEventProto event) {
        ScenarioAddedEventProto scenarioAddedEventAvro = event.getScenarioAdded();
        return ScenarioAddedEventAvro.newBuilder()
                .setName(scenarioAddedEventAvro.getName())
                .setActions(getDeviceActionAvroList(scenarioAddedEventAvro))
                .setConditions(getScenarioConditionAvroList(scenarioAddedEventAvro))
                .build();
    }

    private List<ScenarioConditionAvro> getScenarioConditionAvroList(ScenarioAddedEventProto scenarioAddedEvent) {
        return scenarioAddedEvent.getConditionList().stream()
                .map(c -> ScenarioConditionAvro.newBuilder()
                        .setSensorId(c.getSensorId())
                        .setType(ConditionTypeAvro.valueOf(c.getType().name()))
                        .setOperation(ConditionOperationAvro.valueOf(c.getOperation().name()))
                        .setValue(c.hasBoolValue() ? c.getBoolValue() : c.getIntValue())
                        .build())
                .toList();
    }

    private List<DeviceActionAvro> getDeviceActionAvroList(ScenarioAddedEventProto scenarioAddedEvent) {
        return scenarioAddedEvent.getActionList().stream()
                .map(a -> DeviceActionAvro.newBuilder()
                        .setSensorId(a.getSensorId())
                        .setType(ActionTypeAvro.valueOf(a.getType().name()))
                        .setValue(a.getValue())
                        .build())
                .toList();
    }
}
