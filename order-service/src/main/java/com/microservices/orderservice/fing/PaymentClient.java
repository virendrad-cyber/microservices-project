package com.microservices.orderservice.fing;

import com.microservices.orderservice.dto.PaymentRequest;
import com.microservices.orderservice.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping("/payments")
    PaymentResponse makePayment(@RequestBody PaymentRequest request);
}