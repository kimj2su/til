package org.example.백준.트리.회사문화14267;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int[] parents;
    static int[] like;

    static List<Integer>[] tree;
    static Reader reader = new Reader();

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        for (int i = 1; i <= M; i++) {
            int employee = reader.nextInt();
            int point = reader.nextInt();
            like[employee] += point;
        }

        dfs(1);

        for (int i = 1; i <= N; i++) {
            System.out.print(like[i] + " ");
        }
    }

    private static void dfs(int node) {
        for (int child : tree[node]) {
            like[child] += like[node];
            dfs(child);
        }
    }

    private static void input() {

        N = reader.nextInt();
        M = reader.nextInt();
        parents = new int[N + 1];
        like = new int[N + 1];
        tree = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
            parents[i] = reader.nextInt();
            if (parents[i] != -1) {
                tree[parents[i]].add(i);
            }
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
