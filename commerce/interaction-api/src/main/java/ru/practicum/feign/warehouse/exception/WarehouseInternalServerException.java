package ru.practicum.feign.warehouse.exception;

public class WarehouseInternalServerException extends RuntimeException {

    public WarehouseInternalServerException(String message) {
        super(message);
    }
}
