package org.example.groupby;

import org.example.model.Order;
import org.example.model.OrderLine;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupBy {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(13, 2, 101, 203, 304, 402, 305, 349, 2312, 203);
//        numbers.stream()
//                .filter(n -> n > 100)
//                .map(n -> n / 10 * 10)
//                .distinct()
//                .sorted()
//                .forEach(System.out::println);
        Map<Integer, List<Integer>> collect = numbers.stream()
                .collect(Collectors.groupingBy(number -> number % 10));
        System.out.println("collect = " + collect);

        Map<Integer, Set<Integer>> collect1 = numbers.stream()
                .collect(Collectors.groupingBy(number -> number % 10, Collectors.toSet()));
        System.out.println("collect1 = " + collect1);

        Map<Integer, List<String>> collect2 = numbers.stream()
                .collect(Collectors.groupingBy(number -> number % 10, Collectors.mapping(number -> "ubit digit is " + number, Collectors.toList())));
        System.out.println("collect2 = " + collect2);

        Order order1 = new Order()
                .setId(1)
                .setAmount(BigDecimal.valueOf(2000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.CREATED)
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
                .setOrderLines(List.of(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000))
                ));

        Order order4 = new Order()
                .setId(3)
                .setAmount(BigDecimal.valueOf(1000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.PROCESSED)
                .setOrderLines(List.of(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000))
                ));

        List<Order> orders = Arrays.asList(order1, order2, order3, order4);

        Map<Order.OrderStatus, List<Order>> collect3 = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus));
        System.out.println("collect3 = " + collect3);

        Map<Order.OrderStatus, BigDecimal> collect4 = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus,
                        Collectors.mapping(Order::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        System.out.println("collect4 = " + collect4);
    }
}
