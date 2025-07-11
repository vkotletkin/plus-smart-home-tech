package ru.practicum.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.handler.HubEventHandler;
import ru.practicum.repository.ActionRepository;
import ru.practicum.repository.ConditionRepository;
import ru.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioRemovedHandler implements HubEventHandler {
    private final ScenarioRepository scenarioRepository;
    private final ActionRepository actionRepository;
    private final ConditionRepository conditionRepository;

    @Override
    public void handle(HubEventAvro hubEventAvro) {

        ScenarioRemovedEventAvro payload = (ScenarioRemovedEventAvro) hubEventAvro.getPayload();

        scenarioRepository.findByHubIdAndName(hubEventAvro.getHubId(), payload.getName())
                .ifPresent(scenario -> {
                    actionRepository.deleteByScenario(scenario);
                    conditionRepository.deleteByScenario(scenario);
                    scenarioRepository.delete(scenario);
                    log.info("Removed scenario {} for hub {}", payload.getName(), hubEventAvro.getHubId());
                });
    }

    @Override
    public String getTypeOfPayload() {
        return ScenarioRemovedEventAvro.class.getSimpleName();
    }
}
