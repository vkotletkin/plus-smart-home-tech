package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.feign.order.OrderApi;
import ru.practicum.order.OrderCreateRequest;
import ru.practicum.order.OrderDto;
import ru.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Override
    public List<OrderDto> getClientOrders(String username) {
        return List.of();
    }

    @Override
    public OrderDto createNewOrder(OrderCreateRequest request) {
        return null;
    }

    @Override
    public OrderDto returnProducts(ProductReturnRequest request) {
        return null;
    }

    @Override
    public OrderDto payOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto failPayOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto successPayOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto deliverOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto failDeliverOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto completeOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto calculateTotalPrice(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto calculateDeliveryPrice(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto assemblyOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto failAssemblyOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto getOrder(UUID orderId) {
        return null;
    }
}
