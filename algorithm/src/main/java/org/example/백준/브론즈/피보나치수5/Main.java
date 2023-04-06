package org.example.백준.브론즈.피보나치수5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.BinaryOperator;

public class Main {
    public static int result(int n ) {
        int ppre = 0;
        int pre = 1;
        int current = 0;

        if (n == 0) return 0;
        if (n == 1) return 1;

        for (int i = 2; i <= n; i++) {
            current = ppre + pre;
            System.out.println("current = " + current);
            ppre = pre;
            System.out.println("ppre = " + ppre);
            pre = current;
            System.out.println("pre = " + pre);
        }

        return current;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int result = result(n);
        System.out.println("result = " + result);

    }
}
