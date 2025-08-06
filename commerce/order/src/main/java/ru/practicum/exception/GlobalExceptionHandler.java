package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.common.exception.model.ErrorResponse;
import ru.practicum.feign.warehouse.exception.WarehouseBadRequestException;
import ru.practicum.feign.warehouse.exception.WarehouseInternalServerException;
import ru.practicum.feign.warehouse.exception.WarehouseNotFoundException;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCommonException(RuntimeException e) {
        log.error("{}", e.getMessage());
        return new ErrorResponse(
                e.getCause(),
                Arrays.asList(e.getStackTrace()),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage(),
                "Internal Server Error",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.error("{}", e.getMessage());
        return new ErrorResponse(
                e.getCause(),
                Arrays.asList(e.getStackTrace()),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage(),
                "Not Found Exception",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(RuntimeException e) {
        log.error("{}", e.getMessage());
        return new ErrorResponse(
                e.getCause(),
                Arrays.asList(e.getStackTrace()),
                HttpStatus.BAD_REQUEST.name(),
                e.getMessage(),
                "Validation Error",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleWarehouseBadRequestException(WarehouseBadRequestException e) {
        log.error("{}", e.getMessage());
        return new ErrorResponse(
                e.getCause(),
                Arrays.asList(e.getStackTrace()),
                HttpStatus.BAD_REQUEST.name(),
                e.getMessage(),
                "Warehouse Bad Request",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleWarehouseNotFoundException(WarehouseNotFoundException e) {
        log.error("{}", e.getMessage());
        return new ErrorResponse(
                e.getCause(),
                Arrays.asList(e.getStackTrace()),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage(),
                "Warehouse Not Found",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleWarehouseInternalServerException(WarehouseInternalServerException e) {
        log.error("{}", e.getMessage());
        return new ErrorResponse(
                e.getCause(),
                Arrays.asList(e.getStackTrace()),
                HttpStatus.SERVICE_UNAVAILABLE.name(),
                e.getMessage(),
                "Warehouse Service Unavailable",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }
}