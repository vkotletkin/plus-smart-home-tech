package ru.practicum.feign.payment;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "payment")
public interface PaymentClient extends PaymentApi {
}
