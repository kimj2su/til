package org.example.graph.게임맵최단거리;

import java.util.ArrayDeque;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/1844
 * 배열의 크기가 N * M 이라 하면 dis 배열을 초기화할 때의 시간 복잡도는 O(N*M)이고,
 * 너비 우선 탐색을 할 때는 최악의 경우 dist의 모든 위치가 큐에 들어가는 경우이므로 너비 우선 탐색의 시간 복잡도는 O(N*M)이다.
 */
public class Main {

    private static final int[] rx = {0, 0, 1, -1};
    private static final int[] ry = {1, -1, 0, 0};
    private static final int[][] maps = {
            {1, 0, 1, 1, 1},
            {1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1},
            {1, 1, 1, 0, 1},
            {0, 0, 0, 0, 1}
    };

    private static class Node {
        int r, c;

        public Node(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public int solution(int[][] maps) {
        // 맵의 크기를 저장하는 변수
        int N = maps.length;
        int M = maps[0].length;

        // 최단 거리를 저장할 배열 생성
        int[][] dist = new int[N][M];

        // bfs 탐색을 위한 큐 생성
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.add(new Node(0, 0));
        dist[0][0] = 1;

        while (!queue.isEmpty()) {
            Node now = queue.poll();
            int r = now.r;
            int c = now.c;

            for (int i = 0; i < 4; i++) {
                int nr = r + rx[i];
                int nc = c + ry[i];

                // 범위를 벗어나는 경우 무시
                if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                    continue;
                }

                // maps[nr][nc]가 0이면 벽이므로 무시, dist[nr][nc]가 0이 아니면 이미 방문한 곳이므로 무시
                if (maps[nr][nc] == 0 || dist[nr][nc] != 0) {
                    continue;
                }

                dist[nr][nc] = dist[r][c] + 1;
                queue.addLast(new Node(nr, nc));
            }
        }
        // 목적지까지 최단 거리 반환, 도달하지 못했으면 -1 반환
        return dist[N - 1][M - 1] == 0 ? -1 : dist[N - 1][M - 1];
    }
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("main.solution(maps) = " + main.solution(maps));
    }
}
