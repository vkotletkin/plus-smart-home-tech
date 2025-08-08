package ru.practicum.service;

import ru.practicum.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<Order> getUserOrders(String username);

    Order createNewOrder(Order order);

    Order failedDeliveryOrder(UUID orderId);

    Order completeOrder(UUID orderId);

    Order setTotalPrice(UUID orderId, BigDecimal totalCost);

    Order setDeliveryPrice(UUID orderId, BigDecimal deliveryCost);

    Order assemblyOrder(UUID orderId);

    Order failAssemblyOrder(UUID orderId);

    Order getOrderById(UUID orderId);

    Order savePaymentInfo(Order order);

    Order returnProducts(UUID orderId);

    Order successedPayOrder(UUID orderId);

    Order failPayOrder(UUID orderId);

    Order setDelivery(UUID orderId, UUID deliveryId);

    Order deliveryOrder(UUID orderId);
}