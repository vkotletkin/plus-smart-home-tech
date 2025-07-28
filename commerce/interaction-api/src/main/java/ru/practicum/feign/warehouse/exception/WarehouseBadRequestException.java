package ru.practicum.feign.warehouse.exception;

public class WarehouseBadRequestException extends RuntimeException {

    public WarehouseBadRequestException(String message) {
        super(message);
    }
}
