package ru.practicum.service;

import ru.practicum.order.OrderDto;
import ru.practicum.payment.PaymentDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {

    PaymentDto createPayment(OrderDto order);

    BigDecimal getTotalCost(OrderDto order);

    void paymentSuccess(UUID orderId);

    BigDecimal getProductCost(OrderDto order);

    void paymentFailed(UUID orderId);
}