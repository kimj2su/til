package org.example.백준.완전탐색.N과M.N과M15655;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main {

    static int N;
    static int M;
    static int[] arr;
    static int[] answer;
    static boolean[] visit;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        Arrays.sort(arr);
        dfs(0, 0);
    }

    private static void dfs(int depth, int start) {
        if (depth == M) {
            for (int val : answer) {
                sb.append(val).append(" ");
            }
            sb.append("\n");
            return;
        }
        // 고른 수열은 오름차순이어야 한다. 조건으로 start를 증가시켜줘서 매번 1, 7, 8, 9 에서 다음꺼를 answer 배열에 넣을 수 있도록 해준다.
        for (int i = start; i < N; i++) {
            answer[depth] = arr[i];
            dfs(depth + 1, i + 1);
        }
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        M = reader.nextInt();
        arr = new int[N];
        visit = new boolean[N];
        answer = new int[M];
        for (int i = 0; i < N; i++) {
            arr[i] = reader.nextInt();
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
