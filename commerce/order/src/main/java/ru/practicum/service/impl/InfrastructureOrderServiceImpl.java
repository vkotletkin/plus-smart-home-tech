package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.delivery.DeliveryDto;
import ru.practicum.delivery.DeliveryStatus;
import ru.practicum.feign.cart.CartFeignClient;
import ru.practicum.feign.delivery.DeliveryFeignClient;
import ru.practicum.feign.payment.PaymentFeignClient;
import ru.practicum.feign.warehouse.WarehouseFeignClient;
import ru.practicum.mapper.OrderMapper;
import ru.practicum.model.Order;
import ru.practicum.order.OrderCreateRequest;
import ru.practicum.order.OrderDto;
import ru.practicum.order.OrderStatus;
import ru.practicum.order.ProductReturnRequest;
import ru.practicum.payment.PaymentDto;
import ru.practicum.service.InfrastructureOrderService;
import ru.practicum.service.OrderService;
import ru.practicum.warehouse.AddressDto;
import ru.practicum.warehouse.AssemblyRequest;
import ru.practicum.warehouse.BookedProductsDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InfrastructureOrderServiceImpl implements InfrastructureOrderService {

    private final OrderService orderDataService;
    private final OrderMapper orderMapper;

    private final CartFeignClient cartFeignClient;
    private final WarehouseFeignClient warehouseFeignClient;
    private final DeliveryFeignClient deliveryFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    @Override
    public List<OrderDto> getUserOrders(String username) {
        return orderDataService.getUserOrders(username).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto failDeliverOrder(UUID orderId) {
        return orderMapper.toDto(orderDataService.failedDeliveryOrder(orderId));
    }

    @Override
    public OrderDto completeOrder(UUID orderId) {
        return orderMapper.toDto(orderDataService.completeOrder(orderId));
    }

    @Override
    public OrderDto calculateTotalPrice(UUID orderId) {

        Order order = orderDataService.getOrderById(orderId);
        BigDecimal cost = paymentFeignClient.getTotalCost(orderMapper.toDto(order));

        return orderMapper.toDto(orderDataService.setTotalPrice(orderId, cost));
    }

    @Override
    public OrderDto createNewOrder(OrderCreateRequest request) {
        Order order = orderDataService.createNewOrder(getNewOrderFromRequest(request));
        UUID deliveryId = getCreatedDeliveryId(order.getId(), request.getRecipientAddress());
        return orderMapper.toDto(orderDataService.setDelivery(order.getId(), deliveryId));
    }

    @Override
    public OrderDto returnProducts(ProductReturnRequest request) {
        warehouseFeignClient.acceptReturn(request.getProducts());
        return orderMapper.toDto(orderDataService.returnProducts(request.getOrderId()));
    }

    @Override
    public OrderDto payOrder(UUID orderId) {

        Order order = orderDataService.getOrderById(orderId);

        BigDecimal productCost = paymentFeignClient.getProductCost(orderMapper.toDto(order));
        BigDecimal deliveryCost = deliveryFeignClient.calculateDeliveryCost(orderMapper.toDto(order));

        order.setDeliveryPrice(deliveryCost);
        order.setProductPrice(productCost);


        BigDecimal total = paymentFeignClient.getTotalCost(orderMapper.toDto(order));
        order.setTotalPrice(total);

        PaymentDto paymentDto = paymentFeignClient.createPayment(orderMapper.toDto(order));
        order.setPaymentId(paymentDto.getPaymentId());

        Order savedOrder = orderDataService.savePaymentInfo(order);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto successedPayOrder(UUID orderId) {
        return orderMapper.toDto(orderDataService.successedPayOrder(orderId));
    }

    @Override
    public OrderDto failedPayOrder(UUID orderId) {
        return orderMapper.toDto(orderDataService.failPayOrder(orderId));
    }

    @Override
    public OrderDto deliveryOrder(UUID orderId) {
        return orderMapper.toDto(orderDataService.deliveryOrder(orderId));
    }

    @Override
    public OrderDto calculateDeliveryPrice(UUID orderId) {

        Order order = orderDataService.getOrderById(orderId);
        BigDecimal deliveryCost = deliveryFeignClient.calculateDeliveryCost(orderMapper.toDto(order));

        return orderMapper.toDto(orderDataService.setDeliveryPrice(orderId, deliveryCost));
    }

    @Override
    public OrderDto assemblyOrder(UUID orderId) {
        warehouseFeignClient.assemblyProductsForOrder(getNewAssemblyProductsForRequestOnOrder(orderId));
        return orderMapper.toDto(orderDataService.assemblyOrder(orderId));
    }

    @Override
    public OrderDto failAssemblyOrder(UUID orderId) {
        return orderMapper.toDto(orderDataService.failAssemblyOrder(orderId));
    }

    @Override
    public OrderDto getOrderDetails(UUID orderId) {
        Order order = orderDataService.getOrderById(orderId);
        return orderMapper.toDto(order);
    }

    private AssemblyRequest getNewAssemblyProductsForRequestOnOrder(UUID orderId) {

        Order order = orderDataService.getOrderById(orderId);

        return AssemblyRequest.builder()
                .orderId(orderId)
                .products(order.getProducts())
                .build();
    }

    private Order getNewOrderFromRequest(OrderCreateRequest request) {

        BookedProductsDto bookedProductsDto = cartFeignClient.bookProducts(request.getUsername());

        return Order.builder()
                .username(request.getUsername())
                .cartId(request.getShoppingCart().getShoppingCartId())
                .products(request.getShoppingCart().getProducts())
                .deliveryWeight(bookedProductsDto.deliveryWeight())
                .deliveryVolume(bookedProductsDto.deliveryVolume())
                .fragile(bookedProductsDto.fragile())
                .orderStatus(OrderStatus.NEW)
                .build();
    }

    private UUID getCreatedDeliveryId(UUID orderId, AddressDto address) {

        DeliveryDto deliveryDto = DeliveryDto.builder()
                .senderAddress(address)
                .recipientAddress(address)
                .orderId(orderId)
                .deliveryStatus(DeliveryStatus.CREATED)
                .build();

        return deliveryFeignClient.createDelivery(deliveryDto).getDeliveryId();
    }
}
