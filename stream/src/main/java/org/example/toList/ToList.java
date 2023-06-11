package org.example.toList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToList {
    public static void main(String[] args) {
        List<Integer> collect = Stream.of(3, -5, 3, 4, 5)
                .collect(Collectors.toList());
        System.out.println("collect = " + collect);

        Set<Integer> collect1 = Stream.of(3, -3, 3, 4, 5)
                .collect(Collectors.toSet());
        System.out.println("collect1 = " + collect1);

        List<Integer> collect2 = Stream.of(3, -3, 3, 4, 5)
                .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toList()));
        System.out.println("collect2 = " + collect2);
        Set<Integer> collect3 = Stream.of(3, -3, 3, 4, 5)
                .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toSet()));
        System.out.println("collect3 = " + collect3);

        Integer collect4 = Stream.of(3, -3, 3, 4, 5)
                .collect(Collectors.reducing(0, (x, y) -> x + y));
        System.out.println("collect4 = " + collect4);
    }
}
