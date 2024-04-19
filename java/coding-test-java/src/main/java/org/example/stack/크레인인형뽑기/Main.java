package org.example.stack.크레인인형뽑기;

import java.util.Stack;

public class Main {

    private static final int[][] board = {
            {0, 0, 0, 0, 0},
            {0, 0, 1, 0, 3},
            {0, 2, 5, 0, 1},
            {4, 2, 4, 4, 2},
            {3, 5, 1, 3, 1}
    };

    private static final int[] moves = {1, 5, 3, 5, 1, 2, 1, 4};

    public int solution(int[][] board, int[] moves) {
        // 1. 각 열에 대한 인형을 담을 lanes 배열을 생성한다.
        Stack<Integer>[] lanes = new Stack[board.length];
        for (int i = 0; i < board.length; i++) {
            lanes[i] = new Stack<>();
        }

        // 2. board를 역순으로 탐색하며, 각 열의 인형을 lanes에 추가한다.
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] > 0) {
                    lanes[j].push(board[i][j]);
                }
            }
        }

        // 3. 인형을 담을 bucket을 생성한다.
        Stack<Integer> bucket = new Stack<>();
        // 4. 사라진 인형의 총 개수를 저장할 변수를 초기화한다.
        int answer = 0;

        // 5. move를 순회하며 각 열에서 인형을 뽑아 bucket에 추가한다.
        for (int move : moves) {
            if (!lanes[move - 1].isEmpty()) {
                int doll = lanes[move - 1].pop();
                // 6. 바구니에 인형이 있고, 가장 위에 있는 인형과 같은 경우
                if (!bucket.isEmpty() && doll == bucket.peek()) {
                    bucket.pop();
                    answer += 2;
                } else {
                    bucket.push(doll);
                }
            }
        }
        return answer;
    }
    public static void main(String[] args) {
        int solution = new Main().solution(board, moves);
        System.out.println("solution = " + solution);
    }
}
