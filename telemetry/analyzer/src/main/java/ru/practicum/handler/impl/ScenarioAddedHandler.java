package ru.practicum.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.handler.HubEventHandler;
import ru.practicum.model.Action;
import ru.practicum.model.Condition;
import ru.practicum.model.Scenario;
import ru.practicum.repository.ActionRepository;
import ru.practicum.repository.ConditionRepository;
import ru.practicum.repository.ScenarioRepository;
import ru.practicum.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScenarioAddedHandler implements HubEventHandler {

    private final ScenarioRepository scenarioRepository;
    private final SensorRepository sensorRepository;
    private final ActionRepository actionRepository;
    private final ConditionRepository conditionRepository;

    @Override
    public void handle(HubEventAvro event) {

        ScenarioAddedEventAvro scenarioAddedEventAvro = (ScenarioAddedEventAvro) event.getPayload();

        Scenario scenario = scenarioRepository.findByHubIdAndName(event.getHubId(), scenarioAddedEventAvro.getName())
                .orElseGet(() -> {
                    Scenario newScenario = Scenario.builder()
                            .hubId(event.getHubId())
                            .name(scenarioAddedEventAvro.getName())
                            .build();
                    return scenarioRepository.save(newScenario);
                });

        List<String> actionSensorIds = scenarioAddedEventAvro.getActions().stream()
                .map(DeviceActionAvro::getSensorId)
                .toList();

        if (sensorRepository.existsByIdInAndHubId(actionSensorIds, event.getHubId())) {
            List<Action> actions = scenarioAddedEventAvro.getActions().stream()
                    .map(action -> Action.builder()
                            .sensor(sensorRepository.findById(action.getSensorId()).orElseThrow())
                            .scenario(scenario)
                            .type(action.getType())
                            .value(action.getValue() != null ? action.getValue() : 0)
                            .build())
                    .collect(Collectors.toList());
            actionRepository.saveAll(actions);
            log.info("Saved {} actions for scenario {}", actions.size(), scenario.getName());
        }

        List<String> conditionSensorIds = scenarioAddedEventAvro.getConditions().stream()
                .map(ScenarioConditionAvro::getSensorId)
                .toList();

        if (sensorRepository.existsByIdInAndHubId(conditionSensorIds, event.getHubId())) {
            List<Condition> conditions = scenarioAddedEventAvro.getConditions().stream()
                    .map(condition -> Condition.builder()
                            .sensor(sensorRepository.findById(condition.getSensorId()).orElseThrow())
                            .scenario(scenario)
                            .type(condition.getType())
                            .operation(condition.getOperation())
                            .value(mapValue(condition.getValue()))
                            .build())
                    .collect(Collectors.toList());
            conditionRepository.saveAll(conditions);
            log.info("Saved {} conditions for scenario {}", conditions.size(), scenario.getName());
        }
    }

    private Integer mapValue(Object value) {
        return switch (value) {
            case null -> throw new IllegalArgumentException("Value cannot be null");
            case Integer integerValue -> integerValue;
            case Boolean booleanValue -> Boolean.TRUE.equals(booleanValue) ? 1 : 0;
            default ->
                    throw new IllegalArgumentException("Unsupported value type: " + value.getClass().getSimpleName());
        };
    }

    @Override
    public String getTypeOfPayload() {
        return ScenarioAddedEventAvro.class.getSimpleName();
    }
}
