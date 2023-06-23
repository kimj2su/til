package org.example.consumer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerTest {
    public static void main(String[] args) {
        Consumer<String> myStringConsumer = (s) -> System.out.println(s);

        myStringConsumer.accept("Hello World");

        // imuatableList
        List<Integer> inputs = Arrays.asList(4, 2, 3);
        Consumer<Integer> myIntegerProcessor = x -> System.out.println("Processing integer " + x);
//        process(inputs, myIntegerProcessor);

        Consumer<Integer> myDifferentIntegerProcessor = x -> System.out.println("Processing integer " + x + " differently");
        process(inputs, myDifferentIntegerProcessor);
        process(inputs, myIntegerProcessor.andThen(myDifferentIntegerProcessor));

        Consumer<Double> myDoubleProcessor = x -> System.out.println("Processing double " + x);
        List<Double> doubleInputs = Arrays.asList(4.0, 2.0, 3.0);
        process(doubleInputs, myDoubleProcessor);
    }

//    public static void process(List<Integer> inputs, Consumer<Integer> processor) {
//        for (Integer input : inputs) {
//            processor.accept(input);
//        }
//    }

    public static <T> void process(List<T> inputs, Consumer<T> processor) {
        for (T input : inputs) {
            processor.accept(input);
        }
    }
}
