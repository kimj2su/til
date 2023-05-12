package org.example.백준.완전탐색.행운의바퀴;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int K;
    static char[] wheel;

    public static void main(String[] args) {
        input();
        getResult();
    }

    static Reader reader = new Reader();
    private static void getResult() {
        int currentIndex = 0;
        while (N-- > 0) {
            int step = reader.nextInt(); // 시계방향으로 step만큼 이동
            char nextAlphabet = reader.next().charAt(0);
            int nextIndex = ((currentIndex - step) % N + N) % N; // 이동한 칸의 index (예정)
            if (wheel[nextIndex] == '?') wheel[nextIndex] = nextAlphabet;
            else if (wheel[nextIndex] != nextAlphabet) {
                System.out.println("!");
                return;
            }
            currentIndex = nextIndex; // 현재 화살표의 위치
        }

        boolean[] used = new boolean[26];
        for (int i = 0; i < N; i++) {
            if (wheel[i] == '?') continue;
            if (used[wheel[i] - 'A']) {
                System.out.println("!");
                return;
            }
            used[wheel[i] - 'A'] = true;
        }

        for (int i = 0; i < N; i++) {
            System.out.print(wheel[(currentIndex + i)] % N);
        }
    }

    private static void input() {
        N = reader.nextInt();
        K = reader.nextInt();
        wheel = new char[N];
        Arrays.fill(wheel, '?');
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
