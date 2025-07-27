package ru.practicum.service;

import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.AddProductToWarehouseRequest;
import ru.practicum.warehouse.AddressDto;
import ru.practicum.warehouse.BookedProductsDto;
import ru.practicum.warehouse.NewProductInWarehouseRequest;

public interface WarehouseService {

    void createWarehouseProduct(NewProductInWarehouseRequest newProductInWarehouseRequest);

    void addProductToWarehouse(AddProductToWarehouseRequest addProductToWarehouseRequest);

    BookedProductsDto checkProductOnShoppingCart(ShoppingCartDto shoppingCartDto);

    AddressDto getWarehouseAddress();
}
