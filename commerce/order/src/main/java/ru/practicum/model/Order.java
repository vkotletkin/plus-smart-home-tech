package ru.practicum.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.order.OrderStatus;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @Column(name = "user_name")
    String username;

    @Column(name = "cart_id")
    UUID cartId;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    Map<UUID, Long> products;

    @Column(name = "payment_id")
    UUID paymentId;

    @Column(name = "delivery_id")
    UUID deliveryId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    OrderStatus orderStatus;

    @Column(name = "delivery_weight")
    Double deliveryWeight;

    @Column(name = "delivery_volume")
    Double deliveryVolume;

    Boolean fragile;

    @Column(name = "total_price")
    BigDecimal totalPrice;
    @Column(name = "delivery_price")
    BigDecimal deliveryPrice;
    @Column(name = "product_price")
    BigDecimal productPrice;
}