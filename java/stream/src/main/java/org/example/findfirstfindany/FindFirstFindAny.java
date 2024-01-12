package org.example.findfirstfindany;

import java.util.Optional;
import java.util.stream.Stream;

public class FindFirstFindAny {

    public static void main(String[] args) {
        Optional<Integer> any = Stream.of(3, 2, -5, 6)
                .filter(x -> x < 0)
                .findAny();
        System.out.println("any = " + any.get());

        Optional<Integer> first = Stream.of(3, 2, -5, 6)
                .filter(x -> x > 0)
                .findFirst();
        System.out.println("first = " + first.get());
    }
}
