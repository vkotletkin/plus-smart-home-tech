package ru.practicum.store;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuantityUpdateRequest {

    @NotNull
    UUID productId;

    @NotNull
    Long newQuantity;
}
