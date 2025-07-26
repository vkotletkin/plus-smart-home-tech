package ru.practicum.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.ShoppingService;
import ru.practicum.store.QuantityUpdateRequest;
import ru.practicum.store.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCartController {

    private final ShoppingService shoppingService;

    @GetMapping
    public ShoppingCartDto getShoppingCart(@RequestParam @NotEmpty String username) {
        log.info("Request getting shopping cart for username {}", username);
        return shoppingService.findShoppingCartByUsername(username);
    }

    @PutMapping
    public ShoppingCartDto addProduct(@RequestBody Map<UUID, Long> products,
                                      @RequestParam(name = "username") String username) {
        log.info("Request adding product for username {}", username);
        return shoppingService.createShoppingCart(username, products);
    }

    @DeleteMapping
    public void deleteShoppingCart(@RequestParam @NotEmpty String username) {
        log.info("Request deleting cart for username {}", username);
        shoppingService.deleteCart(username);
    }

    @PostMapping("/remove")
    public ShoppingCartDto removeProduct(@RequestBody List<UUID> products,
                                         @RequestParam(name = "username") String username) {
        return shoppingService.truncateCart(username, products);
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeQuantity(@RequestBody QuantityUpdateRequest quantityUpdateRequest,
                                          @RequestParam(name = "username") String username) {
        log.info("Request changing quantity for username {}", username);
        return shoppingService.changeCartQuantity(username, quantityUpdateRequest);
    }

}
