package ru.practicum.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductReturnRequest {

    @NotNull
    String userName;

    @NotNull
    UUID orderId;

    @NotEmpty
    Map<UUID, Integer> products;
}
