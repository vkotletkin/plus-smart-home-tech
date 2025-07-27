package ru.practicum.warehouse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DimensionDto {

    @NotNull
    @Positive
    Double width;

    @NotNull
    @Positive
    Double height;

    @NotNull
    @Positive
    Double depth;
}