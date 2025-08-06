package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.delivery.DeliveryDto;
import ru.practicum.feign.order.OrderFeignClient;
import ru.practicum.feign.warehouse.WarehouseFeignClient;
import ru.practicum.mapper.AddressMapper;
import ru.practicum.mapper.DeliveryMapper;
import ru.practicum.order.OrderDto;
import ru.practicum.repository.DeliveryRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;
    private final AddressMapper addressMapper;

    private final OrderFeignClient orderFeignClient;
    private final WarehouseFeignClient warehouseFeignClient;

    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        return null;
    }

    @Override
    public void completeDelivery(UUID orderId) {

    }

    @Override
    public void confirmsPickup(UUID orderId) {

    }

    @Override
    public void failsDelivery(UUID orderId) {

    }

    @Override
    public BigDecimal calculateDeliveryCost(OrderDto orderDto) {
        return null;
    }
}
