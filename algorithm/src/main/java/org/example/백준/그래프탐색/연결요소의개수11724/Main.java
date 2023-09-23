package org.example.백준.그래프탐색.연결요소의개수11724;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
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

    // 메인에서 실행된 dfs는 서로 연결안된 노드들을 탐색하고, 재귀 함수인경우 서로 연결된 노드이다.
    private static void getResult() {
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                // dfs(i);
                // stackDfs(i);
                bfs(i);
                result++;
            }
        }
        System.out.println(result);
    }

    private static void dfs(int index) {
        visited[index] = true;
        for (int i = 1; i <= N; i++) {
            if (graph[index][i] == 1 && !visited[i]) {
                dfs(i);
            }
        }
    }

    static Stack<Integer> stack = new Stack<>();
    private static void stackDfs(int index) {
        stack.push(index);
        visited[index] = true;

        while (!stack.isEmpty()) {
            int node = stack.pop();
            result++;
            for (int i = 1; i <= N; i++) {
                if (graph[node][i] == 1 && !visited[i]) {
                    stack.push(i);
                    visited[i] = true;
                }
            }
        }
    }

    static Queue<Integer> queue = new LinkedList<>();
    private static void bfs(int index) {
        queue.offer(index);
        visited[index] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int i = 1; i <= N; i++) {
                if (graph[node][i] == 1 && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
        }
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        M = reader.nextInt();
        graph = new int[N + 1][N + 1];
        visited = new boolean[N + 1];
        for (int i = 1; i <= M; i++) {
            int a = reader.nextInt();
            int b = reader.nextInt();
            graph[a][b] = graph[b][a] = 1;
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
