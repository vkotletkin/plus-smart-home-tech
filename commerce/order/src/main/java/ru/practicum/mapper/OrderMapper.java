package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.model.Order;
import ru.practicum.order.OrderDto;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "shoppingCartId", source = "cartId")
    @Mapping(target = "orderId", source = "id")
    OrderDto toDto(Order order);

    @Mapping(target = "shoppingCartId", source = "cartId")
    @Mapping(target = "orderId", source = "id")
    List<OrderDto> toDto(List<Order> orders);
}
