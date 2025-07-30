package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.practicum.mapper.WarehouseMapper;
import ru.practicum.model.WarehouseProduct;
import ru.practicum.repository.WarehouseProductRepository;
import ru.practicum.service.utils.AddressGenerator;
import ru.practicum.store.ShoppingCartDto;
import ru.practicum.warehouse.AddProductToWarehouseRequest;
import ru.practicum.warehouse.AddressDto;
import ru.practicum.warehouse.BookedProductsDto;
import ru.practicum.warehouse.NewProductInWarehouseRequest;

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
}
