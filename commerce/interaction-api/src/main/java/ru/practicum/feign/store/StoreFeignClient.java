package ru.practicum.feign.store;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.cart.dto.ProductDto;
import ru.practicum.cart.dto.ProductResponse;
import ru.practicum.cart.dto.QuantityStateRequest;
import ru.practicum.cart.enums.ProductCategory;

import java.util.UUID;

@FeignClient(name = "shopping-store")
public interface StoreFeignClient {

    String ENDPOINT_PREFIX = "/api/v1/shopping-store";

    @GetMapping(ENDPOINT_PREFIX)
    ProductResponse findAllByCategory(@RequestParam(name = "category") ProductCategory productCategory, Pageable pageable);

    @PutMapping(ENDPOINT_PREFIX)
    ProductDto createProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping(ENDPOINT_PREFIX)
    ProductDto updateProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping(ENDPOINT_PREFIX + "/removeProductFromStore")
    void deleteProduct(@NotNull @RequestBody UUID productId);

    @PostMapping(ENDPOINT_PREFIX + "/quantityState")
    ProductDto updateQuantityState(@Valid QuantityStateRequest request);

    @GetMapping(ENDPOINT_PREFIX + "/{product-id}")
    ProductDto findProductById(@PathVariable(name = "product-id") UUID productId);
}
