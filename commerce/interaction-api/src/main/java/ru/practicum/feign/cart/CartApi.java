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

    String ENDPOINT_BASE = "/api/v1/shopping-cart";

    @GetMapping(ENDPOINT_BASE)
    ShoppingCartDto getShoppingCart(@RequestParam @NotEmpty String username);

    @PutMapping(ENDPOINT_BASE)
    ShoppingCartDto addProduct(@RequestBody Map<UUID, Long> products, @RequestParam(name = "username") String username);

    @DeleteMapping(ENDPOINT_BASE)
    void deleteShoppingCart(@RequestParam @NotEmpty String username);

    @PostMapping(ENDPOINT_BASE + "/remove")
    ShoppingCartDto removeProduct(@RequestBody List<UUID> products, @RequestParam(name = "username") String username);

    @PostMapping(ENDPOINT_BASE + "/change-quantity")
    ShoppingCartDto changeQuantity(@RequestBody QuantityUpdateRequest quantityUpdateRequest,
                                   @RequestParam(name = "username") String username);

    @PostMapping(ENDPOINT_BASE + "/booking")
    BookedProductsDto bookProducts(@RequestParam @NotBlank String username);
}
