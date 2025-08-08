package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class FallbackController {

    @GetMapping("/fallback/shopping-store")
    public Mono<ResponseEntity<String>> shoppingStoreFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("{\"message\": \"Shopping Store service is temporarily unavailable. Please try again later.\"}"));
    }

    @GetMapping("/fallback/shopping-cart")
    public Mono<ResponseEntity<String>> shoppingCartFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("{\"message\": \"Shopping Cart service is temporarily unavailable. Please try again later.\"}"));
    }

    @GetMapping("/fallback/warehouse")
    public Mono<ResponseEntity<String>> warehouseFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("{\"message\": \"Warehouse service is temporarily unavailable. Please try again later.\"}"));
    }
}
