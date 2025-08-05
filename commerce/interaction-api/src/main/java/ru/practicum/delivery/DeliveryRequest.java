package ru.practicum.delivery;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryRequest {

    @NotNull
    UUID orderId;

    @NotNull
    UUID deliveryId;
}