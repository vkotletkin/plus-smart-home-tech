package ru.practicum.service;

import ru.practicum.delivery.DeliveryRequest;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {

    void createWarehouseProduct(NewProductInWarehouseRequest newProductInWarehouseRequest);

    void addProductToWarehouse(AddProductToWarehouseRequest addProductToWarehouseRequest);

    BookedProductsDto checkProductOnShoppingCart(ShoppingCartDto shoppingCartDto);

    AddressDto getWarehouseAddress();

    void shippedToDelivery(DeliveryRequest request);

    void acceptReturn(Map<UUID, Integer> products);

    BookedProductsDto assemblyProductsForOrder(AssemblyRequest request);
}
