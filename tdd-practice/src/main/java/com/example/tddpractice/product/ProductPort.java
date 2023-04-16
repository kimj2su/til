package com.example.tddpractice.product;

interface ProductPort {
    void save(final Product product);

    Product getProduct(Long productId);
}
