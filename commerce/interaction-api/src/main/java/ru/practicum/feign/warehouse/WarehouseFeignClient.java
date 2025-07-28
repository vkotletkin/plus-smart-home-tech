package ru.practicum.feign.warehouse;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.AddProductToWarehouseRequest;
import ru.practicum.warehouse.AddressDto;
import ru.practicum.warehouse.BookedProductsDto;
import ru.practicum.warehouse.NewProductInWarehouseRequest;

@FeignClient(name = "warehouse",
        configuration = WarehouseErrorDecoder.class,
        fallback = WarehouseFallback.class)
public interface WarehouseFeignClient {

    String ENDPOINT_PREFIX = "/api/v1/warehouse";

    @PutMapping(ENDPOINT_PREFIX)
    void createProductToWarehouse(@Valid @RequestBody NewProductInWarehouseRequest request);

    @PostMapping(ENDPOINT_PREFIX + "/check")
    BookedProductsDto checkProductToWarehouse(@Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @PostMapping(ENDPOINT_PREFIX + "/add")
    void addProductToWarehouse(@Valid @RequestBody AddProductToWarehouseRequest request);

    @GetMapping(ENDPOINT_PREFIX + "/address")
    AddressDto getAddress();
}
