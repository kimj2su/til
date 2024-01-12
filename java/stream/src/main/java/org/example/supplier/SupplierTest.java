package org.example.supplier;

import java.util.function.Supplier;

public class SupplierTest {

    public static void main(String[] args) {
        Supplier<String> myStringSupplier = () -> "Hello World";;
        System.out.println(myStringSupplier.get());

        // 함수가 일급 시민이 되었으므로 파라미터로 넘길 수 있다.
//        Supplier<Double> myRandomDoubleSupplier = () -> Math.random();
        Supplier<Double> myRandomDoubleSupplier = Math::random;
        printRandomDoubles(myRandomDoubleSupplier, 5);
    }

    public static void printRandomDoubles(Supplier<Double> randomSupplier, int count) {
        for (int i = 0; i < count; i ++) {
            System.out.println(randomSupplier.get());
        }
    }
}
