package ru.practicum.feign.order;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order")
public interface OrderClient extends OrderApi {
}
