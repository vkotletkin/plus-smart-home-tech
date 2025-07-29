package ru.practicum.feign.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;
import ru.practicum.store.QuantityUpdateRequest;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.BookedProductsDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartApi {

    @GetMapping("/api/v1/shopping-cart")
    ShoppingCartDto getShoppingCart(@RequestParam @NotEmpty String username);

    @PutMapping("/api/v1/shopping-cart")
    ShoppingCartDto addProduct(@RequestBody Map<UUID, Long> products, @RequestParam(name = "username") String username);

    @DeleteMapping("/api/v1/shopping-cart")
    void deleteShoppingCart(@RequestParam @NotEmpty String username);

    @PostMapping("/api/v1/shopping-cart/remove")
    ShoppingCartDto removeProduct(@RequestBody List<UUID> products, @RequestParam(name = "username") String username);

    @PostMapping("/api/v1/shopping-cart/change-quantity")
    ShoppingCartDto changeQuantity(@RequestBody QuantityUpdateRequest quantityUpdateRequest,
                                   @RequestParam(name = "username") String username);

    @PostMapping("/api/v1/shopping-cart/booking")
    BookedProductsDto bookProducts(@RequestParam @NotBlank String username);
}
