package org.example.flatmap;

import org.example.model.Order;
import org.example.model.OrderLine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMapPractice {

    public static void main(String[] args) {
        String[][] cities = new String[][] {
                {"Seoul", "Busan"},
                {"New York", "Washington", "Chicago"},
                {"Paris", "Lyon", "Marseille"}
        };

        Stream<String[]> stream = Arrays.stream(cities);
        Stream<String> stringStream = stream.flatMap(Arrays::stream);
        List<String> collect1 = stringStream.toList();
        System.out.println("collect1 = " + collect1);

        LocalDateTime now = LocalDateTime.now();
        Order order1 = new Order()
                .setId(1)
                .setAmount(BigDecimal.valueOf(2000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.CREATED)
                .setCreatedAt(now.minusDays(2))
                .setOrderLines(List.of(
                        new OrderLine()
                                .setId(10001)
                                .setType(OrderLine.OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setId(10002)
                                .setType(OrderLine.OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(2000))
                ));

        Order order2 = new Order()
                .setId(2)
                .setAmount(BigDecimal.valueOf(2000))
                .setCreatedByUserId(102)
                .setStatus(Order.OrderStatus.CREATED)
                .setCreatedAt(now.minusDays(2))
                .setOrderLines(List.of(
                        new OrderLine()
                                .setId(10003)
                                .setType(OrderLine.OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setId(10004)
                                .setType(OrderLine.OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(2000))
                ));

        List<Order> orders = Arrays.asList(order1, order2);
        List<OrderLine> list = orders.stream() // Stream<Order>
                .map(Order::getOrderLines) // Stream<List<OrderLine>>
                .flatMap(List::stream) // Stream<OrderLine>
                .toList();
        System.out.println("list = " + list);

    }

}
