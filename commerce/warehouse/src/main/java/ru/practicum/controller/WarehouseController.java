package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.feign.warehouse.WarehouseApi;
import ru.practicum.service.WarehouseService;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.AddProductToWarehouseRequest;
import ru.practicum.warehouse.AddressDto;
import ru.practicum.warehouse.BookedProductsDto;
import ru.practicum.warehouse.NewProductInWarehouseRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WarehouseController implements WarehouseApi {

    private final WarehouseService warehouseService;

    @Override
    public void createProductToWarehouse(@Valid @RequestBody NewProductInWarehouseRequest request) {
        warehouseService.createWarehouseProduct(request);
    }

    @Override
    public BookedProductsDto checkProductToWarehouse(@Valid @RequestBody ShoppingCartDto shoppingCartDto) {
        log.info("Check product to warehouse");
        return warehouseService.checkProductOnShoppingCart(shoppingCartDto);
    }

    @Override
    public void addProductToWarehouse(@Valid @RequestBody AddProductToWarehouseRequest request) {
        warehouseService.addProductToWarehouse(request);
    }

    @Override
    public AddressDto getAddress() {
        return warehouseService.getWarehouseAddress();
    }
}