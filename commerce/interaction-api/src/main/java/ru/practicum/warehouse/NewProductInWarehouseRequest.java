package ru.practicum.warehouse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewProductInWarehouseRequest {

    @NotNull
    UUID productId;

    @NotNull
    Boolean fragile;

    @NotNull
    DimensionDto dimension;

    @NotNull
    @Positive
    Double weight;
}
