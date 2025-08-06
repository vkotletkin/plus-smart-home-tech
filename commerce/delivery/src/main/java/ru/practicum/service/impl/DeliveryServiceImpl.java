package ru.practicum.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.delivery.DeliveryDto;
import ru.practicum.delivery.DeliveryRequest;
import ru.practicum.delivery.DeliveryStatus;
import ru.practicum.feign.order.OrderFeignClient;
import ru.practicum.feign.warehouse.WarehouseFeignClient;
import ru.practicum.mapper.AddressMapper;
import ru.practicum.mapper.DeliveryMapper;
import ru.practicum.model.Delivery;
import ru.practicum.order.OrderDto;
import ru.practicum.repository.DeliveryRepository;
import ru.practicum.service.DeliveryCostService;
import ru.practicum.service.DeliveryService;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.practicum.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class DeliveryServiceImpl implements DeliveryService {

    private static final String NOT_FOUND_DELIVERY_EXCEPTION_MESSAGE = "Не найдена доставка с идентификатором: {0}";

    private final DeliveryCostService deliveryCostService; // domain service
    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;
    private final AddressMapper addressMapper;

    private final OrderFeignClient orderFeignClient;
    private final WarehouseFeignClient warehouseFeignClient;

    @Override
    @Transactional
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = transformToDeliveryWithEnrich(deliveryDto);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return deliveryMapper.toDto(savedDelivery);
    }

    @Override
    @Transactional
    public void completeDelivery(UUID orderId) {

        Delivery delivery = deliveryRepository.findByOrderId(orderId).orElseThrow(
                notFoundException(NOT_FOUND_DELIVERY_EXCEPTION_MESSAGE, orderId));

        delivery.setDeliveryStatus(DeliveryStatus.DELIVERED);
        deliveryRepository.save(delivery);

        orderFeignClient.deliveryOrder(orderId);
    }

    @Override
    @Transactional
    public void confirmsPickup(UUID orderId) {

        Delivery delivery = deliveryRepository.findByOrderId(orderId).orElseThrow(
                notFoundException(NOT_FOUND_DELIVERY_EXCEPTION_MESSAGE, orderId));

        delivery.setDeliveryStatus(DeliveryStatus.IN_PROGRESS);
        deliveryRepository.save(delivery);

        DeliveryRequest request = DeliveryRequest.builder()
                .orderId(delivery.getOrderId())
                .deliveryId(delivery.getId())
                .build();

        warehouseFeignClient.shippedToDelivery(request);
    }

    @Override
    @Transactional
    public void failsDelivery(UUID orderId) {

        Delivery delivery = deliveryRepository.findByOrderId(orderId).orElseThrow(
                notFoundException(NOT_FOUND_DELIVERY_EXCEPTION_MESSAGE, orderId));

        delivery.setDeliveryStatus(DeliveryStatus.FAILED);
        deliveryRepository.save(delivery);

        orderFeignClient.failDeliverOrder(orderId);
    }

    @Override
    public BigDecimal calculateDeliveryCost(@NotNull OrderDto orderDto) {

        Delivery delivery = deliveryRepository.findByOrderId(orderDto.getOrderId()).orElseThrow(
                notFoundException(NOT_FOUND_DELIVERY_EXCEPTION_MESSAGE, orderDto.getOrderId()));

        return deliveryCostService.calculateCostForDelivery(delivery.getSenderAddress().toString(), delivery.getRecipientAddress().toString(),
                delivery.isFragile(), delivery.getDeliveryWeight(), delivery.getDeliveryVolume());
    }

    private Delivery transformToDeliveryWithEnrich(DeliveryDto dto) {
        return Delivery.builder()
                .orderId(dto.getOrderId())
                .senderAddress(addressMapper.toModel(dto.getSenderAddress()))
                .recipientAddress(addressMapper.toModel(dto.getRecipientAddress()))
                .deliveryStatus(DeliveryStatus.CREATED)
                .build();
    }
}
