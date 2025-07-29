package ru.practicum.feign.store;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.cart.dto.ProductDto;
import ru.practicum.cart.dto.ProductResponse;
import ru.practicum.cart.dto.QuantityStateRequest;
import ru.practicum.cart.enums.ProductCategory;

import java.util.UUID;


public interface StoreApi {

    String ENDPOINT_BASE = "/api/v1/shopping-store";

    @GetMapping(ENDPOINT_BASE)
    ProductResponse findAllByCategory(@RequestParam(name = "category") ProductCategory productCategory, Pageable pageable);

    @PutMapping(ENDPOINT_BASE)
    ProductDto createProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping(ENDPOINT_BASE)
    ProductDto updateProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping(ENDPOINT_BASE + "/removeProductFromStore")
    void deleteProduct(@NotNull @RequestBody UUID productId);

    @PostMapping(ENDPOINT_BASE + "/quantityState")
    ProductDto updateQuantityState(@Valid QuantityStateRequest request);

    @GetMapping(ENDPOINT_BASE + "/{product-id}")
    ProductDto findProductById(@PathVariable(name = "product-id") UUID productId);
}
