package ru.practicum.feign.warehouse;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.feign.warehouse.exception.WarehouseBadRequestException;
import ru.practicum.feign.warehouse.exception.WarehouseInternalServerException;
import ru.practicum.feign.warehouse.exception.WarehouseNotFoundException;

@Slf4j
public class WarehouseErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 400) {
            log.error("Bad request to warehouse service");
            return new WarehouseBadRequestException(
                    String.format("Bad request to warehouse service: %s", methodKey));
        }

        if (response.status() == 404) {
            log.error("Not found to warehouse service");
            return new WarehouseNotFoundException(
                    String.format("Resource not found for method: %s", methodKey));
        }

        if (response.status() >= 500) {
            log.error("Internal server error");
            return new WarehouseInternalServerException("Server error occurred");
        }

        return defaultDecoder.decode(methodKey, response);
    }
}
