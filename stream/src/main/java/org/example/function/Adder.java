package org.example.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Adder implements Function<Integer, Integer> {
    @Override
    public Integer apply(Integer integer) {
        return integer + 10;
    }

    public static void main(String[] args) {
//        Function<Integer, Integer> adder = new Adder();
//        Integer apply = adder.apply(10);
//        System.out.println("apply = " + apply);

        Function<Integer, Integer> adder = (Integer x) -> {
            return x + 10;
        };
        Integer apply = adder.apply(10);
        System.out.println("apply = " + apply);

        Function<Integer, Integer> adder2 = x ->  x + 10;;
        Integer adder2Result = adder2.apply(10);
        System.out.println("adder2Result = " + adder2Result);


        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
        int result = add.apply(10, 20);
        System.out.println("result = " + result);


        TriFunction<Integer, Integer, Integer, Integer> triFunction = (x, y, z) -> x + y + z;

        Integer triFunctionResult = triFunction.apply(10, 20, 30);
        System.out.println("triFunctionResult = " + triFunctionResult);
    }
}
