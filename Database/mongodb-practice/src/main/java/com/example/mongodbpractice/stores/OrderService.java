package com.example.mongodbpractice.stores;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrdersRepository ordersRepository;

  public OrderService(OrdersRepository ordersRepository) {
    this.ordersRepository = ordersRepository;
  }

  public Orders createOrder(Orders order) {
    return ordersRepository.save(order);
  }

}
