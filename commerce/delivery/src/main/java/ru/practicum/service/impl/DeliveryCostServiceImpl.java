package ru.practicum.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.common.Constants;
import ru.practicum.service.DeliveryCostService;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Domain service without infrastructure logic
@Service
public class DeliveryCostServiceImpl implements DeliveryCostService {

    public BigDecimal calculateCostForDelivery(String senderAddress, String recipientAddress, boolean isFragile,
                                               double deliveryWeight, double deliveryVolume) {

        BigDecimal cost = BigDecimal.valueOf(Constants.BASE_DELIVERY);

        BigDecimal fromAddressCoef = getCoefByFromAddress(senderAddress);
        cost = cost.add(BigDecimal.valueOf(Constants.BASE_DELIVERY).multiply(fromAddressCoef));

        BigDecimal fragileCoef = getFragileCoefficient(isFragile);
        cost = cost.multiply(fragileCoef);

        BigDecimal weightCost = BigDecimal.valueOf(deliveryWeight)
                .multiply(BigDecimal.valueOf(Constants.WEIGHT_RATE));
        cost = cost.add(weightCost);

        BigDecimal volumeCost = BigDecimal.valueOf(deliveryVolume)
                .multiply(BigDecimal.valueOf(Constants.VOLUME_RATE));
        cost = cost.add(volumeCost);

        BigDecimal distanceCoef = getCoefByToAddress(senderAddress, recipientAddress);
        cost = cost.multiply(distanceCoef);

        return cost.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getCoefByFromAddress(String address) {
        if (address.contains("ADDRESS_1")) {
            return BigDecimal.valueOf(Constants.ADDRESS_1_ADDRESS);
        } else if (address.contains("ADDRESS_2")) {
            return BigDecimal.valueOf(Constants.ADDRESS_2_ADDRESS);
        } else {
            return BigDecimal.valueOf(Constants.BASE_ADDRESS);
        }
    }

    private BigDecimal getCoefByToAddress(String from, String to) {
        if (!from.equals(to)) {
            return BigDecimal.valueOf(Constants.DIFF_STREET_ADDRESS);
        }
        return BigDecimal.valueOf(1.0);
    }

    private BigDecimal getFragileCoefficient(boolean isFragile) {
        return BigDecimal.valueOf(isFragile ? Constants.FRAGILE : 1.0);
    }
}