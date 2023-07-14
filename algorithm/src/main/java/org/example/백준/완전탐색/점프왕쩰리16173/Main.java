package org.example.백준.완전탐색.점프왕쩰리16173;

import java.util.*;
import java.io.*;

class Main {

    static int MAX = 3 + 100 + 10;
    static int N;
    static int[][] A;
    static boolean[][] visited;
    static int[] dirY = {1, 0};
    static int[] dirX = {0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        A = new int[MAX][MAX];
        visited = new boolean[MAX][MAX];

        // 1. 정사각형 생성
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= N; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 2. dfs 실행
        dfs(1, 1);

        // 3. 출력
        if(visited[N][N]) bw.write("HaruHaru");
        else bw.write("Hing");
        bw.flush();
        bw.close();
        br.close();
    }

    private static void dfs(int y, int x) {
        visited[y][x] = true;

        if (y == N && x == N) return;

        for (int i = 0; i < 2; i++) {
            int newY = y + dirY[i] * A[y][x];
            int newX = x + dirX[i] * A[y][x];
            if (!visited[newY][newX]) {
                dfs(newY, newX);
            }
        }
    }
}