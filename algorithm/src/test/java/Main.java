import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BinaryOperator;


public class Main {

    @Test
    void test() {

        List<User> users = List.of(
                new User(1, "jisu1"),
                new User(2, "jisu2"),
                new User(3, "jisu3"),
                new User(4, "jisu4"),
                new User(5, "jisu5")
        );

        User user = users.stream().reduce(new CompareTo()).get();
        System.out.println("user = " + user);
    }

    @Test
    void test2() {
        test3(1);
    }
    void test3(int k) {
        if (k == 10) {
            return;
        } else {
            test3(k+1);
            System.out.println("k = " + k);
        }
    }

    static class CompareTo implements BinaryOperator<User> {

        @Override
        public User apply(User user, User user2) {
            if (user.getAge() < 3 || user2.getAge() < 3) {
                return user.removerUser();
            }
            return user;
        }
    }

    static class User {
        private int age;
        private String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User removerUser() {
            return null;
        }
    }
}



