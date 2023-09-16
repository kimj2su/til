package org.example.백준.완전탐색.N과M.N과M15654;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main {

    static int N;
    static int M;
    static int[] arr;
    static int[] answer;
    static boolean[] visit;
    static String[] str;

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        Arrays.sort(arr);
        dfs(0);
    }

    private static void dfs(int depth) {
        if (depth == M) {
            for (int val : answer) {
                System.out.print(val + " ");
            }
            System.out.println();
            return;
        }
        for (int i = 0; i < N; i++) {
            if (visit[i]) continue;
            visit[i] = true;
            answer[depth] = arr[i];
            dfs(depth + 1);
            visit[i] = false;
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
