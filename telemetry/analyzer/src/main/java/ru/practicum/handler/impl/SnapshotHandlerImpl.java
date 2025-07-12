package ru.practicum.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.handler.SnapshotHandler;
import ru.practicum.model.Action;
import ru.practicum.model.Condition;
import ru.practicum.model.Scenario;
import ru.practicum.repository.ActionRepository;
import ru.practicum.repository.ConditionRepository;
import ru.practicum.repository.ScenarioRepository;
import ru.practicum.service.HubRouterClient;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnapshotHandlerImpl implements SnapshotHandler {

    private final ScenarioRepository scenarioRepository;
    private final ConditionRepository conditionRepository;
    private final ActionRepository actionRepository;
    private final HubRouterClient hubRouterClient;

    @Override
    public void handle(SensorsSnapshotAvro snapshot) {

        Map<String, SensorStateAvro> states = snapshot.getSensorsState();

        List<Scenario> scenarios = scenarioRepository.findByHubId(snapshot.getHubId());
        scenarios.stream()
                .filter(scenario -> checkConditions(scenario, states))
                .forEach(this::executeActions);
    }

    private boolean checkConditions(Scenario scenario, Map<String, SensorStateAvro> states) {
        List<Condition> conditions = conditionRepository.findAllByScenario(scenario);
        return conditions.stream().allMatch(condition -> evaluateCondition(condition, states));
    }

    private boolean evaluateCondition(Condition condition, Map<String, SensorStateAvro> states) {
        SensorStateAvro state = states.get(condition.getSensor().getId());
        if (state == null) {
            return false;
        }

        Integer sensorValue = extractSensorValue(state.getData(), condition.getType());
        if (sensorValue == null) {
            return false;
        }

        return switch (condition.getOperation()) {
            case EQUALS -> sensorValue.equals(condition.getValue());
            case LOWER_THAN -> sensorValue < condition.getValue();
            case GREATER_THAN -> sensorValue > condition.getValue();
        };
    }

    private Integer extractSensorValue(Object data, ConditionTypeAvro type) {
        if (data == null) {
            return null;
        }

        return switch (type) {
            case TEMPERATURE -> {
                if (data instanceof ClimateSensorAvro climate) {
                    yield climate.getTemperatureC();
                }
                yield null;
            }
            case HUMIDITY -> {
                if (data instanceof ClimateSensorAvro climate) {
                    yield climate.getHumidity();
                }
                yield null;
            }
            case CO2LEVEL -> {
                if (data instanceof ClimateSensorAvro climate) {
                    yield climate.getCo2Level();
                }
                yield null;
            }
            case LUMINOSITY -> {
                if (data instanceof LightSensorAvro light) {
                    yield light.getLuminosity();
                }
                yield null;
            }
            case MOTION -> {
                if (data instanceof MotionSensorAvro motion) {
                    yield motion.getMotion() ? 1 : 0;
                }
                yield null;
            }
            case SWITCH -> {
                if (data instanceof SwitchSensorAvro switchSensor) {
                    yield switchSensor.getState() ? 1 : 0;
                }
                yield null;
            }
        };
    }

    private void executeActions(Scenario scenario) {
        List<Action> actions = actionRepository.findAllByScenario(scenario);
        actions.forEach(hubRouterClient::sendAction);
        log.info("Executed {} actions for scenario {}", actions.size(), scenario.getName());
    }
}
