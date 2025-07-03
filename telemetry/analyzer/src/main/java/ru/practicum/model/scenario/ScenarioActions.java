package ru.practicum.model.scenario;

import jakarta.persistence.*;
import ru.practicum.model.Action;
import ru.practicum.model.Sensor;

@Entity
@Table(name = "scenario_actions")
@IdClass(ScenarioActionId.class)
public class ScenarioActions {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id", referencedColumnName = "id")
    Scenario scenario;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    Sensor sensor;

    @Id
    @ManyToOne
    @JoinColumn(name = "condition_id", referencedColumnName = "id")
    Action action;
}
