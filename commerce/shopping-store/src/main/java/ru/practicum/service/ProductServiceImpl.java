package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.cart.dto.ProductDto;
import ru.practicum.cart.dto.ProductResponse;
import ru.practicum.cart.dto.QuantityStateRequest;
import ru.practicum.cart.dto.SortDto;
import ru.practicum.cart.enums.ProductCategory;
import ru.practicum.cart.enums.ProductState;
import ru.practicum.mapper.ProductMapper;
import ru.practicum.model.Product;
import ru.practicum.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

import static ru.practicum.common.constants.ExceptionConstants.NOT_FOUND_EXCEPTION_MESSAGE;
import static ru.practicum.exception.NotFoundException.notFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse findProductsByCategory(ProductCategory productCategory, Pageable page) {

        List<ProductDto> products = productMapper.toDto(productRepository.findAllByProductCategory(productCategory, page));

        List<SortDto> sorts = page.getSort().stream()
                .map(o -> new SortDto(o.getProperty(), o.getDirection().name()))
                .toList();

        return ProductResponse.builder()
                .content(products)
                .sort(sorts)
                .build();
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toModel(productDto);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {

        Product product = checkProductExists(productDto.getProductId());

        Product updatedProduct = productMapper.toModel(productDto);
        updatedProduct.setProductId(product.getProductId());
        productRepository.save(updatedProduct);

        return productMapper.toDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        Product product = checkProductExists(productId);
        product.setProductState(ProductState.DEACTIVATE);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public ProductDto updateQuantityState(QuantityStateRequest quantityStateRequest) {
        Product product = checkProductExists(quantityStateRequest.getProductId());
        product.setQuantityState(quantityStateRequest.getQuantityState());
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto findProductById(UUID uuid) {
        Product product = checkProductExists(uuid);
        return productMapper.toDto(product);
    }

    private Product checkProductExists(UUID id) {
        return productRepository.findByProductId(id).orElseThrow(notFoundException(NOT_FOUND_EXCEPTION_MESSAGE, id));
    }
}
