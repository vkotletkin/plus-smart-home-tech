package ru.practicum.feign.warehouse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.delivery.DeliveryRequest;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.*;

import java.util.Map;
import java.util.UUID;

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

    @PostMapping(ENDPOINT_BASE + "/shipped")
    void shippedToDelivery(@RequestBody @Valid DeliveryRequest request);

    @PostMapping(ENDPOINT_BASE + "/return")
    void acceptReturn(@RequestBody @Valid Map<UUID, Integer> products);

    @PostMapping(ENDPOINT_BASE + "/assembly")
    BookedProductsDto assemblyProductsForOrder(@RequestBody @Valid AssemblyRequest request);
}
