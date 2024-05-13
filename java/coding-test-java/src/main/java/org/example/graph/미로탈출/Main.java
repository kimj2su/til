package org.example.graph.미로탈출;

import java.util.ArrayDeque;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.solution(maps));
    }
    private static final String[] maps = {
            "SOOOL",
            "XXXXO",
            "OOOOO",
            "OXXXX",
            "OOOOE"
    };

    // 위 아래 왼쪽 오른쪽 이동 방향
    private static final int[] dx = {0, 0, 1, -1};
    private static final int[] dy = {1, -1, 0, 0};

    // 위치 정보 (x, y)를 저장할 클래스 생성
    private static class Point {
        int nx,ny;

        public Point(int nx, int ny) {
            this.nx = nx;
            this.ny = ny;
        }
    }

    private static char[][] map;
    private static int N, M;

    public int solution(String[] maps)  {
        N = maps.length;
        M = maps[0].length();

        // 미로에 대한 정보를 배열로 저장
        map = new char[N][M];
        for (int i = 0; i < N; i++) {
            map[i] = maps[i].toCharArray();
        }

        Point start = null, end = null, lever = null;

        // 시작점, 끝점, 레버 위치 찾기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 'S') {
                    start = new Point(i, j);
                } else if (map[i][j] == 'E') {
                    end = new Point(i, j);
                } else if (map[i][j] == 'L') {
                    lever = new Point(i, j);
                }
            }
        }

        // 시작 지점 -> 레버, 레버 -> 출구까지의 최단거리를 각각 구함
        int startLever = bfs(start, lever);
        int leverEnd = bfs(lever, end);

        if (startLever == -1 || leverEnd == -1) {
            return -1;
        }
        return startLever + leverEnd;
    }

    // start -> end로 너비 우선 탐색하며 최단거리 반환
    private int bfs(Point start, Point end) {
        int[][] dist = new int[N][M];
        ArrayDeque<Point> queue = new ArrayDeque<>();
        dist[start.ny][start.nx] = 1;
        queue.add(start);

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now.nx + dx[i];
                int ny = now.ny + dy[i];

                // 범위를 벗어나거나, 벽인 경우 무시
                if (nx < 0 || ny < 0 || nx >= M || ny >= N) {
                    continue;
                }

                // 이미 방문한 경우 무시
                if (dist[ny][nx] > 0) {
                    continue;
                }

                if (map[ny][nx] == 'X') {
                    continue;
                }

                dist[ny][nx] = dist[now.ny][now.nx] + 1;

                queue.add(new Point(nx, ny));

                if (nx == end.nx && ny == end.ny) {
                    return dist[end.ny][end.nx] - 1;
                }
            }
        }
        return -1;
    }
}
