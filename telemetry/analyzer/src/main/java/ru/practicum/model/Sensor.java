package ru.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@Table(name = "sensors")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sensor {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "hub_id")
    String hubId;
}
