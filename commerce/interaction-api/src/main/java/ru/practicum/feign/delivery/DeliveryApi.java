package ru.practicum.feign.delivery;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.delivery.DeliveryDto;
import ru.practicum.order.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

@Validated
public interface DeliveryApi {

    String ENDPOINT_BASE = "/api/v1/delivery";

    @PutMapping(ENDPOINT_BASE)
    DeliveryDto createDelivery(@RequestBody @Valid DeliveryDto deliveryDto);

    @PostMapping(ENDPOINT_BASE + "/successful")
    void completeDelivery(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/picked")
    void confirmPickup(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/failed")
    void failDelivery(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/cost")
    BigDecimal calculateDeliveryCost(@RequestBody @Valid OrderDto orderDto);
}