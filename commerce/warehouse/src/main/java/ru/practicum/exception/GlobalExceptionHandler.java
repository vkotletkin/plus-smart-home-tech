package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.common.exception.model.ErrorResponse;

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
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                e.getMessage(),
                "Internal Server Error",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNoSpecifiedProductInWarehouseException(NoSpecifiedProductInWarehouseException e) {
        log.error("{}", e.getMessage());
        return new ErrorResponse(
                e.getCause(),
                Arrays.asList(e.getStackTrace()),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage(),
                "No specified product in warehouse",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleProductInShoppingCartLowQuantityInWarehouse(ProductInShoppingCartLowQuantityInWarehouse e) {
        log.error("{}", e.getMessage());
        return new ErrorResponse(
                e.getCause(),
                Arrays.asList(e.getStackTrace()),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage(),
                "Product in shopping cart low quantity in warehouse",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSpecifiedProductAlreadyInWarehouseException(SpecifiedProductAlreadyInWarehouseException e) {
        log.error("{}", e.getMessage());
        return new ErrorResponse(
                e.getCause(),
                Arrays.asList(e.getStackTrace()),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage(),
                "Specified product already in warehouse",
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
                HttpStatus.NOT_FOUND.name(),
                e.getMessage(),
                "Validation Error",
                Arrays.asList(e.getSuppressed()),
                e.getLocalizedMessage());
    }
}