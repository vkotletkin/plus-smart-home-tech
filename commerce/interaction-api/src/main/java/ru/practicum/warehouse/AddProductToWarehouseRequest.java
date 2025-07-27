package ru.practicum.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddProductToWarehouseRequest {

    @NonNull
    UUID productId;

    @NotNull
    Long quantity;
}
