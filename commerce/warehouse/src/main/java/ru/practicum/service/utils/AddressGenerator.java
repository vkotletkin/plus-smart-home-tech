package ru.practicum.service.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressGenerator {

    private static final String[] ADDRESSES = new String[]{"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS = ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    public static String getCurrentAddress() {
        return CURRENT_ADDRESS;
    }
}
