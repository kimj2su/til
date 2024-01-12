package org.example.streamfilter;

import org.example.model.Order;
import org.example.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FilterTest {
    public static void main(String[] args) {
        Stream<Integer> numberStream = Stream.of(3, -5, 7, 10, -3);
        Stream<Integer> filteredBumberStream = numberStream.filter(n -> n > 0);
        List<Integer> filteredNumberList = filteredBumberStream.toList();
        System.out.println(filteredNumberList);

        List<Integer> newFilteredNumbers = Stream.of(3, -5, 7, 10, -3)
                .filter(n -> n > 0)
                .toList();
        System.out.println("newFilteredNumbers = " + newFilteredNumbers);


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
                .setVerified(true)
                .setEmailAddress("Charlie@naver.com");

        List<User> users = List.of(user1, user2, user3);
        List<User> verifiedUsers = users.stream()
                .filter(User::isVerified)
                .toList();
        System.out.println("verifiedUsers = " + verifiedUsers);

        List<User> unverifiedUsers = users.stream()
                .filter(user -> !user.isVerified())
                .toList();
        System.out.println("unverifiedUsers = " + unverifiedUsers);


        Order order1 = new Order()
                .setId(1)
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.CREATED);
        Order order2 = new Order()
                .setId(2)
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.ERROR);
        Order order3 = new Order()
                .setId(3)
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.PROCESSED);
        Order order4 = new Order()
                .setId(4)
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.ERROR);
        Order order5 = new Order()
                .setId(5)
                .setCreatedByUserId(101)
                .setStatus(Order.OrderStatus.IN_PROGRESS);

        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);
        List<Order> filteredOrders = orders.stream().filter(order -> Order.OrderStatus.ERROR.equals(order.getStatus()))
                .toList();
        System.out.println("filteredOrders = " + filteredOrders);
    }
}
