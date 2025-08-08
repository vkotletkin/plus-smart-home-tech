package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.delivery.DeliveryDto;
import ru.practicum.model.Delivery;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    DeliveryDto toDto(Delivery entity);
}
