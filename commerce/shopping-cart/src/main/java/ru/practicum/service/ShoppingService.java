package ru.practicum.service;

import ru.practicum.store.QuantityUpdateRequest;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.BookedProductsDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ShoppingService {

    ShoppingCartDto findShoppingCartByUsername(String username);

    ShoppingCartDto createShoppingCart(String username, Map<UUID, Long> products);

    void deleteCart(String username);

    ShoppingCartDto truncateCart(String username, List<UUID> products);

    ShoppingCartDto changeCartQuantity(String username, QuantityUpdateRequest quantityUpdateRequest);

    BookedProductsDto bookProducts(String userName);
}
