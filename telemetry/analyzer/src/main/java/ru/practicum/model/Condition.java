package ru.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.enums.ScenarioConditionType;

@Data
@Entity
@Builder
@Table(name = "conditions")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    ScenarioConditionType type;

    @Column(name = "operation")
    @Enumerated(EnumType.STRING)
    ScenarioConditionType operation;

    @Column(name = "value")
    Long value;
}
