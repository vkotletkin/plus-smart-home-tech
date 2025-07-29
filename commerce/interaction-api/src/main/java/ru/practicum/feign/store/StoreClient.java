package ru.practicum.feign.store;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "shopping-store")
public interface StoreClient extends StoreApi {
}
