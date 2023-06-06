package org.example.maxmincount;

import org.example.model.Order;
import org.example.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MaxMinCount {

    public static void main(String[] args) {
        Optional<Integer> max = Stream.of(3, 6, -4)
                .max(Integer::compareTo);
        System.out.println("max = " + max.get());

        User user1 = new User()
                .setId(101)
                .setName("Alice")
                .setVerified(true)
                .setEmailAddress("Alice@naver.com");
        User user2 = new User()
                .setId(102)
                .setName("Bob")
                .setVerified(false)
                .setEmailAddress("Bob@naver.com");
        User user3 = new User()
                .setId(103)
                .setName("Charlie")
                .setVerified(false)
                .setEmailAddress("Charlie@naver.com");
        User user4 = new User()
                .setId(103)
                .setName("David")
                .setVerified(true)
                .setEmailAddress("David@naver.com");

        List<User> users = List.of(user1, user2, user3, user4);

        User firstUser = users.stream()
                .min((u1, u2) -> u1.getName().compareTo(u2.getName()))
                .get();
        System.out.println("firstUser = " + firstUser);

        long count = Stream.of(1, -4, 5, -3, 6)
                .filter(x -> x > 0)
                .count();
        System.out.println("count = " + count);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        user1.setCreatedAt(now.minusDays(2));
        user2.setCreatedAt(now.minusHours(10));
        user3.setCreatedAt(now.minusHours(1));
        user4.setCreatedAt(now.minusHours(27));

        users = List.of(user1, user2, user3, user4);

        long unverifiedUsersIn24Hrs = users.stream()
                .filter(user -> user.getCreateAt().isAfter(now.minusDays(1)))
                .filter(user -> !user.isVerified())
                .count();

        System.out.println("unverifiedUsersIn24Hrs = " + unverifiedUsersIn24Hrs);

        Order order1 = new Order()
                .setId(1)
                .setAmount(BigDecimal.valueOf(2000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.CREATED);
        Order order2 = new Order()
                .setId(2)
                .setAmount(BigDecimal.valueOf(4000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.ERROR);
        Order order3 = new Order()
                .setId(3)
                .setAmount(BigDecimal.valueOf(3000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.PROCESSED);
        Order order4 = new Order()
                .setId(4)
                .setAmount(BigDecimal.valueOf(7000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.ERROR);
        Order order5 = new Order()
                .setId(5)
                .setAmount(BigDecimal.valueOf(2000))
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.IN_PROGRESS);

        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);

        Order order = orders.stream()
                .filter(x -> Order.OrderStatus.ERROR.equals(x.getStatus()))
                .max((o1, o2) -> o1.getAmount().compareTo(o2.getAmount()))
                .get();
        System.out.println("order = " + order);

        BigDecimal bigDecimal = orders.stream()
                .filter(x -> Order.OrderStatus.ERROR.equals(x.getStatus()))
                .map(x -> x.getAmount())
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        System.out.println("bigDecimal = " + bigDecimal);
    }
}
