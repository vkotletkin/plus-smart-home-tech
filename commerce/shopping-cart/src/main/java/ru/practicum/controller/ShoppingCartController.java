package ru.practicum.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.feign.cart.CartApi;
import ru.practicum.service.ShoppingService;
import ru.practicum.store.QuantityUpdateRequest;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.BookedProductsDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCartController implements CartApi {

    private final ShoppingService shoppingService;

    @Override
    public ShoppingCartDto getShoppingCart(@RequestParam @NotEmpty String username) {
        log.info("Request getting shopping cart for username {}", username);
        return shoppingService.findShoppingCartByUsername(username);
    }

    @Override
    public ShoppingCartDto addProduct(@RequestBody Map<UUID, Long> products, @RequestParam(name = "username") String username) {
        log.info("Request adding product for username {}", username);
        return shoppingService.createShoppingCart(username, products);
    }

    @Override
    public void deleteShoppingCart(@RequestParam @NotEmpty String username) {
        log.info("Request deleting cart for username {}", username);
        shoppingService.deleteCart(username);
    }

    @Override
    public ShoppingCartDto removeProduct(@RequestBody List<UUID> products,
                                         @RequestParam(name = "username") String username) {
        return shoppingService.truncateCart(username, products);
    }

    @Override
    public ShoppingCartDto changeQuantity(@RequestBody QuantityUpdateRequest quantityUpdateRequest,
                                          @RequestParam(name = "username") String username) {
        log.info("Request changing quantity for username {}", username);
        return shoppingService.changeCartQuantity(username, quantityUpdateRequest);
    }

    @Override
    public BookedProductsDto bookProducts(@NotBlank String username) {
        return shoppingService.bookProducts(username);
    }
}
