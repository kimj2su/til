package org.example.stack.십진수를이진수로변환하기;

import java.util.Stack;

class Main {
    public static void main(String[] args) {
        int decimal = 10;
        String binaryString = Integer.toBinaryString(decimal);
        System.out.println("binaryString = " + binaryString);
        Main main = new Main();
        String solution = main.solution(decimal);
        System.out.println(solution);
    }

    private String solution(int decimal) {
        StringBuilder sb = new StringBuilder();
        Stack<Integer> stack = new Stack<>();
        while (decimal > 0) {
            int remainder = decimal % 2;
            stack.push(remainder);
            decimal /= 2;
        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    // private String solution(int n) {
    //     StringBuilder sb = new StringBuilder();
    //     while (n > 0) {
    //         sb.append(n % 2);
    //         n /= 2;
    //     }
    //     return sb.reverse().toString();
    // }
}
