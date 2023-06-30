package org.example.백준.dp동적계획법.가장긴감소하는부분수열;

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

        for (int i = 0; i < N; i++) {
            dp[i] = 1;

            // 0 ~ i 이전 원소들을 탐색
            for (int j = 0; j < i; j++) {

                // j번째 원소가 i번째 원소보다 작으면서 i번째 dp가 j번째 dp + 1 값보다 작은 경우
                if (A[j] > A[i] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1; // i번째 dp를 j번째 dp + 1 값으로 갱신
                }
            }
        }

        int max = -1;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, dp[i]);
        }
        System.out.println(max);
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        A = new int[N];
        dp = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = reader.nextInt();
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
