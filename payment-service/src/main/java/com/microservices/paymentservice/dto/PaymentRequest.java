package com.microservices.paymentservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private long orderId;
    private Double amount;
}
