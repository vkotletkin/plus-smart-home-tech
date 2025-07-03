package ru.practicum.model.scenario;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.Condition;
import ru.practicum.model.Sensor;

@Entity
@Table(name = "scenario_conditions")
@IdClass(ScenarioConditionId.class)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioConditions {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id", referencedColumnName = "id")
    Scenario scenario;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    Sensor sensor;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id", referencedColumnName = "id")
    Condition condition;
}