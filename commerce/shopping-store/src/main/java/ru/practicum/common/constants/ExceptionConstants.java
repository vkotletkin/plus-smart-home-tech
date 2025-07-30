package ru.practicum.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstants {

    public static final String NOT_FOUND_EXCEPTION_MESSAGE = "Product with id: {0} not found";
}
