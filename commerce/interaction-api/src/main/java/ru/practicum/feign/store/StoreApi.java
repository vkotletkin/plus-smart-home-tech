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

    @GetMapping("/api/v1/shopping-store")
    ProductResponse findAllByCategory(@RequestParam(name = "category") ProductCategory productCategory, Pageable pageable);

    @PutMapping("/api/v1/shopping-store")
    ProductDto createProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping("/api/v1/shopping-store")
    ProductDto updateProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping("/api/v1/shopping-store/removeProductFromStore")
    void deleteProduct(@NotNull @RequestBody UUID productId);

    @PostMapping("/api/v1/shopping-store/quantityState")
    ProductDto updateQuantityState(@Valid QuantityStateRequest request);

    @GetMapping("/api/v1/shopping-store/{product-id}")
    ProductDto findProductById(@PathVariable(name = "product-id") UUID productId);
}
