package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.*;
import com.microservices.orderservice.entity.Order;
import com.microservices.orderservice.entity.OrderStatus;
import com.microservices.orderservice.fing.PaymentClient;
import com.microservices.orderservice.fing.ProductClient;
import com.microservices.orderservice.fing.UserClient;
import com.microservices.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final PaymentServiceCaller paymentServiceCaller;

    public OrderServiceImpl(OrderRepository repo,
                            UserClient userClient,
                            ProductClient productClient, PaymentClient paymentClient,  PaymentServiceCaller paymentServiceCaller) {
        this.repo = repo;
        this.userClient = userClient;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
        this.paymentServiceCaller = paymentServiceCaller;
    }



    @Override
    public OrderResponse create(OrderRequest request) {

        // 1. Validate User
        Object user = userClient.getUserById(request.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 2. Validate Product + get price
        ProductResponse product = productClient.getProductById(request.getProductId());
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // 3. Calculate amount
        double totalAmount = product.getPrice() * request.getQuantity();

        // 4. Create Order (CREATED)
        Order order = Order.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .amount(totalAmount)
                .status(OrderStatus.CREATED)
                .build();

        order = repo.save(order);

        try {
            // 5. Update status → PAYMENT_PENDING
            order.setStatus(OrderStatus.PAYMENT_PENDING);
            repo.save(order);

            // 6. Call Payment Service
            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .orderId(order.getId())
                    .amount(totalAmount)
                    .build();

            PaymentResponse response = paymentServiceCaller.callPaymentService(paymentRequest);

            // 7. Handle response
            if ("SUCCESS".equalsIgnoreCase(response.getStatus())) {
                order.setStatus(OrderStatus.PAYMENT_COMPLETED);
            } else {
                order.setStatus(OrderStatus.PAYMENT_FAILED);
            }

        } catch (Exception ex) {
            // 8. Failure case
            order.setStatus(OrderStatus.PAYMENT_FAILED);
        }

        // 9. Save final state
        order = repo.save(order);

        return map(order);
    }

    private OrderResponse map(Order o) {
        return OrderResponse.builder()
                .id(o.getId())
                .userId(o.getUserId())
                .productId(o.getProductId())
                .quantity(o.getQuantity())
                .status(o.getStatus().name())
                .amount(o.getAmount())
                .build();
    }
}
