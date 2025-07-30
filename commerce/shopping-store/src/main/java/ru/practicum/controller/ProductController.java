package ru.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.cart.dto.ProductDto;
import ru.practicum.cart.dto.ProductResponse;
import ru.practicum.cart.dto.QuantityStateRequest;
import ru.practicum.cart.enums.ProductCategory;
import ru.practicum.feign.store.StoreApi;
import ru.practicum.service.ProductService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController implements StoreApi {

    private final ProductService productService;

    @Override
    public ProductResponse findAllByCategory(@RequestParam(name = "category") ProductCategory productCategory, Pageable pageable) {
        log.info("Requested find all by category query");
        return productService.findProductsByCategory(productCategory, pageable);
    }

    @Override
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Requested product creation");
        return productService.createProduct(productDto);
    }

    @Override
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Requested product update");
        return productService.updateProduct(productDto);
    }

    @Override
    public void deleteProduct(@NotNull @RequestBody UUID productId) {
        log.info("Requested product delete");
        productService.deleteProduct(productId);
    }

    @Override
    public ProductDto updateQuantityState(@Valid QuantityStateRequest request) {
        log.info("Requested product update quantity state");
        return productService.updateQuantityState(request);
    }

    @Override
    public ProductDto findProductById(@PathVariable(name = "product-id") UUID productId) {
        log.info("Requested find product by id");
        return productService.findProductById(productId);
    }
}
