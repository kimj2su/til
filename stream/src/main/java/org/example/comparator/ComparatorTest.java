package org.example.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorTest {

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        users.add(new User(3, "Alice"));
        users.add(new User(1, "Charlie"));
        users.add(new User(2, "Bob"));

        Comparator<User> idComparator = (User u1, User u2) -> {
            return u1.getId() - u2.getId();
        };

        users.sort(idComparator);
        System.out.println("users = " + users);

        // String이 알파벳순으로 정렬되는 것을 이용한 Comparator
        Collections.sort(users, Comparator.comparing(User::getName));
        System.out.println("users = " + users);
    }

    private static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
