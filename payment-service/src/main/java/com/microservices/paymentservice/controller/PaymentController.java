package com.microservices.paymentservice.controller;

import com.microservices.paymentservice.entity.Payment;
import com.microservices.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public Payment makePayment(@RequestParam Long orderId,
                               @RequestParam Double amount) {
        return service.processPayment(orderId, amount);
    }
}
