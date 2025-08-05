package ru.practicum.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.AddressDto;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreateRequest {

    @NotBlank
    String username;

    @NotNull
    ShoppingCartDto shoppingCart;

    @NotNull
    AddressDto recipientAddress;
}
