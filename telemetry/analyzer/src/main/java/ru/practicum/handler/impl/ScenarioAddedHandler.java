package ru.practicum.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.practicum.model.Action;
import ru.practicum.model.Condition;
import ru.practicum.model.Scenario;
import ru.practicum.repository.ActionRepository;
import ru.practicum.repository.ConditionRepository;
import ru.practicum.repository.ScenarioRepository;
import ru.practicum.repository.SensorRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScenarioAddedHandler implements HubEventHandler {

    private final ScenarioRepository scenarioRepository;
    private final SensorRepository sensorRepository;
    private final ActionRepository actionRepository;
    private final ConditionRepository conditionRepository;

    @Override
    @Transactional
    public void handle(HubEventAvro hubEventAvro) {

        ScenarioAddedEventAvro scenarioAddedEvent = (ScenarioAddedEventAvro) hubEventAvro.getPayload();

        Scenario scenario = findOrCreateScenario(hubEventAvro, scenarioAddedEvent);

        processActions(hubEventAvro, scenarioAddedEvent, scenario);
        processConditions(hubEventAvro, scenarioAddedEvent, scenario);
    }

    public Scenario findOrCreateScenario(HubEventAvro hubEvent, ScenarioAddedEventAvro scenarioAddedEvent) {
        return scenarioRepository.findByHubIdAndName(hubEvent.getHubId(), scenarioAddedEvent.getName())
                .orElseGet(() -> createNewScenario(hubEvent, scenarioAddedEvent));
    }

    public Scenario createNewScenario(HubEventAvro hubEvent, ScenarioAddedEventAvro scenarioAddedEvent) {
        Scenario newScenario = Scenario.builder()
                .hubId(hubEvent.getHubId())
                .name(scenarioAddedEvent.getName())
                .build();
        return scenarioRepository.save(newScenario);
    }

    public void processActions(HubEventAvro hubEvent, ScenarioAddedEventAvro scenarioAddedEvent, Scenario scenario) {
        List<String> actionSensorIds = extractActionSensorIds(scenarioAddedEvent);

        if (sensorsExist(actionSensorIds, hubEvent.getHubId())) {
            List<Action> actions = createActions(scenarioAddedEvent.getActions(), scenario);
            actionRepository.saveAll(actions);
            log.info("Saved {} actions for scenario {}", actions.size(), scenario.getName());
        }
    }

    public List<String> extractActionSensorIds(ScenarioAddedEventAvro scenarioAddedEvent) {
        return scenarioAddedEvent.getActions().stream()
                .map(DeviceActionAvro::getSensorId)
                .toList();
    }

    public List<Action> createActions(List<DeviceActionAvro> actionAvros, Scenario scenario) {
        return actionAvros.stream()
                .map(action -> buildAction(action, scenario))
                .toList();
    }

    public Action buildAction(DeviceActionAvro actionAvro, Scenario scenario) {
        return Action.builder()
                .sensor(sensorRepository.findById(actionAvro.getSensorId()).orElseThrow())
                .scenario(scenario)
                .type(actionAvro.getType())
                .value(actionAvro.getValue() != null ? actionAvro.getValue() : 0)
                .build();
    }

    public void processConditions(HubEventAvro hubEvent, ScenarioAddedEventAvro scenarioAddedEvent, Scenario scenario) {
        List<String> conditionSensorIds = extractConditionSensorIds(scenarioAddedEvent);

        if (sensorsExist(conditionSensorIds, hubEvent.getHubId())) {
            List<Condition> conditions = createConditions(scenarioAddedEvent.getConditions(), scenario);
            conditionRepository.saveAll(conditions);
            log.info("Saved {} conditions for scenario {}", conditions.size(), scenario.getName());
        }
    }

    public List<String> extractConditionSensorIds(ScenarioAddedEventAvro scenarioAddedEvent) {
        return scenarioAddedEvent.getConditions().stream()
                .map(ScenarioConditionAvro::getSensorId)
                .toList();
    }

    public List<Condition> createConditions(List<ScenarioConditionAvro> conditionAvros, Scenario scenario) {
        return conditionAvros.stream()
                .map(condition -> buildCondition(condition, scenario))
                .toList();
    }

    private Condition buildCondition(ScenarioConditionAvro conditionAvro, Scenario scenario) {
        return Condition.builder()
                .sensor(sensorRepository.findById(conditionAvro.getSensorId()).orElseThrow())
                .scenario(scenario)
                .type(conditionAvro.getType())
                .operation(conditionAvro.getOperation())
                .value(mapValue(conditionAvro.getValue()))
                .build();
    }

    private boolean sensorsExist(List<String> sensorIds, String hubId) {
        return sensorRepository.existsByIdInAndHubId(sensorIds, hubId);
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