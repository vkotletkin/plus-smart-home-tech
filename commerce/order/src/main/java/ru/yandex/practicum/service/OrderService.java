package ru.yandex.practicum.service;

import ru.practicum.order.OrderCreateRequest;
import ru.practicum.order.OrderDto;
import ru.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderDto> getUserOrders(String userName);

    OrderDto createNewOrder(OrderCreateRequest request);

    OrderDto returnProducts(ProductReturnRequest request);

    OrderDto payOrder(UUID orderId);

    OrderDto successedPayOrder(UUID orderId);

    OrderDto failedPayOrder(UUID orderId);

    OrderDto deliveryOrder(UUID orderId);

    OrderDto failDeliveryOrder(UUID orderId);

    OrderDto completeOrder(UUID orderId);

    OrderDto calculateTotalPrice(UUID orderId);

    OrderDto calculateDeliveryPrice(UUID orderId);

    OrderDto assemblyOrder(UUID orderId);

    OrderDto failAssemblyOrder(UUID orderId);

    OrderDto getOrderDetails(UUID orderId);
}