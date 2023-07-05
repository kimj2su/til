package org.example.백준.완전탐색.dfs와bfs1260;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static boolean[][] graph;
    static boolean[] visited;
    static int N, M, V;
    static ArrayList<Integer> queue;

    public static void main(String[] args) throws IOException {
        // 0. 입력 및 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());

        // 1. graph에 연결 정보 채우기
        graph = new boolean[N + 1][N + 1];
        visited = new boolean[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()); // u -> v
            int y = Integer.parseInt(st.nextToken());
            graph[x][y] = true;
            graph[y][x] = true;
        }

        // 2. dfs(재귀함수 호출)
        dfs(V);
        System.out.println();

        // 3. bfs
        bfs();

        bw.flush();
        bw.close();
        br.close();
    }

    public static void dfs(int idx) {
        visited[idx] = true;
        System.out.print(idx + " ");

        for (int i = 1; i <= N; i++) {
            // graph[idx][i] graph 의 index와 i가 연결되어 있을 떼
            if (graph[idx][i] && !visited[i]) {
                dfs(i);
            }
        }
    }

    public static void bfs() {
        queue = new ArrayList<>();
        visited = new boolean[N + 1];
        queue.add(V);
        visited[V] = true;
        while (!queue.isEmpty()) {
            int idx = queue.remove(0);
            System.out.print(idx + " ");

            for (int i = 1; i <= N; i++) {
                if (graph[idx][i] && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
    }
}
