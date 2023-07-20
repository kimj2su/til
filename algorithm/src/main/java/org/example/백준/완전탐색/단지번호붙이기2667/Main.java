package org.example.백준.완전탐색.단지번호붙이기2667;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int N, groupCnt;
    static int MAX = 25 + 10;
    static ArrayList<Integer> group;
    static String[] a;
    static boolean[][] visited;
    static int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public static void main(String[] args) {
        input();
        pro();
    }


    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        a = new String[N];
        visited = new boolean[MAX][MAX];
        for (int i = 0; i < N; i++) {
            a[i] = reader.nextLine();
        }
    }

    private static void dfs(int x, int y) {
        groupCnt++;
        visited[x][y] = true;

        for (int i = 0; i < 4; i++) {
            int nextX = x + dir[i][0];
            int nextY = y + dir[i][1];

            if (nextX < 0 || nextY < 0 || nextX >= N || nextY >= N) {
                continue;
            }

            if (a[nextX].charAt(nextY) == '1' && !visited[nextX][nextY]) {
                dfs(nextX, nextY);
            }
        }
    }

    private static void pro() {
        group = new ArrayList<>();
        groupCnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (a[i].charAt(j) == '1' && !visited[i][j]) {
                    groupCnt = 0;
                    dfs(i, j);
                    group.add(groupCnt);
                }
            }
        }
        Collections.sort(group);
        System.out.println(group.size());
        for (Integer integer : group) {
            System.out.println(integer);
        }
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
