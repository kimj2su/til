package org.example.백준.dp동적계획법.계단오르기2579;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] A;

    static int[] dp;

    public static void main(String[] args) {
        input();
        getResult();
    }

    public static void getResult() {
        dp[1] = A[1];

        if (N >= 2) {
            dp[2] = A[1] + A[2];
        }
        for (int i = 3; i <= N; i++) {
//            dp[i] = Math.max(dp[i - 2] + A[i], dp[i - 3] + A[i - 1] + A[i]);
//            dp[i] = Math.max(dp[i - 2] + A[i], dp[i - 1] + A[i]);
            dp[i] = Math.max(dp[i - 2], dp[i - 3] + A[i - 1]) + A[i];
        }
        System.out.println("Arrays.toString(dp) = " + Arrays.toString(dp));
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        A = new int[N + 1];
        dp = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = reader.nextInt();
//            dp[i] = A[i];
        }

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

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
