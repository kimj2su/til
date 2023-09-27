package org.example.백준.삼성기출문제.감시15683;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int[][] graph;
    static int cameraCnt = 1;
    static int[] cameraType = new int[9];
    static int[] cameraX = new int[9];
    static int[] cameraY = new int[9];
    static final int MAX = 987654321;

    static int[] dx = {0, 0, 1, -1}; // 0=rignt, 1=left, 2=down, 3=up
    static int[] dy = {1, -1, 0 ,0};
    static int[][] camera1 = {
            {1, 0, 0, 0},
            {1, 0, 1, 0},
            {1, 1, 0, 0},
            {1, 1, 1, 0},
            {1, 1, 1, 1}

    };
    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        System.out.println(dfs(0));
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        M = reader.nextInt();
        graph = new int[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <=M; j++) {
                graph[i][j] = reader.nextInt();
                if (graph[i][j] >= 1 && graph[i][j] <= 5) {
                    cameraType[cameraCnt] = graph[i][j];
                    cameraX[cameraCnt] = i;
                    cameraY[cameraCnt++] = j;
                }
            }
        }
    }

    private static int dfs(int index) {
        if (index == cameraCnt) {
            return getBlindSpot();
        }

        int ret = MAX;

        int type = 0;
return 0;
    }

    private static int getBlindSpot() {
        int blindSpot = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <=M; j++) {
                if (graph[i][j] == 0) blindSpot++;
            }
        }
        return blindSpot;
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
