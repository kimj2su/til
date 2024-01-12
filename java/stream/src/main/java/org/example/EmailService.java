package org.example;

import org.example.model.User;

import java.util.Optional;

public class EmailService {
    public void sendPlayWithFriendsEmail(User user) {
        Optional.ofNullable(user.getEmailAddress())
                .ifPresent(email -> System.out.println("Play With Friends email to " + email));
    }

    public void sendMakeMoreFriendsEmail(User user) {
        Optional.ofNullable(user.getEmailAddress())
                .ifPresent(email -> System.out.println("Sending email to " + email));
    }

    public void sendVerifyYourEmail(User user) {
        Optional.ofNullable(user.getEmailAddress())
                .ifPresent(email -> System.out.println("Verify your email to " + email));
    }
}