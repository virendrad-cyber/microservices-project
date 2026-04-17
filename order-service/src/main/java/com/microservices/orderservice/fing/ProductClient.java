package com.microservices.orderservice.fing;

import com.microservices.orderservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "product-service", url = "http://localhost:8082")
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponse getProductById(@PathVariable Long id);

    @PutMapping("/products/{id}/reduce")
    void reduceStock(@PathVariable("id") Long id,
                     @RequestParam("quantity") int quantity);
}
