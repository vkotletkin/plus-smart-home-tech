package ru.practicum.delivery;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.warehouse.AddressDto;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryDto {

    UUID deliveryId;

    @NotNull
    AddressDto senderAddress;

    @NotNull
    AddressDto recipientAddress;

    @NotNull
    UUID orderId;

    @NotNull
    DeliveryStatus deliveryStatus;
}
