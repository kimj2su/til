package org.example.predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PredicateTest {

    public static void main(String[] args) {
        Predicate<Integer> isPositive = x -> x > 0;
//        System.out.println("isPositive = " + isPositive.test(-1));
//        filter(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), x -> x % 2 == 0).forEach(System.out::println);
//        filter(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), isPositive).forEach(System.out::println);
        List<Integer> inputs = List.of(10, -5, 4, -2, 0, 3);
        System.out.println("Positive number: " + filter(inputs, isPositive));

        // 내가 만든 predicate와 반대인 predicate는 negate()로 만들 수 있다.
        System.out.println("Non-positive number: " + filter(inputs, isPositive.negate()));
        inputs.stream().filter(isPositive.negate()).forEach(System.out::println);

        // or은 내가 만든 predicate와 또 다른 predicate를 합칠 수 있다.
        System.out.println("Non-negative number: " + filter(inputs, isPositive.or( x -> x == 0 )));

        // and는 내가 만든 predicate와 또 다른 predicate의 조건을 맞는 것만 합칠 수 있다.
        System.out.println("Positive even number: " + filter(inputs, isPositive.and(x -> x % 2 == 0)));
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
}
