package org.example.streammap;

import org.example.model.User;

import java.util.List;
import java.util.stream.Stream;

public class MapTest {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(3, 6, -4);
        Stream<Integer> numberStream = numbers.stream();
        numberStream.map(n -> n * 2)
                .forEach(System.out::println);

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

        Stream<String> nameStream = users.stream()
                .map(User::getEmailAddress);
        nameStream.forEach(System.out::println);
    }
}
