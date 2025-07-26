package ru.practicum.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class SpecifiedProductAlreadyInWarehouseException extends RuntimeException {

    public SpecifiedProductAlreadyInWarehouseException(String message) {
        super(message);
    }

    public SpecifiedProductAlreadyInWarehouseException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<SpecifiedProductAlreadyInWarehouseException> specifiedProductAlreadyInWarehouseException(String message, Object... args) {
        return () -> new SpecifiedProductAlreadyInWarehouseException(message, args);
    }

    public static Supplier<SpecifiedProductAlreadyInWarehouseException> specifiedProductAlreadyInWarehouseException(String message) {
        return () -> new SpecifiedProductAlreadyInWarehouseException(message);
    }
}
