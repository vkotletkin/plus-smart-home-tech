package ru.practicum.feign.payment;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.order.OrderDto;
import ru.practicum.payment.PaymentDto;

import java.math.BigDecimal;
import java.util.UUID;

@Validated
public interface PaymentApi {

    String ENDPOINT_BASE = "/api/v1/payment";

    @PostMapping(ENDPOINT_BASE)
    PaymentDto createPayment(@RequestBody @NotNull OrderDto order);

    @PostMapping(ENDPOINT_BASE + "/totalCost")
    BigDecimal getTotalCost(@RequestBody @NotNull OrderDto order);

    @PostMapping(ENDPOINT_BASE + "/refund")
    void paymentSuccess(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/productCost")
    BigDecimal getProductCost(@RequestBody @NotNull OrderDto order);

    @PostMapping(ENDPOINT_BASE + "/failed")
    void paymentFailed(@RequestBody @NotNull UUID orderId);
}