package org.example.백준.완전탐색.섬의개수4963;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static int MAX = 50 + 10;
    static boolean[][] map;
    static boolean[][] visited;
    static int[] dirY = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dirX = {0, 1, 1, 1, 0, -1, -1, -1};
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());

            if (N == 0 && M == 0) {
                break;
            }

            map = new boolean[MAX][MAX];
            visited = new boolean[MAX][MAX];

            // 1. map 입력
            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= M; j++) {
                    map[i][j] = st.nextToken().charAt(0) == '1';
                }
            }

            // 2. dfs 탐색
            int answer = 0;
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= M; j++) {
                    if (map[i][j] && !visited[i][j]) {
                        dfs(i, j);
                        answer++;
                    }
                }
            }


            // 3 . 정답 출력
            bw.write(String.valueOf(answer));
            bw.newLine();
            bw.flush();
        }


        bw.close();
        br.close();
    }

    private static void dfs(int y, int x) {
        visited[y][x] = true;
        for (int i = 0; i < 8; i++) {
            int nextY = y + dirY[i];
            int nextX = x + dirX[i];
            if (map[nextY][nextX] && !visited[nextY][nextX]) {
                dfs(nextY, nextX);
            }
        }
    }
}
