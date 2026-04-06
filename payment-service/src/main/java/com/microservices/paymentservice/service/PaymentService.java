package com.microservices.paymentservice.service;

import com.microservices.paymentservice.entity.Payment;
import com.microservices.paymentservice.repsotory.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;

    public Payment processPayment(Long orderId, Double amount) {

        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .status("SUCCESS") // mock logic
                .build();

        return repo.save(payment);
    }
}
