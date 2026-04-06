package com.microservices.orderservice.fing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "product-service", url = "http://localhost:8082")
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/products/{id}")
    Object getProductById(@PathVariable Long id);
}
