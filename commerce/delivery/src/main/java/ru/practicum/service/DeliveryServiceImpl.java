package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import ru.practicum.feign.order.OrderFeignClient;

@RequiredArgsConstructor
public class DeliveryServiceImpl {

    private final OrderFeignClient orderFeignClient;
}
