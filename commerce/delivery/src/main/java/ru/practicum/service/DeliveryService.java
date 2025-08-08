package ru.practicum.service;

import ru.practicum.delivery.DeliveryDto;
import ru.practicum.order.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface DeliveryService {

    DeliveryDto createDelivery(DeliveryDto deliveryDto);

    void completeDelivery(UUID orderId);

    void confirmsPickup(UUID orderId);

    void failsDelivery(UUID orderId);

    BigDecimal calculateDeliveryCost(OrderDto orderDto);
}
