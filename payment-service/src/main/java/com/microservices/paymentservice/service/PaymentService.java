package com.microservices.paymentservice.service;

import com.microservices.paymentservice.dto.PaymentResponse;
import com.microservices.paymentservice.entity.Payment;
import com.microservices.paymentservice.entity.PaymentStatus;
import com.microservices.paymentservice.repsotory.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;

    public PaymentResponse processPayment(Long orderId, Double amount) {

        PaymentStatus status = decidePaymentStatus(amount);
        System.out.println(">>> PAYMENT AMOUNT RECEIVED: " + amount);

        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .status(status)
                .build();

        repo.save(payment);

        return new PaymentResponse(status.name()); // IMPORTANT
    }

    private PaymentStatus decidePaymentStatus(Double amount) {
        System.out.println(">>>>>>---payment "+ amount);
        if (amount == null || amount <= 0) {
            return PaymentStatus.FAILED;
        }

        // simulate realistic behavior (no hardcoding)
        return Math.random() < 0.85
                ? PaymentStatus.SUCCESS
                : PaymentStatus.FAILED;
    }
}