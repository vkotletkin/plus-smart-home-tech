package ru.practicum.feign.cart;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.practicum.store.QuantityUpdateRequest;
import ru.practicum.store.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "shopping-cart")
public interface CartFeignClient {

    String ENDPOINT_PREFIX = "/api/v1/shopping-cart";

    @GetMapping(ENDPOINT_PREFIX)
    ShoppingCartDto getShoppingCart(@RequestParam @NotEmpty String username);

    @PutMapping(ENDPOINT_PREFIX)
    ShoppingCartDto addProduct(@RequestBody Map<UUID, Long> products, @RequestParam(name = "username") String username);

    @DeleteMapping(ENDPOINT_PREFIX)
    void deleteShoppingCart(@RequestParam @NotEmpty String username);

    @PostMapping(ENDPOINT_PREFIX + "/remove")
    ShoppingCartDto removeProduct(@RequestBody List<UUID> products, @RequestParam(name = "username") String username);

    @PostMapping(ENDPOINT_PREFIX + "/change-quantity")
    ShoppingCartDto changeQuantity(@RequestBody QuantityUpdateRequest quantityUpdateRequest,
                                   @RequestParam(name = "username") String username);
}
