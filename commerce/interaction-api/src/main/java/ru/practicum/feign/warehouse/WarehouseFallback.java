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

    private static final String WAREHOUSE_SERVICE_UNAVAILABLE = "Warehouse Service Unavailable";

    @Override
    public void createProductToWarehouse(NewProductInWarehouseRequest request) {
        throw new WarehouseInternalServerException(WAREHOUSE_SERVICE_UNAVAILABLE);
    }

    @Override
    public BookedProductsDto checkProductToWarehouse(ShoppingCartDto shoppingCartDto) {
        throw new WarehouseInternalServerException(WAREHOUSE_SERVICE_UNAVAILABLE);
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
        throw new WarehouseInternalServerException(WAREHOUSE_SERVICE_UNAVAILABLE);
    }

    @Override
    public AddressDto getAddress() {
        throw new WarehouseInternalServerException(WAREHOUSE_SERVICE_UNAVAILABLE);
    }

    @Override
    public void shippedToDelivery(DeliveryRequest request) {
        throw new WarehouseInternalServerException(WAREHOUSE_SERVICE_UNAVAILABLE);
    }

    @Override
    public void acceptReturn(Map<UUID, Integer> products) {
        throw new WarehouseInternalServerException(WAREHOUSE_SERVICE_UNAVAILABLE);
    }

    @Override
    public BookedProductsDto assemblyProductsForOrder(AssemblyRequest request) {
        throw new WarehouseInternalServerException(WAREHOUSE_SERVICE_UNAVAILABLE);
    }
}
