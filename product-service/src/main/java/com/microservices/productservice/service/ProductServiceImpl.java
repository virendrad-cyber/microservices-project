package com.microservices.productservice.service;


import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.dto.ProductResponse;
import com.microservices.productservice.entity.Product;
import com.microservices.productservice.repository.ProductRepository;
import com.microservices.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        return map(repo.save(product));
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return map(product);
    }

    @Override
    public List<ProductResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        return map(repo.save(product));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

//    @Override
//    public void reduceStock(Long productId, int quantity) {
//
//        Product product = repo.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        if (product.getQuantity() < quantity) {
//            throw new RuntimeException("Insufficient stock");
//        }
//
//        product.setQuantity(product.getQuantity() - quantity);
//
//        repo.save(product);
//    }

    private ProductResponse map(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .quantity(p.getQuantity())
                .build();
    }
}
