package org.example.백준.완전탐색.nqueen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static StringBuilder sb = new StringBuilder();
    static FastReader reader = new FastReader();
    static int[] col;
    static int answer;

    static void input() {
        N = reader.nextInt();
        col = new int[N];
    }

    private static boolean attackable(int r1, int c1, int r2, int c2) {
        if (c1 == c2) return true;
        if (r1 - c1 == r2 - c2) return true;
        if (r1 + c1 == r2 + c2) return true;
        return false;
    }

    static boolean validity_check() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j < i; j++) {
                if (attackable(i, col[i], j, col[j])) {
                    return false;
                }

            }
        }
        return true;
    }

    static void rec_func(int depth) {
        if (depth == N + 1) {
            answer++;
        } else {
            for (int i = 1; i <= N; i++) {
                if (validity_check()) {
                    col[depth] = i;
                    rec_func(depth + 1);
                    col[depth] = 0;
                }
            }
        }
    }

        public static void main (String[] args) {
            input();
            // 1 번째 원소부터 M 번째 원소를 조건에 맞게 고르는 모든 방법을 탐색해줘
            rec_func(1);
            System.out.println(sb.toString());
        }


        static class FastReader {
            BufferedReader br;
            StringTokenizer st;

            public FastReader() {
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
