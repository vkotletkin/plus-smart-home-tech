package ru.practicum.feign.warehouse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.AddProductToWarehouseRequest;
import ru.practicum.warehouse.AddressDto;
import ru.practicum.warehouse.BookedProductsDto;
import ru.practicum.warehouse.NewProductInWarehouseRequest;

public interface WarehouseApi {

    @PutMapping("/api/v1/warehouse")
    void createProductToWarehouse(@Valid @RequestBody NewProductInWarehouseRequest request);

    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkProductToWarehouse(@Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @PostMapping("/api/v1/warehouse/add")
    void addProductToWarehouse(@Valid @RequestBody AddProductToWarehouseRequest request);

    @GetMapping("/api/v1/warehouse/address")
    AddressDto getAddress();
}
