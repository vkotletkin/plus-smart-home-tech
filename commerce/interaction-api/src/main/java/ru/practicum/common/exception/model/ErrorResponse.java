package ru.practicum.common.exception.model;

import java.util.List;

public record ErrorResponse(
        Throwable cause,
        List<StackTraceElement> stackTrace,
        String httpStatus,
        String userMessage,
        String message,
        List<Throwable> suppressed,
        String localizedMessage) {
}
