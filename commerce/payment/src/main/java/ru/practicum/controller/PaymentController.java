package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.feign.payment.PaymentApi;
import ru.practicum.order.OrderDto;
import ru.practicum.payment.PaymentDto;
import ru.practicum.service.PaymentService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;

    @Override
    public PaymentDto createPayment(OrderDto order) {
        return null;
    }

    @Override
    public BigDecimal getTotalCost(OrderDto order) {
        return null;
    }

    @Override
    public void paymentSuccess(UUID orderId) {

    }

    @Override
    public BigDecimal getProductCost(OrderDto order) {
        return null;
    }

    @Override
    public void paymentFailed(UUID orderId) {

    }
}
