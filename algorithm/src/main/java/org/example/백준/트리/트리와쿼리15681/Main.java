package org.example.백준.트리.트리와쿼리15681;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int R;
    static int Q;
    static int[] arr;
    static boolean[] visit;
    static List<Integer>[] tree;
    static int[] answer;
    static Reader reader = new Reader();

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        answer[R] = dfs(R);

        for (int i = 1; i <= Q; i++) {
            int q = reader.nextInt();
            System.out.println(answer[q]);
        }
    }

    private static void input() {
        N = reader.nextInt();
        R = reader.nextInt();
        Q = reader.nextInt();

        visit = new boolean[N + 1];
        answer = new int[N + 1];
        tree = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < N; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            tree[u].add(v);
            tree[v].add(u);
        }
    }

    private static int dfs(int idx) {
        if (answer[idx] != 0) return answer[idx];
        visit[idx] = true;
        int result = 1; // 자기자신
        for (int child : tree[idx]) {
            if (!visit[child]) {
                result += dfs(child);
            }
        }

        return answer[idx] = result;
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
