package org.example.백준.dp동적계획법.연속합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] A;

    static int[] dp;

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        dp[0] = A[0];
        int max = dp[0];
        for (int i = 1; i < N; i++) {
            dp[i] = Math.max(dp[i - 1] + A[i], A[i]);
            max = Math.max(max, dp[i]);
        }
        System.out.println("max = " + max);
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        A = new int[N];
        dp = new int[N];
        for (int i = 0; i < N; i++) {
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
