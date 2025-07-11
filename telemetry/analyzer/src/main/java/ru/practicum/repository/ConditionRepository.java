package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Condition;
import ru.practicum.model.Scenario;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
    List<Condition> findAllByScenario(Scenario scenario);

    void deleteByScenario(Scenario scenario);
}
