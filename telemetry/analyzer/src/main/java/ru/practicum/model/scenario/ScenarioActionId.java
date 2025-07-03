package ru.practicum.model.scenario;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioActionId implements Serializable {
    Long scenario;
    String sensor;
    Long action;
}