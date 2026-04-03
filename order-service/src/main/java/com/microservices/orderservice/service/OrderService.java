package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.dto.OrderResponse;

public interface OrderService {
    OrderResponse create(OrderRequest request);
}
