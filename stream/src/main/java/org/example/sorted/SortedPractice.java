package org.example.sorted;

import org.example.model.Order;
import org.example.model.OrderLine;
import org.example.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortedPractice {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 5, 7, 1, 2, 4, 6);
        List<Integer> collect = numbers.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("collect = " + collect);

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
        List<User> users = List.of(user2, user1, user3);
        System.out.println("users = " + users);
        List<User> collect1 = users.stream()
                .sorted((u1, u2) -> u1.getName().compareTo(u2.getName()))
                .collect(Collectors.toList());
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
        List<Order> collect2 = orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedAt))
                .toList();
        System.out.println("collect2 = " + collect2);
    }
}
