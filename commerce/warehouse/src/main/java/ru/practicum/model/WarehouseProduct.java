package ru.practicum.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Entity
@Table(name = "warehouse_products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "fragile")
    boolean fragile;

    @Column(name = "width")
    double width;

    @Column(name = "height")
    double height;

    @Column(name = "depth")
    double depth;

    @Column(name = "weight")
    double weight;

    @Column(name = "quantity")
    long quantity;
}
