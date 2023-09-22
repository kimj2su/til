package org.example.백준.그래프탐색.dfs와vfs1260;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int V;
    static int[][] graph;
    static boolean[] visited;
    static Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        dfs(V);
        System.out.println();
        visited = new boolean[N + 1];
        bfs(V);
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        M = reader.nextInt();
        V = reader.nextInt();
        graph = new int[N + 1][N + 1];
        visited = new boolean[N + 1];
        for (int i = 0; i < M; i++) {
            int from = reader.nextInt();
            int to = reader.nextInt();
            graph[from][to] = 1;
            graph[to][from] = 1;
        }
    }

    static void dfs(int node) {
        visited[node] = true;
        System.out.print(node + " ");
        for (int i = 1; i <= N; i++) {
            if (graph[node][i] == 1 && !visited[i]) {
                dfs(i);
            }
        }
    }

    static void bfs(int node) {
        queue.add(node);
        visited[node] = true;
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            System.out.print(poll + " ");
            for (int i = 1; i <= N; i++) {
                if (graph[poll][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
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
