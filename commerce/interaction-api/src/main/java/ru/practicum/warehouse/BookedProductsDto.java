package ru.practicum.warehouse;

public record BookedProductsDto(double deliveryWeight, double deliveryVolume, boolean fragile) {
}
