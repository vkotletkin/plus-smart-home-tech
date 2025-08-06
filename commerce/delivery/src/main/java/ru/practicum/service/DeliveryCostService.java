package ru.practicum.service;

import java.math.BigDecimal;

public interface DeliveryCostService {

    BigDecimal calculateCostForDelivery(String senderAddress, String recipientAddress, boolean isFragile,
                                        double deliveryWeight, double deliveryVolume);
}
