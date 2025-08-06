package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.delivery.DeliveryRequest;
import ru.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.practicum.mapper.WarehouseMapper;
import ru.practicum.model.Booking;
import ru.practicum.model.WarehouseProduct;
import ru.practicum.repository.BookingRepository;
import ru.practicum.repository.WarehouseProductRepository;
import ru.practicum.service.utils.AddressGenerator;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.practicum.exception.NoSpecifiedProductInWarehouseException.noSpecifiedProductInWarehouseException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseProductRepository warehouseProductRepository;
    private final BookingRepository bookingRepository;
    private final WarehouseMapper warehouseMapper;


    @Override
    @Transactional
    public void createWarehouseProduct(NewProductInWarehouseRequest newProductInWarehouseRequest) {
        checkProductExist(newProductInWarehouseRequest);
        WarehouseProduct product = warehouseMapper.toModel(newProductInWarehouseRequest);
        warehouseProductRepository.save(product);
    }

    @Override
    @Transactional
    public void addProductToWarehouse(AddProductToWarehouseRequest addProductToWarehouseRequest) {
        WarehouseProduct warehouseProduct = getWarehouseProduct(addProductToWarehouseRequest.getProductId());
        warehouseProduct.setQuantity(warehouseProduct.getQuantity() + addProductToWarehouseRequest.getQuantity());
        warehouseProductRepository.save(warehouseProduct);
    }

    @Override
    public BookedProductsDto checkProductOnShoppingCart(ShoppingCartDto shoppingCartDto) {

        Map<UUID, Long> products = shoppingCartDto.getProducts();
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findAllById(products.keySet());

        Map<UUID, WarehouseProduct> warehouseProductMap = warehouseProducts.stream()
                .collect(Collectors.toMap(WarehouseProduct::getId, Function.identity()));

        if (warehouseProductMap.size() != products.size()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse("Some products not exists on warehouse");
        }

        return calculateProductsRequest(products, warehouseProductMap);
    }

    @Override
    public AddressDto getWarehouseAddress() {
        String address = AddressGenerator.getCurrentAddress();
        return new AddressDto(address);
    }

    @Override
    @Transactional
    public void shippedToDelivery(DeliveryRequest request) {
        Booking booking = bookingRepository.findById(request.getOrderId())
                .orElseThrow(noSpecifiedProductInWarehouseException("Нет информации о бронировании товаров по заказу"));
        booking.setDeliveryId(request.getDeliveryId());
        bookingRepository.save(booking);
    }

    @Override
    public void acceptReturn(Map<UUID, Integer> products) {

        Map<UUID, WarehouseProduct> warehouseProducts = getWarehouseProducts(products.keySet());

        for (Map.Entry<UUID, Integer> product : products.entrySet()) {
            WarehouseProduct warehouseProduct = warehouseProducts.get(product.getKey());
            warehouseProduct.setQuantity(warehouseProduct.getQuantity() + product.getValue());
        }

        warehouseProductRepository.saveAll(warehouseProducts.values());
    }

    @Override
    public BookedProductsDto assemblyProductsForOrder(AssemblyRequest request) {

        Map<UUID, Long> orderProducts = request.getProducts();
        Map<UUID, WarehouseProduct> products = getWarehouseProducts(orderProducts.keySet());

        // set default for calculating
        double weight = 0;
        double volume = 0;
        boolean fragile = false;

        for (Map.Entry<UUID, Long> cartProduct : orderProducts.entrySet()) {

            WarehouseProduct product = products.get(cartProduct.getKey());
            long newQuantity = product.getQuantity() - cartProduct.getValue();

            if (newQuantity < 0) {
                throw new ProductInShoppingCartLowQuantityInWarehouse(
                        String.format(
                                "шибка, товар из корзины не находится в требуемом количестве на складе. Идентификатор: %s",
                                cartProduct.getKey()));
            }

            product.setQuantity(newQuantity);

            weight += product.getWeight() * cartProduct.getValue();
            volume += product.getHeight() * product.getWeight() * product.getDepth() * cartProduct.getValue();
            fragile = fragile || product.isFragile();
        }

        Booking booking = Booking.builder()
                .orderId(request.getOrderId())
                .products(request.getProducts())
                .build();

        bookingRepository.save(booking);
        warehouseProductRepository.saveAll(products.values());

        return new BookedProductsDto(
                weight,
                volume,
                fragile
        );
    }

    private void checkProductExist(NewProductInWarehouseRequest newProductInWarehouseRequest) {
        if (warehouseProductRepository.existsById(newProductInWarehouseRequest.getProductId())) {
            throw new SpecifiedProductAlreadyInWarehouseException("Product with id: {0} already exists",
                    newProductInWarehouseRequest.getProductId());
        }
    }

    private WarehouseProduct getWarehouseProduct(UUID id) {
        return warehouseProductRepository.findById(id)
                .orElseThrow(noSpecifiedProductInWarehouseException("Information about product: {0} not found", id));
    }

    private BookedProductsDto calculateProductsRequest(Map<UUID, Long> cartProducts, Map<UUID, WarehouseProduct> products) {

        double deliveryWeight = 0;
        double deliveryVolume = 0;
        boolean fragile = false;

        for (Map.Entry<UUID, Long> cartProductEntry : cartProducts.entrySet()) {
            WarehouseProduct product = products.get(cartProductEntry.getKey());
            if (cartProductEntry.getValue() < product.getQuantity()) {
                deliveryWeight += product.getWeight() * cartProductEntry.getValue();
                deliveryVolume += product.getHeight() * product.getWeight() * product.getDepth() * cartProductEntry.getValue();
                fragile = fragile || product.isFragile();
            } else {
                throw new ProductInShoppingCartLowQuantityInWarehouse(
                        "Product from cart not exists on warehouse in needed count");
            }
        }
        return new BookedProductsDto(deliveryWeight, deliveryVolume, fragile);
    }

    private Map<UUID, WarehouseProduct> getWarehouseProducts(Collection<UUID> ids) {

        Map<UUID, WarehouseProduct> products = warehouseProductRepository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(WarehouseProduct::getId, Function.identity()));

        if (products.size() != ids.size()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse("Некоторых товаров нет на складе");
        }

        return products;
    }
}
