package ru.practicum.feign.warehouse;

import org.springframework.stereotype.Component;
import ru.practicum.feign.warehouse.exception.WarehouseInternalServerException;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.AddProductToWarehouseRequest;
import ru.practicum.warehouse.AddressDto;
import ru.practicum.warehouse.BookedProductsDto;
import ru.practicum.warehouse.NewProductInWarehouseRequest;

@Component
public class WarehouseFallback implements WarehouseFeignClient {

    @Override
    public void createProductToWarehouse(NewProductInWarehouseRequest request) {
        throw new WarehouseInternalServerException("Warehouse service unavailable");
    }

    @Override
    public BookedProductsDto checkProductToWarehouse(ShoppingCartDto shoppingCartDto) {
        throw new WarehouseInternalServerException("Warehouse service unavailable");
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
        throw new WarehouseInternalServerException("Warehouse service unavailable");
    }

    @Override
    public AddressDto getAddress() {
        throw new WarehouseInternalServerException("Warehouse service unavailable");
    }
}
