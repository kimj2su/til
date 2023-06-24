package org.example.partitioningby;

import org.example.EmailService;
import org.example.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PartitioningByPractice {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(13, 2, 101, 203, 304, 402, 305, 349, 2312, 203);
        Map<Boolean, List<Integer>> numberPartitions = numbers.stream()
                .collect(Collectors.partitioningBy(number -> number % 2 == 0));
        System.out.println("numberPartitions = " + numberPartitions);

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
        Map<Boolean, List<User>> userPartitions = users.stream()
                .collect(Collectors.partitioningBy(user -> user.getFriendUserIds().size() > 5));

        EmailService emailService = new EmailService();

        for (User user : userPartitions.get(true)) {
            emailService.sendPlayWithFriendsEmail(user);
        }

        for (User user : userPartitions.get(false)) {
            emailService.sendMakeMoreFriendsEmail(user);
        }

    }
}
