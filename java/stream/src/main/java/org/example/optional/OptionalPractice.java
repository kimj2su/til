package org.example.optional;

import org.example.model.User;

import java.util.Optional;

public class OptionalPractice {

    public static void main(String[] args) {
        String someEmail = "kimjisu3316@naver.com";
        String nullEmail = null;

        Optional<String> maybeEmail1 = Optional.of(someEmail);
        Optional<String> maybeEmail2 = Optional.empty();
        Optional<String> maybeEmail3 = Optional.ofNullable(someEmail);
        Optional<String> maybeEmail4 = Optional.ofNullable(nullEmail);

        String email = maybeEmail1.get();
        System.out.println("email = " + email);

        if (maybeEmail2.isPresent()) {
            System.out.println("maybeEmail2 = " + maybeEmail2.get());
        } else {
            System.out.println("maybeEmail2 is empty");
        }

        String defaultEmail = "default@email.com";
        String email2 = maybeEmail2.orElse(defaultEmail);
        System.out.println("email2 = " + email2);

        String email3 = maybeEmail4.orElseGet(() -> defaultEmail);
        System.out.println("email3 = " + email3);
//        String s = maybeEmail2.orElseThrow(() -> new RuntimeException("이메일이 없습니다."));
//        System.out.println("s = " + s);

        Optional<User> user = Optional.ofNullable(maybeGetUser(true));
        user.ifPresent(u -> System.out.println("user = " + u));

        Optional<Integer> userId = Optional.ofNullable(maybeGetUser(false))
                .map(u -> u.getId());
        userId.ifPresent(System.out::println);

        String userName = Optional.ofNullable(maybeGetUser(true))
                .map(User::getName)
                .map(name -> "the name is " + name)
                .orElse("Name is empty");
        System.out.println("userName = " + userName);
    }

    public static User maybeGetUser(boolean returnUser) {
        if (returnUser) {
            return new User()
                    .setId(1001)
                    .setName("Alice")
                    .setEmailAddress("email.com")
                    .setVerified(false);
        }
        return null;
    }
}
