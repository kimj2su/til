package org.example.stack.주식가격;

import java.util.Stack;

class Main {

    public static int[] solution(int[] prices) {
        int n = prices.length;
        int[] answer = new int[n]; // 1. 가격이 떨어지지 않은 기간을 저장할 배열

        // 스택을 사용해 이전 가격과 현재 가격 비교
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        for (int i = 1; i < n; i++) {
            while (!stack.isEmpty() && prices[i] < prices[stack.peek()]) {
                // 3. 가격이 떨어졌으므로 이전 가격의 기간 계산
                int top = stack.pop();
                answer[top] = i - top;
            }

            stack.push(i);
        }

        // 4. 스택에 남아 있는 가격들은 가격이 떨이지지 않은 경우
        while (!stack.isEmpty()) {
            int top = stack.pop();
            answer[top] = n - 1 - top;
        }

        return answer;
    }
    public static void main(String[] args) {
        Main main = new Main();
        int[] prices = {1, 2, 3, 2, 3};
        int[] result = main.solution(prices);
        for (int i : result) {
            System.out.print(i + " ");
        }
    }
}
