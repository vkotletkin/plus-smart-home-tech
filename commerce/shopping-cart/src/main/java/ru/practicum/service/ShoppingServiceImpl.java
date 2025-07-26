package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.ShoppingCartMapper;
import ru.practicum.model.ShoppingCart;
import ru.practicum.repository.ShoppingCartRepository;
import ru.practicum.store.QuantityUpdateRequest;
import ru.practicum.store.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static ru.practicum.constants.ExceptionConstants.CART_NOT_FOUND_EXCEPTION_MESSAGE;
import static ru.practicum.constants.ExceptionConstants.PRODUCT_NOT_FOUND_IN_CART_EXCEPTION_MESSAGE;
import static ru.practicum.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingServiceImpl implements ShoppingService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartDto findShoppingCartByUsername(String username) {
        ShoppingCart shoppingCart = getShoppingCart(username);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto createShoppingCart(String username, Map<UUID, Long> products) {

        final ShoppingCart shoppingCart = shoppingCartRepository.findByUserName(username)
                .orElseGet(createNewCartForUsername(username));

        shoppingCart.setItems(products);
        shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void deleteCart(String username) {
        getShoppingCart(username);
        shoppingCartRepository.deleteByUserName(username);
    }

    @Override
    public ShoppingCartDto truncateCart(String username, List<UUID> products) {

        ShoppingCart shoppingCart = getShoppingCart(username);
        products.forEach(product -> shoppingCart.getItems().remove(product));

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto changeCartQuantity(String username, QuantityUpdateRequest quantityUpdateRequest) {

        ShoppingCart shoppingCart = getShoppingCart(username);
        checkIfProductExists(shoppingCart, quantityUpdateRequest.getProductId());

        shoppingCart.getItems().put(quantityUpdateRequest.getProductId(), quantityUpdateRequest.getNewQuantity());
        shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    private Supplier<ShoppingCart> createNewCartForUsername(String username) {
        return () -> ShoppingCart.builder()
                .userName(username)
                .build();
    }

    private ShoppingCart getShoppingCart(String username) {
        return shoppingCartRepository.findByUserName(username)
                .orElseThrow(notFoundException(CART_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private void checkIfProductExists(ShoppingCart shoppingCart, UUID productId) {
        if (!shoppingCart.getItems().containsKey(productId)) {
            throw new NotFoundException(PRODUCT_NOT_FOUND_IN_CART_EXCEPTION_MESSAGE, productId, shoppingCart.getId());
        }
    }
}
