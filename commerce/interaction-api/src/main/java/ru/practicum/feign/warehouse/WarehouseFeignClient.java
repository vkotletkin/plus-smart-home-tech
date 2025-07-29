package ru.practicum.feign.warehouse;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "warehouse",
        configuration = WarehouseErrorDecoder.class,
        fallback = WarehouseFallback.class)
public interface WarehouseFeignClient extends WarehouseApi {
}
