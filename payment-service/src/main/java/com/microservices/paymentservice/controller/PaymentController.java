package com.microservices.paymentservice.controller;

import com.microservices.paymentservice.dto.PaymentRequest;
import com.microservices.paymentservice.dto.PaymentResponse;
import com.microservices.paymentservice.entity.Payment;
import com.microservices.paymentservice.repsotory.PaymentRepository;
import com.microservices.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse makePayment(@RequestBody PaymentRequest request) {

        return paymentService.processPayment(
                request.getOrderId(),
                request.getAmount()
        );
    }
}
