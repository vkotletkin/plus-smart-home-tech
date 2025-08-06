package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.delivery.DeliveryDto;
import ru.practicum.feign.delivery.DeliveryApi;
import ru.practicum.order.OrderDto;
import ru.practicum.service.DeliveryService;

import java.math.BigDecimal;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
public class DeliveryController implements DeliveryApi {

    private final DeliveryService deliveryService;

    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        return deliveryService.createDelivery(deliveryDto);
    }

    @Override
    public void completeDelivery(UUID orderId) {
        deliveryService.completeDelivery(orderId);
    }

    @Override
    public void confirmPickup(UUID orderId) {
        deliveryService.confirmsPickup(orderId);
    }

    @Override
    public void failDelivery(UUID orderId) {
        deliveryService.failsDelivery(orderId);
    }

    @Override
    public BigDecimal calculateDeliveryCost(OrderDto orderDto) {
        return deliveryService.calculateDeliveryCost(orderDto);
    }
}