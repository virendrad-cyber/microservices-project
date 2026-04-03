package com.microservices.productservice.controller;

import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.dto.ProductResponse;
import com.microservices.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id,
                                  @RequestBody ProductRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
