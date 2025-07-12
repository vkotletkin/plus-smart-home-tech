package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Action;
import ru.practicum.model.Scenario;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

    List<Action> findAllByScenario(Scenario scenario);

    void deleteByScenario(Scenario scenario);
}
