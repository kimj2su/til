package org.example.scopeclosurecurry;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ScopeClosureCurry {
    public static void main(String[] args) {
        Supplier<String> supplier = getSupplier();
        System.out.println("supplier.get() = " + supplier.get());

        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
//        Function<Integer, Function<Integer, Integer>> curriesAdd = x -> (y -> x + y);
        Function<Integer, Function<Integer, Integer>> curriesAdd = x -> y -> x + y;

        // addThree 는 어딜가도 3이란 숫자를 기억하고 있다.
        Function<Integer, Integer> addThree = curriesAdd.apply(3);
        Integer apply = addThree.apply(10);
        System.out.println("apply = " + apply);
    }

    public static Supplier<String> getSupplier() {
        String name = "Hello";
        Supplier<String> supplier = () -> {
            String world = "world";
            return name + ", " + world;
        };
        return supplier;
    }
}
