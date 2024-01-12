package org.example.stream;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamPractice {

    public static void main(String[] args) {
        Stream<String> nameStream = Stream.of("김철수", "홍길동", "김영희");
        List<String> collect = nameStream.collect(Collectors.toList());
        System.out.println("collect = " + collect);


        String[] strings = {"김철수", "홍길동", "김영희"};
        Stream<String> stream = Arrays.stream(strings);
        List<String> collect1 = stream.collect(Collectors.toList());
        System.out.println("collect1 = " + collect1);

        Set<Integer> numberSet = new HashSet<>(Arrays.asList(3, 5, 7));
        Stream<Integer> stream1 = numberSet.stream();
        List<Integer> list = stream1.toList();
        System.out.println("list = " + list);
    }
}
