package ru.practicum.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class ProductInShoppingCartLowQuantityInWarehouse extends RuntimeException {

    public ProductInShoppingCartLowQuantityInWarehouse(String message) {
        super(message);
    }

    public ProductInShoppingCartLowQuantityInWarehouse(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<ProductInShoppingCartLowQuantityInWarehouse> productInShoppingCartLowQuantityInWarehouse(String message, Object... args) {
        return () -> new ProductInShoppingCartLowQuantityInWarehouse(message, args);
    }

    public static Supplier<ProductInShoppingCartLowQuantityInWarehouse> productInShoppingCartLowQuantityInWarehouse(String message) {
        return () -> new ProductInShoppingCartLowQuantityInWarehouse(message);
    }
}