package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Scenario;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}
