package org.example.foreach;

import org.example.EmailService;
import org.example.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ForEachPractice {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(3, 5, 2, 1);
        numbers.forEach(number -> System.out.println(number));

        User user1 = new User()
                .setId(101)
                .setName("Alice")
                .setVerified(true)
                .setEmailAddress("Alice@naver.com")
                .setFriendUserIds(List.of(101, 102, 103, 104, 105, 106));
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
        List<User> users = Arrays.asList(user1, user2, user3);

        EmailService emailService = new EmailService();
        users.stream()
                .filter(x -> !x.isVerified())
                .forEach(emailService::sendVerifyYourEmail);

        IntStream.range(0, users.size()).forEach(i -> {
            User user = users.get(i);
            System.out.println("user = " + user);
        });
    }
}
