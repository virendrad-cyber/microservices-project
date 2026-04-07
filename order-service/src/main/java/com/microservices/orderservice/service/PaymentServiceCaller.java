package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.PaymentRequest;
import com.microservices.orderservice.dto.PaymentResponse;
import com.microservices.orderservice.fing.PaymentClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceCaller {

    private final PaymentClient paymentClient;

    public PaymentServiceCaller(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    @Retry(name = "paymentService")
    public PaymentResponse callPaymentService(PaymentRequest request) {

        System.out.println(">>>>>Calling Payment Service...");
        PaymentResponse response = paymentClient.makePayment(request);
        System.out.println(">>>>>Payment Response: " + response);

        return response;
    }

    public PaymentResponse paymentFallback(PaymentRequest request, Throwable ex) {
        System.out.println(">>>>FALLBACK TRIGGERED: " + ex.getMessage());
        return new PaymentResponse("FAILED");
    }
}