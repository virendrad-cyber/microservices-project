package com.microservices.orderservice.fing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping("/payments")
    Object makePayment(@RequestParam Long orderId,
                       @RequestParam Double amount);
}
