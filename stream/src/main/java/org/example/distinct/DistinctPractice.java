package org.example.distinct;

import org.example.model.Order;
import org.example.model.OrderLine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DistinctPractice {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, -5, 4, -5, 2, 3);
        List<Integer> list = numbers.stream()
                .distinct()
                .toList();
        System.out.println("list = " + list);

        LocalDateTime now = LocalDateTime.now();
        Order order1 = new Order()
                .setId(1)
                .setAmount(BigDecimal.valueOf(2000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.CREATED)
                .setCreatedAt(now.minusDays(2))
                .setOrderLines(List.of(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000))
                ));
        Order order2 = new Order()
                .setId(2)
                .setAmount(BigDecimal.valueOf(4000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedAt(now.minusDays(3))
                .setOrderLines(List.of(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000))
                ));
        Order order3 = new Order()
                .setId(3)
                .setAmount(BigDecimal.valueOf(3000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.PROCESSED)
                .setCreatedAt(now.minusDays(4))
                .setOrderLines(List.of(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000))
                ));

        List<Order> orders = Arrays.asList(order1, order2, order3);
        List<Long> list1 = orders.stream()
                .map(Order::getCreatedByUserId)
                .distinct()
                .toList();
        System.out.println("list1 = " + list1);
    }
}
