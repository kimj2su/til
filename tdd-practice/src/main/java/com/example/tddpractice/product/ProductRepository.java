package com.example.tddpractice.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    private Long sequence = 0L;
//    private Map<Long, Product> persistence = new HashMap<>();
//
//    public void save(Product product) {
//        product.assignId(++sequence);
//        persistence.put(product.getId(), product);
//    }
}
