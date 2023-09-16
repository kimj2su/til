package org.example.백준.완전탐색.퇴사14501;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] days;
    static int[] moneys;

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        int[] dp = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            int money = 0;
            int validateDay = i + days[i];
            money = moneys[i];
            while (validateDay <= N + 1) {

                money += moneys[validateDay];
                validateDay += days[validateDay];
                dp[i] = money;
            }
        }

        Arrays.sort(dp);
        System.out.println("dp = " + Arrays.toString(dp));
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        days = new int[N + 1];
        moneys = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            days[i] = reader.nextInt();
            moneys[i] = reader.nextInt();
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
