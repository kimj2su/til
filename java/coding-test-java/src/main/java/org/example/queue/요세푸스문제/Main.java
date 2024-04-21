package org.example.queue.요세푸스문제;

import java.util.ArrayDeque;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        int N = 5;
        int K = 2;
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= N; i++) {
            queue.offer(i);
        }

        while (queue.size() > 1) {
            for (int i = 0; i < K - 1; i++) {
                queue.addLast(queue.pollFirst());
            }
            queue.pollFirst();
        }
        System.out.println(queue.poll());
    }
}
