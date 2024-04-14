package org.example.stack.올바른괄호;

import java.util.ArrayDeque;
import java.util.Stack;

public class Main {

    private final String s = "()()";
    public static void main(String[] args) {
        Main main = new Main();
        boolean solution = solution(main.s);
        System.out.println(solution);
    }

    private static boolean solution(String s) {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        char[] a = s.toCharArray();
        for (char c : a) {
            if (c == '(') {
                stack.push(c);
            } else {
                if (stack.isEmpty() || stack.pop() == c) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
