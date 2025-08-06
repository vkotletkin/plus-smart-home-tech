package ru.practicum.feign.warehouse;

import org.springframework.stereotype.Component;
import ru.practicum.delivery.DeliveryRequest;
import ru.practicum.feign.warehouse.exception.WarehouseInternalServerException;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.*;

import java.util.Map;
import java.util.UUID;

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

    @Override
    public void shippedToDelivery(DeliveryRequest request) {
        throw new WarehouseInternalServerException("Warehouse service unavailable");
    }

    @Override
    public void acceptReturn(Map<UUID, Integer> products) {
        throw new WarehouseInternalServerException("Warehouse service unavailable");
    }

    @Override
    public BookedProductsDto assemblyProductsForOrder(AssemblyRequest request) {
        throw new WarehouseInternalServerException("Warehouse service unavailable");
    }
}
