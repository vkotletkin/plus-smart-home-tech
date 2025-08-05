package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.feign.order.OrderFeignClient;
import ru.practicum.order.OrderCreateRequest;
import ru.practicum.order.OrderDto;
import ru.practicum.order.ProductReturnRequest;
import ru.practicum.mapper.OrderMapper;
import ru.practicum.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderFeignClient orderFeignClient;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> getUserOrders(String userName) {
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
    public OrderDto successedPayOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto failedPayOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto deliveryOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto failDeliveryOrder(UUID orderId) {
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
    public OrderDto getOrderDetails(UUID orderId) {
        return null;
    }
}
