package org.example.백준.그래프탐색.바이러스2606;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int[][] graph;
    static boolean[] visited;
    static int result;
    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        dfs(1);
        System.out.println(result - 1);
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        M = reader.nextInt();
        graph = new int[N + 1][N + 1];
        visited = new boolean[N + 1];
        for (int i = 1; i <= M; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            graph[u][v] = graph[v][u] = 1;
        }
    }

    static void dfs(int index) {
        visited[index] = true;
        result++;
        for (int i = 1; i <= N; i++) {
            if (graph[index][i] == 1 && !visited[i]) {
                dfs(i);
            }
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
