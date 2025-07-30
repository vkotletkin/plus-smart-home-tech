package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.model.ShoppingCart;
import ru.practicum.store.ShoppingCartDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShoppingCartMapper {

    @Mapping(target = "products", source = "items")
    @Mapping(target = "shoppingCartId", source = "id")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
