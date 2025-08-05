package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.order.OrderDto;
import ru.yandex.practicum.model.Order;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "shoppingCartId", source = "cartId")
    @Mapping(target = "orderId", source = "id")
    OrderDto toModel(Order order);
}
