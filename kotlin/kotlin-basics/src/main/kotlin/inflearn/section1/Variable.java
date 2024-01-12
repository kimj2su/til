package inflearn.section1;

import dataclass.Person;
import org.jetbrains.annotations.Nullable;

public class Variable {

    public static void main(String[] args) {
        long number1 = 10L;
        final long number2 = 10L;

        Long number3 = 1_000L;
        Person person = new Person("김지수");
    }

    public static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        @Nullable
        public String getName() {
            return name;
        }
    }
}
