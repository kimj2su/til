package org.example.graph.너비우선탐색순회;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class Main {
    private static final int[][] graph = {
            {1, 2},
            {1, 3},
            {2, 4},
            {2, 5},
            {3, 6},
            {3, 7},
            {4, 8},
            {5, 8},
            {6, 9},
            {7, 9}
    };

    private static final int start = 1;
    private static final int n = 9;
    private static ArrayList<Integer>[] adjList;
    private static boolean[] visited;
    private static ArrayList<Integer> answer;

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
        }

        bfs(start);
        return answer.stream().mapToInt(Integer::intValue).toArray();
    }

    private static void bfs(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            Integer now = queue.poll();
            answer.add(now);

            for (Integer next : adjList[now]) {
                if (!visited[next]) {
                    queue.add(next);
                    visited[next] = true;
                }
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("solution(graph, start, n) = " + Arrays.toString(solution(graph, start, n)));
    }
}
