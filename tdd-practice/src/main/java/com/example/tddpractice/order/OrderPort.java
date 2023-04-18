package com.example.tddpractice.order;

import com.example.tddpractice.product.Product;

interface OrderPort {
    Product getProductById(final Long productId);

    void save(final Order order);
}
