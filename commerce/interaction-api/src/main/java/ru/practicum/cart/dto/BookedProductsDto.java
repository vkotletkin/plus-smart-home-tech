package ru.practicum.cart.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookedProductsDto {

    @PositiveOrZero
    Double deliveryWeight;

    @PositiveOrZero
    Double deliveryVolume;

    Boolean fragile;
}
