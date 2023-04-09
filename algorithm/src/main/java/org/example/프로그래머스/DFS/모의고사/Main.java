package org.example.프로그래머스.DFS.모의고사;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        int[] corrects = new int[3];
        int max = 0;
        int[] answers = {1, 3, 2, 4, 2};

        for (int problem = 0; problem < answers.length; problem++) {
            int answer = answers[problem];

            //각 수포자별로 정답세기
            for (int person = 0; person < 3; person++) {
                int picked = getPicked(person, problem);
                if (answer == picked) {
                    //답과 내가 선택한 답이 같으면 더해준다.
                    System.out.println("corrects = " + Arrays.toString(corrects));
                    if (++corrects[person] > max) {
                        System.out.println("corrects = " + Arrays.toString(corrects));
                        System.out.println("1.max = " + max);
                        max = corrects[person];
                        System.out.println("2.max = " + max);
                    }
                }
            }
        }
        System.out.println("corrects = " + Arrays.toString(corrects));
        final int maxCorrects = max;
        int[] ints = IntStream.range(0, 3)
                .filter(i -> corrects[i] == maxCorrects)
                .map(i -> i + 1)
                .toArray();
        System.out.println("ints = " + Arrays.toString(ints));
    }

    private static final int[][] RULES = {
            {1, 2, 3, 4, 5},
            {2, 1, 2, 3, 2, 4, 2, 5},
            {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}
    };

    private static int getPicked(int person, int problem) {
        int[] rule = RULES[person];
        int index = problem % rule.length;
        return rule[problem];
    }

}
