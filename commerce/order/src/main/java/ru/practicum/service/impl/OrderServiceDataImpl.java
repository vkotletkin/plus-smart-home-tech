package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.Order;
import ru.practicum.order.OrderStatus;
import ru.practicum.repository.OrderRepository;
import ru.practicum.service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static ru.practicum.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceDataImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getUserOrders(String username) {
        return orderRepository.findAllByUsername(username);
    }

    @Override
    @Transactional
    public Order createNewOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order returnProducts(UUID orderId) {
        return updateOrderState(orderId, OrderStatus.PRODUCT_RETURNED);
    }

    @Override
    @Transactional
    public Order successedPayOrder(UUID orderId) {
        return updateOrderState(orderId, OrderStatus.PAID);
    }

    @Override
    @Transactional
    public Order failPayOrder(UUID orderId) {
        return updateOrderState(orderId, OrderStatus.PAYMENT_FAILED);
    }


    @Override
    @Transactional
    public Order setDeliveryPrice(UUID orderId, BigDecimal deliveryCost) {
        Order order = findOrderById(orderId);
        order.setDeliveryPrice(deliveryCost);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order assemblyOrder(UUID orderId) {
        return updateOrderState(orderId, OrderStatus.ASSEMBLED);
    }

    @Override
    @Transactional
    public Order failAssemblyOrder(UUID orderId) {
        return updateOrderState(orderId, OrderStatus.ASSEMBLY_FAILED);
    }

    @Override
    public Order getOrderById(UUID orderId) {
        return findOrderById(orderId);
    }

    @Override
    @Transactional
    public Order savePaymentInfo(Order order) {
        Order oldOrder = findOrderById(order.getId());
        oldOrder.setProductPrice(order.getProductPrice());
        oldOrder.setDeliveryPrice(order.getDeliveryPrice());
        oldOrder.setTotalPrice(order.getTotalPrice());
        oldOrder.setPaymentId(order.getPaymentId());
        oldOrder.setOrderStatus(OrderStatus.ON_PAYMENT);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order setDelivery(UUID orderId, UUID deliveryId) {
        Order order = findOrderById(orderId);
        order.setDeliveryId(deliveryId);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order deliveryOrder(UUID orderId) {
        return updateOrderState(orderId, OrderStatus.DELIVERED);
    }

    @Override
    @Transactional
    public Order failedDeliveryOrder(UUID orderId) {
        return updateOrderState(orderId, OrderStatus.DELIVERY_FAILED);
    }

    @Override
    @Transactional
    public Order completeOrder(UUID orderId) {
        return updateOrderState(orderId, OrderStatus.COMPLETED);
    }

    @Override
    @Transactional
    public Order setTotalPrice(UUID orderId, BigDecimal totalCost) {
        Order order = findOrderById(orderId);
        order.setTotalPrice(totalCost);
        return orderRepository.save(order);
    }

    private Order updateOrderState(UUID orderId, OrderStatus newState) {
        Order order = findOrderById(orderId);
        order.setOrderStatus(newState);
        return orderRepository.save(order);
    }

    private Order findOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                notFoundException("Заказ покупателя не найден. Идентификатор заказа: {0}", orderId));
    }
}