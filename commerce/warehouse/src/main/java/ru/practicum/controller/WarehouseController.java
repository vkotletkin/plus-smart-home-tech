package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.WarehouseService;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.AddProductToWarehouseRequest;
import ru.practicum.warehouse.AddressDto;
import ru.practicum.warehouse.BookedProductsDto;
import ru.practicum.warehouse.NewProductInWarehouseRequest;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PutMapping
    public void createProductToWarehouse(@Valid @RequestBody NewProductInWarehouseRequest request) {
        warehouseService.createWarehouseProduct(request);
    }

    @PostMapping("/check")
    public BookedProductsDto checkProductToWarehouse(@Valid @RequestBody ShoppingCartDto shoppingCartDto) {
        return warehouseService.checkProductOnShoppingCart(shoppingCartDto);
    }

    @PostMapping("/add")
    public void addProductToWarehouse(@Valid @RequestBody AddProductToWarehouseRequest request) {
        warehouseService.addProductToWarehouse(request);
    }

    @GetMapping("/address")
    public AddressDto getAddress() {
        return warehouseService.getWarehouseAddress();
    }
}
