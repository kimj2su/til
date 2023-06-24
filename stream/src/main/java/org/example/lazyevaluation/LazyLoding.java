package org.example.lazyevaluation;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LazyLoding {

    public static void main(String[] args) {
        if (lazyOR(() -> returnTrue(), () -> returnFalse())) {
            System.out.println("true");
        }

        Stream<Integer> integerStream = Stream.of(3, -2, 5, 8, -3, 10)
                .filter(x -> x > 0)
                .peek(x -> System.out.println("peeking: " + x))
                .filter(x -> x % 2 == 0);
        System.out.println("BeforeCollect");

        List<Integer> collect = integerStream.collect(Collectors.toList());
        System.out.println("After Collect = " + collect);
        //BeforeCollect
        //peeking: 3
        //peeking: 5
        //peeking: 8
        //peeking: 10
        //After Collect = [8, 10]  // peeking 은 collect 가 호출되기 전까지는 실행되지 않는다.
    }

    public static boolean lazyOR(Supplier<Boolean> x, Supplier<Boolean> y) {
        return x.get() || y.get();
    }

    public static boolean returnTrue() {
        System.out.println("returnTrue");
        return true;
    }

    public static boolean returnFalse() {
        System.out.println("returnFalse");
        return false;
    }
}
