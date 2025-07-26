package ru.practicum.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class NoSpecifiedProductInWarehouseException extends RuntimeException {

    public NoSpecifiedProductInWarehouseException(String message) {
        super(message);
    }

    public NoSpecifiedProductInWarehouseException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<NoSpecifiedProductInWarehouseException> noSpecifiedProductInWarehouseException(String message, Object... args) {
        return () -> new NoSpecifiedProductInWarehouseException(message, args);
    }

    public static Supplier<NoSpecifiedProductInWarehouseException> noSpecifiedProductInWarehouseException(String message) {
        return () -> new NoSpecifiedProductInWarehouseException(message);
    }
}