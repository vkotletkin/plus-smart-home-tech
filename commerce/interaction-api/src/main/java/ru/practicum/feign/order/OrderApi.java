package ru.practicum.feign.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.order.OrderCreateRequest;
import ru.practicum.order.OrderDto;
import ru.practicum.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

@Validated
public interface OrderApi {

    String ENDPOINT_BASE = "/api/v1/order";

    @GetMapping(ENDPOINT_BASE)
    List<OrderDto> getClientOrders(@RequestParam @NotEmpty String username);

    @PutMapping(ENDPOINT_BASE)
    OrderDto createNewOrder(@RequestBody @Valid OrderCreateRequest request);

    @PostMapping(ENDPOINT_BASE + "/return")
    OrderDto returnProducts(@RequestBody @Valid ProductReturnRequest request);

    @PostMapping(ENDPOINT_BASE + "/payment")
    OrderDto payOrder(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/payment/failed")
    OrderDto failPayOrder(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/payment/success")
    OrderDto successPayOrder(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/delivery")
    OrderDto deliverOrder(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/delivery/failed")
    OrderDto failDeliverOrder(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/completed")
    OrderDto completeOrder(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/calculate/total")
    OrderDto calculateTotalPrice(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/calculate/delivery")
    OrderDto calculateDeliveryPrice(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/assembly")
    OrderDto assemblyOrder(@RequestBody @NotNull UUID orderId);

    @PostMapping(ENDPOINT_BASE + "/assembly/failed")
    OrderDto failAssemblyOrder(@RequestBody @NotNull UUID orderId);

    @GetMapping(ENDPOINT_BASE + "/only")
    OrderDto getOrder(@RequestBody @NotNull UUID orderId);
}