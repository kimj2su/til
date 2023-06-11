package org.example.reduce;

import org.example.model.Order;
import org.example.model.OrderLine;
import org.example.model.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Reduce {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 4, -2, -5, 3);
        int sum = numbers.stream()
                .reduce((x, y) -> x + y)
                .get();
        System.out.println("sum = " + sum);

        int min = numbers.stream()
                .reduce((x, y) -> x < y ? x : y)
                .get();
        System.out.println("min = " + min);

        int product = numbers.stream()
                .reduce(1, (x, y) -> x * y);
        System.out.println("product = " + product);

        List<String> numberStrList = Arrays.asList("3", "2", "5", "-4");
        int sum2 = numberStrList.stream()
                .mapToInt(Integer::parseInt)
                .reduce(0, (x, y) -> x + y);
        System.out.println("sum2 = " + sum2);

        int sumOfNumberStrList2 = numberStrList.stream()
                .reduce(0, (number, str) -> number + Integer.parseInt(str), (num1, num2) -> num1 | num2);
        System.out.println("sumOfNumberStrList2 = " + sumOfNumberStrList2);


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

        Integer reduce = users.stream()
                .map(User::getFriendUserIds)
                .map(List::size)
                .reduce(0, (x, y) -> x + y);
        System.out.println("reduce = " + reduce);


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

        BigDecimal reduce1 = orders.stream()
                .map(Order::getOrderLines)
                .flatMap(List::stream)
                .reduce(BigDecimal.ZERO, (x, y) -> x.add(y.getAmount()), (x, y) -> x.add(y));
        System.out.println("reduce1 = " + reduce1);

        BigDecimal reduce2 = orders.stream()
                .map(Order::getOrderLines)
                .flatMap(List::stream)
                .map(OrderLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("reduce2 = " + reduce2);

    }
}
