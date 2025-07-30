package ru.practicum.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstants {

    public static final String CART_NOT_FOUND_EXCEPTION_MESSAGE = "Cart with id: {0} not found";

    public static final String PRODUCT_NOT_FOUND_IN_CART_EXCEPTION_MESSAGE = "Product with id: {0} not found in cart: {1}";
}
