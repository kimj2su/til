package org.example.프로그래머스.카카오인턴십2022.두큐합같게만들기;

import java.util.*;
class Main {

    static Queue<Long> queue1 = new LinkedList<>();
    static Queue<Long> queue2 = new LinkedList<>();
    //L > R이라면, queue1의 원소를 queue2로 넘겨줍니다.
    // L < R이라면, queue2의 원소를 queue1로 넘겨줍니다.
    public int solution(int[] arr1, int[] arr2) {
        int answer = 0;
        inputQueue(arr1, arr2);
        while (true) {

            long L = 0;
            long R = 0;

            for (int i = 0; i < queue1.size(); i++) {
                long tmp = queue1.poll();
                L += tmp;
                queue1.add(tmp);
            }

            for (int i = 0; i < queue2.size(); i++) {
                long tmp = queue2.poll();
                R += tmp;
                queue2.add(tmp);
            }

            if (L == R) {
                break;
            } else if (L > R) {
                long tmp = queue1.poll();
                queue2.add(tmp);
                answer++;
            } else if (L < R) {
                long tmp = queue2.poll();
                queue1.add(tmp);
                answer++;
            }

            if (answer > arr1.length * 3) {
                answer = -1;
                break;
            }
        }

        return answer;
    }

    private void inputQueue(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            queue1.add((long) arr1[i]);
            queue2.add((long) arr2[i]);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        int solution = main.solution(new int[]{1, 1}, new int[]{1, 5});
        System.out.println("solution = " + solution);
    }
}
