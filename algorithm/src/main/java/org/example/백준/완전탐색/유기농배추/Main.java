package org.example.백준.완전탐색.유기농배추;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int MAX = 50 + 10;
    static int T, N, M, K;
    static boolean[][] map;
    static boolean[][] visited;
    static int[] dirY = {-1, 1, 0, 0};
    static int[] dirX = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 첫번째 테스트 케이스
        T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken()); // 가로
            N = Integer.parseInt(st.nextToken()); // 세로
            K = Integer.parseInt(st.nextToken()); // 배추 개수

            map = new boolean[MAX][MAX];
            visited = new boolean[MAX][MAX];

            // 1. map 정보 반영 -> 내가 검증하고 싶은 것 즉, 여기서는 배추의 개수 만큼 반복문을 돌려야한다.
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()); // 가로
                int y = Integer.parseInt(st.nextToken()); // 세로

                map[y + 1][x + 1] = true;
            }

            // 2. dfs 수행
            int answer = 0;
            for (int i = 1; i <= N; i++) { // 세로
                for (int j = 1; j <= M; j++) { // 가로
                    if (map[i][j] && !visited[i][j]) {
                        answer++;
                        dfs(i, j);
                    }
//                    if (map[i][j]) {
//                        map[i][j] = false;
//                        answer++;
//                        dfs(i, j);
//                    }
                }
            }

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= M; j++) {
                    if (map[i][j] && !visited[i][j]) {
                        answer++;
                        dfs(i, j);
                    }
                }
            }

            // 3. 출력
            bw.write(String.valueOf(answer));
            bw.newLine();
            bw.flush();
        }

        bw.close();
        br.close();
    }

    private static void dfs(int y, int x) {
//        map[y][x] = false;
        visited[y][x] = true;
//        dfs(y + 1, x); // 지금 위치에서 아래
//        dfs(y - 1, x); // 지금 위치에서 위
//        dfs(y, x + 1); // 지금 위치에서 오른쪽
//        dfs(y, x - 1); // 지금 위치에서 왼쪽
        for (int i = 0; i < 4; i++) {
            int newY = y + dirY[i];
            int newX = x + dirX[i];
            if (map[newY][newX] && !visited[newY][newX]) {
                dfs(newY, newX);
            }
//            if (map[newY][newX]) {
//                map[newY][newX] = false;
//                dfs(newY, newX);
//            }
        }
    }

}
