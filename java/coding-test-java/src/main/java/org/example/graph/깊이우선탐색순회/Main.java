package org.example.graph.깊이우선탐색순회;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static ArrayList<Integer>[] adjList;
    private static boolean[] visited;
    private static ArrayList<Integer> answer;

    private static int[][] graph = {
            {1, 2},
            {2, 3},
            {3, 4},
            {4, 5}
    };

    private static int start = 1;
    private static int n = 5;

    private static int[] solution(int[][] graph, int start, int n) {
        adjList = new ArrayList[n + 1];
        visited = new boolean[n + 1];
        answer = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int[] edge : graph) {
            int u = edge[0];
            int v = edge[1];
            adjList[u].add(v);
            adjList[v].add(u);
        }

        dfs(start);
        return answer.stream().mapToInt(Integer::intValue).toArray();
    }

    private static void dfs(int now) {
        visited[now] = true;
        answer.add(now);
        for (Integer next : adjList[now]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
    }

    public static void main(String[] args) {
        int[] solution = solution(graph, start, n);
        System.out.println("solution = " + Arrays.toString(solution));
    }
}
