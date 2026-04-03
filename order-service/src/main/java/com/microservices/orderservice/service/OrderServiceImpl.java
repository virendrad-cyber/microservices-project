package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.dto.OrderResponse;
import com.microservices.orderservice.entity.Order;
import com.microservices.orderservice.fing.ProductClient;
import com.microservices.orderservice.fing.UserClient;
import com.microservices.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;
    private final UserClient userClient;
    private final ProductClient productClient;

    public OrderServiceImpl(OrderRepository repo,
                            UserClient userClient,
                            ProductClient productClient) {
        this.repo = repo;
        this.userClient = userClient;
        this.productClient = productClient;
    }



    @Override
    public OrderResponse create(OrderRequest request) {

        // Call User Service
        Object user = userClient.getUserById(request.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        //  Call Product Service
        Object product = productClient.getProductById(request.getProductId());
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();

        return map(repo.save(order));
    }

    private OrderResponse map(Order o) {
        return OrderResponse.builder()
                .id(o.getId())
                .userId(o.getUserId())
                .productId(o.getProductId())
                .quantity(o.getQuantity())
                .build();
    }
}
