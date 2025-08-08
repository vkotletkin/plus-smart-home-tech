package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.delivery.DeliveryRequest;
import ru.practicum.feign.warehouse.WarehouseApi;
import ru.practicum.service.WarehouseService;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.*;

import java.util.Map;
import java.util.UUID;

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

    @Override
    public void shippedToDelivery(DeliveryRequest request) {
        warehouseService.shippedToDelivery(request);
    }

    @Override
    public void acceptReturn(Map<UUID, Integer> products) {
        warehouseService.acceptReturn(products);
    }

    @Override
    public BookedProductsDto assemblyProductsForOrder(AssemblyRequest request) {
        return warehouseService.assemblyProductsForOrder(request);
    }
}