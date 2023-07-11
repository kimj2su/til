package org.example.백준.완전탐색.침투13565;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static int MAX = 1000 + 10;
    static boolean[][] map;
    static boolean[][] visited;
    static int[] dirY = {-1, 1, 0, 0};
    static int[] dirX = {0, 0, -1, 1};
    static int T, N, M, K;
    static boolean answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new boolean[MAX][MAX];
        visited = new boolean[MAX][MAX];

        // 1. map 입력
        for (int i = 1; i <= N; i++) {
            String str = br.readLine();
            for (int j = 1; j <= M; j++) {
                map[i][j] = str.charAt(j - 1) == '1';
            }
        }

        // 2. dfs 탐색
        for (int j = 1; j <= M; j++) {
            if (map[1][j]) {
                dfs(1, j);
            }
        }

        // 3 . 정답 출력
        if (answer) {
            bw.write("YES");
        } else {
            bw.write("NO");
        }
        bw.close();
        br.close();
    }

    private static void dfs(int y, int x) {
        if (y == N) {
            answer = true;
            return;
        }

        visited[y][x] = true;
        for (int i = 0; i < 4; i++) {
            int nextY = y + dirY[i];
            int nextX = x + dirX[i];

            if (map[nextY][nextX] && !visited[nextY][nextX]) {
                dfs(nextY, nextX);
            }
        }

    }
}
