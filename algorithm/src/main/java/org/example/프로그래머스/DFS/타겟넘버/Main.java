package org.example.프로그래머스.DFS.타겟넘버;

import java.util.Stack;

public class Main {
    public int solution(int[] numbers, int target) {
        Stack<State> stack = new Stack<>();
        stack.push(new State(0, 0));
        
        int count = 0;

        while (!stack.isEmpty()) {
            State stage = stack.pop();
            if (stage.index == numbers.length) {
                if (stage.acc == target) count++;
                continue;
            }
            
            //+를 선택한 경우
            stack.push(new State(stage.index + 1, stage.acc - numbers[stage.index]));

            //-를 선택한 경우
            stack.push(new State(stage.index + 1, stage.acc + numbers[stage.index]));
        }
        
        return count;
    }
    public static void main(String[] args) {
        int[] numbers = {1, 1, 1, 1, 1};
        int target = 3;
        Main main = new Main();
        int solution = main.solution(numbers, target);
        System.out.println("solution = " + solution);
    }

    private static class State {
        public final int index;
        public final int acc;

        public State(int index, int acc) {
            this.index = index;
            this.acc = acc;
        }
    }
    
}
