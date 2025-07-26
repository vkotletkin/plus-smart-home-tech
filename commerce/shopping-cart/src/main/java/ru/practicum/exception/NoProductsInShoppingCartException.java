package ru.practicum.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class NoProductsInShoppingCartException extends RuntimeException {

    public NoProductsInShoppingCartException(String message) {
        super(message);
    }

    public NoProductsInShoppingCartException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<NoProductsInShoppingCartException> noProductsInShoppingCartException(String message, Object... args) {
        return () -> new NoProductsInShoppingCartException(message, args);
    }

    public static Supplier<NoProductsInShoppingCartException> noProductsInShoppingCartException(String message) {
        return () -> new NoProductsInShoppingCartException(message);
    }
}
