package org.example.queue.기능개발;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Main {

    private static final int[] progresses = {93, 30, 55};
    private static final int[] speeds = {1, 30, 5};

    public int[] solution(int[] progresses, int[] speeds) {
        Queue<Integer> answer = new ArrayDeque<>();

        int n = progresses.length;
        // 1. 각 작업의 배포 가능일 계산
        int[] daysLeft = new int[n];
        for (int i = 0; i < n; i++) {
            daysLeft[i] = (int) Math.ceil((100 - progresses[i]) / (double) speeds[i]);
        }

        int count = 0; // 2. 배포될 작업의 수 카운트
        int maxDay = daysLeft[0]; // 3. 배포될 작업의 최대 배포일

        for (int i = 0; i < n; i++) {
            if (daysLeft[i] <= maxDay) { // 4. 배포 가능일이 가장 늦은 배포일보다 빠르면
                count++;
            } else { // 5. 배포 예정일이 기준 배포일보다 느리면
                answer.add(count);
                count = 1;
                maxDay = daysLeft[i];
            }
        }

        answer.add(count); // 6. 마지막 배포될 작업의 수 추가
        return answer.stream().mapToInt(Integer::intValue).toArray();

        // int[] dayOfend = new int[100];
        // int day = -1;
        // for(int i=0; i<progresses.length; i++) {
        //     while(progresses[i] + (day*speeds[i]) < 100) {
        //         day++;
        //     }
        //     dayOfend[day]++;
        // }
        // return Arrays.stream(dayOfend).filter(i -> i!=0).toArray();
    }
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(Arrays.toString(main.solution(progresses, speeds)));
    }
}
