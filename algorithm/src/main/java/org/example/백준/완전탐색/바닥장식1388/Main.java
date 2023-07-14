package org.example.백준.완전탐색.바닥장식1388;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static int MAX = 50 + 10;
    static char[][] map;
    static boolean[][] visited;
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        map = new char[MAX][MAX];
        visited = new boolean[MAX][MAX];
        // 1. map 입력
        for (int i = 1; i <= N; i++) {
            String str = br.readLine();
            for (int j = 1; j <= M; j++) {
                map[i][j] = str.charAt(j - 1);
        }}

        // 2. dfs 탐색
        int answer = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (!visited[i][j]) {
                    dfs(i, j);
                    answer++;
                }
            }
        }

        // 3 . 정답 출력
        bw.write(String.valueOf(answer));
        bw.flush();

        bw.close();
        br.close();
    }

    private static void dfs(int y, int x) {
        visited[y][x] = true;

        if (map[y][x] == '-' && map[y][x + 1] == '-') {
            dfs(y, x + 1);
        }

        if (map[y][x] == '|' && map[y + 1][x] == '|') {
            dfs(y + 1, x);
        }
    }
}
