package ru.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.enums.PaymentState;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "payments")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @Column(name = "order_id")
    UUID orderId;

    @Enumerated(value = EnumType.STRING)
    PaymentState state;

    @Column(name = "total_cost")
    BigDecimal totalPayment;

    @Column(name = "delivery_cost")
    BigDecimal deliveryTotal;

    @Column(name = "fee_cost")
    BigDecimal feeTotal;
}