package org.example.프로그래머스.DFS.네트워크;

import java.util.Stack;

public class Main {
    private void visitAll(int computer, int[][] computers, boolean[] isVisited) {
        // (2) 초기 상태
        Stack<Integer> stack = new Stack<>();
        stack.push(computer);

        // (3) 탐색 진행
        while (!stack.isEmpty()) {
            int c = stack.pop();

            // (4) 중복 검사
            if (isVisited[c]) continue;
            isVisited[c] = true;

            // (6) 전이 상태 생성
            for (int next = 0; next < computers[c].length; next++) {
                // (8) 유효성 검사
                if (computers[c][next] == 0) continue;
                // (9) 상태 전이
                stack.push(next);
            }
        }
    }
    public int solution(int n, int[][] computers) {
        // (1) 방문 검사 배열
        boolean[] isVisited = new boolean[n];
        int answer = 0;

        for (int i = 0; i < n; i++) {
            if (isVisited[i]) continue;
            visitAll(i, computers, isVisited);
            answer++;
        }

        return answer;
    }
    public static void main(String[] args) {
        int n = 3;
        int[][] computers = {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        Main main = new Main();
        System.out.println(main.solution(n, computers));
    }
}
