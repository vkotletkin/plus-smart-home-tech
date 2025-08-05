package ru.practicum.feign.order;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order")
public interface OrderFeignClient extends OrderApi {
}
