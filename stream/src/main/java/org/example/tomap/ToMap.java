package org.example.tomap;

import org.example.model.Order;
import org.example.model.OrderLine;
import org.example.model.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToMap {

    public static void main(String[] args) {
        Map<Integer, String> numberMap = Stream.of(3, -5, -4, 4, 5)
                .collect(Collectors.toMap(x -> x, x -> "Number is " + x));

        Map<Integer, String> numberMap2 = Stream.of(3, -5, -4, 4, 5)
                .collect(Collectors.toMap(Function.identity(), x -> "Number is " + x));
        System.out.println("numberMap = " + numberMap2);

        User user1 = new User()
                .setId(101)
                .setName("Alice")
                .setVerified(true)
                .setEmailAddress("Alice@naver.com")
                .setFriendUserIds(List.of(101, 102, 103, 104));
        User user2 = new User()
                .setId(102)
                .setName("Bob")
                .setVerified(false)
                .setEmailAddress("Bob@naver.com")
                .setFriendUserIds(List.of(101, 102, 103));
        User user3 = new User()
                .setId(103)
                .setName("Charlie")
                .setVerified(false)
                .setEmailAddress("Charlie@naver.com")
                .setFriendUserIds(List.of(101, 102, 103));

        List<User> users = List.of(user1, user2, user3);

        Map<Integer, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        System.out.println("userMap = " + userMap);

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

        List<Order> orders = Arrays.asList(order1, order2, order3);

        Map<Long, Order.OrderStatus> collect = orders.stream()
                .collect(Collectors.toMap(Order::getId, Order::getStatus));
        System.out.println("collect = " + collect);
    }
}
