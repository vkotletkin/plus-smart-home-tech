package ru.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.cart.dto.ProductDto;
import ru.practicum.cart.dto.ProductResponse;
import ru.practicum.cart.dto.QuantityStateRequest;
import ru.practicum.cart.dto.SortDto;
import ru.practicum.cart.enums.ProductCategory;
import ru.practicum.service.ProductService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ProductResponse findAllByCategory(@RequestParam(name = "category") ProductCategory productCategory,
                                             Pageable pageable) {
        log.info("Requested find all by category query");
        return productService.findProductsByCategory(productCategory, pageable);
    }

    @PutMapping
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Requested product creation");
        return productService.createProduct(productDto);
    }

    @PostMapping
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Requested product update");
        return productService.updateProduct(productDto);
    }

    @PostMapping("/removeProductFromStore")
    public void deleteProduct(@NotNull @RequestBody UUID productId) {
        log.info("Requested product delete");
        productService.deleteProduct(productId);
    }

    @PostMapping("/quantityState")
    public ProductDto updateQuantityState(@Valid QuantityStateRequest request) {
        log.info("Requested product update quantity state");
        return productService.updateQuantityState(request);
    }

    @GetMapping("/{product-id}")
    public ProductDto findProductById(@PathVariable(name = "product-id") UUID productId) {
        log.info("Requested find product by id");
        return productService.findProductById(productId);
    }
}
