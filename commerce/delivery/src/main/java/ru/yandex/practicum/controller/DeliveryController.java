package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import ru.practicum.feign.order.OrderFeignClient;

@RequiredArgsConstructor
public class DeliveryController {

    private final OrderFeignClient orderFeignClient;
}
