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

    String ENDPOINT_BASE = "/api/v1/warehouse";

    @PutMapping(ENDPOINT_BASE)
    void createProductToWarehouse(@Valid @RequestBody NewProductInWarehouseRequest request);

    @PostMapping(ENDPOINT_BASE + "/check")
    BookedProductsDto checkProductToWarehouse(@Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @PostMapping(ENDPOINT_BASE + "/add")
    void addProductToWarehouse(@Valid @RequestBody AddProductToWarehouseRequest request);

    @GetMapping(ENDPOINT_BASE + "/address")
    AddressDto getAddress();
}
