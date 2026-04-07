package com.microservices.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {
    private Long orderId;
    private Double amount;
}
