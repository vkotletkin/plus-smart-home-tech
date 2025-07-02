package ru.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.enums.ActionType;

@Data
@Entity
@Builder
@Table(name = "actions")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    ActionType type;

    @Column(name = "value")
    Long value;
}