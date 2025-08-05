package ru.practicum.feign.delivery;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "delivery")
public interface DeliveryClient extends DeliveryApi {
}
