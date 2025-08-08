package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.cart.dto.ProductDto;
import ru.practicum.common.Constants;
import ru.practicum.exception.NotEnoughInfoInOrderToCalculateException;
import ru.practicum.feign.order.OrderFeignClient;
import ru.practicum.feign.store.StoreFeignClient;
import ru.practicum.mapper.PaymentMapper;
import ru.practicum.model.Payment;
import ru.practicum.model.enums.PaymentState;
import ru.practicum.order.OrderDto;
import ru.practicum.payment.PaymentDto;
import ru.practicum.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.practicum.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    private final OrderFeignClient orderFeignClient;
    private final StoreFeignClient storeFeignClient;

    @Override
    public PaymentDto createPayment(OrderDto order) {
        Payment payment = paymentRepository.save(getEnrichedPayment(order));
        return paymentMapper.toModel(payment);
    }

    @Override
    public BigDecimal getTotalCost(OrderDto order) {
        return calcCostTotal(order);
    }

    @Override
    public void paymentSuccess(UUID orderId) {
        updatePaymentState(orderId, PaymentState.SUCCESS);
        orderFeignClient.successPayOrder(orderId);
    }

    @Override
    public BigDecimal getProductCost(OrderDto order) {
        return calcCostProducts(order);
    }

    @Override
    public void paymentFailed(UUID orderId) {
        updatePaymentState(orderId, PaymentState.FAILED);
        orderFeignClient.failPayOrder(orderId);
    }

    private Payment getEnrichedPayment(OrderDto order) {
        BigDecimal fee = calcCostFee(order.getProductPrice());
        return Payment.builder()
                .orderId(order.getOrderId())
                .state(PaymentState.PENDING)
                .totalPayment(order.getTotalPrice())
                .deliveryTotal(order.getDeliveryPrice())
                .feeTotal(fee)
                .build();
    }

    private BigDecimal calcCostFee(BigDecimal cost) {
        return cost.multiply(BigDecimal.valueOf(Constants.BASE_RATE));
    }

    private BigDecimal calcCostTotal(OrderDto order) {

        BigDecimal productCost = calcCostProducts(order);
        BigDecimal deliveryCost = order.getDeliveryPrice();

        return productCost.add(calcCostFee(productCost)).add(deliveryCost);
    }

    private BigDecimal calcCostProducts(OrderDto order) {

        if (order == null || order.getProducts() == null) {
            throw new IllegalArgumentException("Order and products cannot be null");
        }

        if (order.getProducts().isEmpty()) {
            return BigDecimal.ZERO;
        }

        Map<UUID, ProductDto> products = order.getProducts().keySet().stream()
                .map(storeFeignClient::findProductById)
                .collect(Collectors.toMap(ProductDto::getProductId, Function.identity()));

        BigDecimal totalCost = BigDecimal.ZERO;

        for (Map.Entry<UUID, Integer> orderProduct : order.getProducts().entrySet()) {
            UUID productId = orderProduct.getKey();
            int quantity = orderProduct.getValue();

            if (!products.containsKey(productId)) {
                throw new NotEnoughInfoInOrderToCalculateException(
                        "Недостаточно информации в заказе для расчёта: продукт " + productId + " не найден"
                );
            }

            ProductDto product = products.get(productId);
            if (product.getPrice() == null) {
                throw new NotEnoughInfoInOrderToCalculateException(
                        "Цена продукта " + productId + " не указана"
                );
            }

            BigDecimal productCost = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity));
            totalCost = totalCost.add(productCost);
        }

        return totalCost;
    }

    private void updatePaymentState(UUID orderId, PaymentState newState) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(notFoundException("Оплата не найдена"));
        payment.setState(newState);
        paymentRepository.save(payment);
    }
}
