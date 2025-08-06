package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.feign.order.OrderApi;
import ru.practicum.order.OrderCreateRequest;
import ru.practicum.order.OrderDto;
import ru.practicum.order.ProductReturnRequest;
import ru.practicum.service.InfrastructureOrderService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final InfrastructureOrderService infrastructureOrderService;

    @Override
    public List<OrderDto> getClientOrders(String username) {
        return infrastructureOrderService.getUserOrders(username);
    }

    @Override
    public OrderDto createNewOrder(OrderCreateRequest request) {
        return infrastructureOrderService.createNewOrder(request);
    }

    @Override
    public OrderDto returnProducts(ProductReturnRequest request) {
        return infrastructureOrderService.returnProducts(request);
    }

    @Override
    public OrderDto payOrder(UUID orderId) {
        return infrastructureOrderService.payOrder(orderId);
    }

    @Override
    public OrderDto failPayOrder(UUID orderId) {
        return infrastructureOrderService.failedPayOrder(orderId);
    }

    @Override
    public OrderDto successPayOrder(UUID orderId) {
        return infrastructureOrderService.successedPayOrder(orderId);
    }

    @Override
    public OrderDto deliveryOrder(UUID orderId) {
        return infrastructureOrderService.deliveryOrder(orderId);
    }

    @Override
    public OrderDto failDeliverOrder(UUID orderId) {
        return infrastructureOrderService.failDeliverOrder(orderId);
    }

    @Override
    public OrderDto completeOrder(UUID orderId) {
        return infrastructureOrderService.completeOrder(orderId);
    }

    @Override
    public OrderDto calculateTotalPrice(UUID orderId) {
        return infrastructureOrderService.calculateTotalPrice(orderId);
    }

    @Override
    public OrderDto calculateDeliveryPrice(UUID orderId) {
        return infrastructureOrderService.calculateDeliveryPrice(orderId);
    }

    @Override
    public OrderDto assemblyOrder(UUID orderId) {
        return infrastructureOrderService.assemblyOrder(orderId);
    }

    @Override
    public OrderDto failAssemblyOrder(UUID orderId) {
        return infrastructureOrderService.failAssemblyOrder(orderId);

    }

    @Override
    public OrderDto getOrder(UUID orderId) {
        return infrastructureOrderService.getOrderDetails(orderId);
    }
}