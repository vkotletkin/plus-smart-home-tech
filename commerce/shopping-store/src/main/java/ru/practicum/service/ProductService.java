package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.cart.dto.ProductDto;
import ru.practicum.cart.dto.ProductResponse;
import ru.practicum.cart.dto.QuantityStateRequest;
import ru.practicum.cart.enums.ProductCategory;

import java.util.UUID;

public interface ProductService {

    ProductResponse findProductsByCategory(ProductCategory productCategory, Pageable page);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProduct(UUID productId);

    ProductDto updateQuantityState(QuantityStateRequest quantityStateRequest);

    ProductDto findProductById(UUID uuid);
}
