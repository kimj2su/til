package org.example.백준.greedy.거스름돈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N;

    static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
    }

    static void operation(int N) {
        int[] coinArr = {500, 100, 50, 10, 5, 1};
        int result = 1000 - N;
        System.out.println("result = " + result);
        int num = 0;
        for (int i = 0; i < 6; i++) {
            if (result / coinArr[i] > 0) {
                num += result / coinArr[i];
                result = result % coinArr[i];
            }
        }

        System.out.println("result = " + num);
    }

    public static void main(String[] args) {
        input();
        operation(N);
    }

    static class Reader {
        BufferedReader br;
        StringTokenizer st;

        public Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
