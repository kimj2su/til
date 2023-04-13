package org.example.프로그래머스.BFS.단어변환;

import java.util.LinkedList;
import java.util.Queue;

public class Main {

    private static class State {
        public final String word;
        public final int step;

        public State(String word, int step) {
            this.word = word;
            this.step = step;
        }
    }
    public int solution(String begin, String target, String[] words) {
        // 방문 배열 생성
        boolean[] isVisited = new boolean[words.length];

        // 초기 상태
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(begin, 0));

        //4. 탐색진행
        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.word.equals(target)) {
                return state.step;
            }

            for (int i = 0; i < words.length; i++) {
                String next = words[i];
                if (!isConvertable(state.word, next)) {
                    continue;
                }
                if (isVisited[i]) {
                    continue;
                }
                isVisited[i] = true;
                queue.add(new State(next, state.step + 1));
            }
        }
        return 0;
    }

    // 두 단어는 하나의 문자만 다를 때 변환될 수 있다.
    private boolean isConvertable(String src, String dst) {
        char[] srcArr = src.toCharArray();
        char[] dstArr = dst.toCharArray();

        int diff = 0;
        for (int i = 0; i < srcArr.length; i++) {
            if (srcArr[i] != dstArr[i]) diff ++;
        }

        return diff == 1;
    }

    public static void main(String[] args) {
        String begin = "hit";
        String target = "cog";
        String[] words = {"hot", "dot", "dog", "lot", "log", "cog"};
        Main main = new Main();
        System.out.println(main.solution(begin, target, words));
    }
}
