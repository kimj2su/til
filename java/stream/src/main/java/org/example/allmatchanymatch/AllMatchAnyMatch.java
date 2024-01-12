package org.example.allmatchanymatch;

import java.util.List;

public class AllMatchAnyMatch {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(3, -4, 2, 7, 9);
        boolean allPositive = numbers.stream()
                .allMatch(n -> n > 0);
        System.out.println("allPositive = " + allPositive);

        boolean anyMatch = numbers.stream()
                .anyMatch(n -> n > 0);
        System.out.println("anyMatch = " + anyMatch);
    }
}
