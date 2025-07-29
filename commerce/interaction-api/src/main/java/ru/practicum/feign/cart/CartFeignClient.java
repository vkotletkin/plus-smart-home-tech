package ru.practicum.feign.cart;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "shopping-cart")
public interface CartFeignClient extends CartApi {
}
