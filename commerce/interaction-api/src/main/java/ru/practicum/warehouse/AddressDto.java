package ru.practicum.warehouse;

public record AddressDto(String country, String city, String street, String house, String flat) {
    public AddressDto(String address) {
        this(address, address, address, address, address);
    }
}
