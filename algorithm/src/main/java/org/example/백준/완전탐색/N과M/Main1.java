package org.example.백준.완전탐색.N과M;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main1 {

    static int N;
    static int M;
    static boolean[] visit;
    static int[] arr;
    public static StringBuilder sb = new StringBuilder();

    static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        M = reader.nextInt();
        arr = new int[M];
        visit = new boolean[N];
    }

    public static void dfs(int depth) {

        // 재귀 깊이가 M과 같아지면 탐색과정에서 담았던 배열을 출력
        if (depth == M) {
            for (int val : arr) {
                sb.append(val).append(' ');
            }
            sb.append('\n');
            return;
        }


        for (int i = 0; i < N; i++) {

            // 만약 해당 노드(값)을 방문하지 않았다면?
            if (!visit[i]) {

                visit[i] = true;        // 해당 노드를 방문상태로 변경
                arr[depth] = i + 1;        // 해당 깊이를 index로 하여 i + 1 값 저장
                dfs(depth + 1);    // 다음 자식 노드 방문을 위해 depth 1 증가시키면서 재귀호출

                // 자식노드 방문이 끝나고 돌아오면 방문노드를 방문하지 않은 상태로 변경
                visit[i] = false;
            }
        }
        return;

    }


    public static void main(String[] args) throws IOException {
        input();
        dfs(0);
        System.out.println(sb.toString());
    }

    static class Reader {
        BufferedReader br;
        StringTokenizer st;

        public Reader() {
            this.br = new BufferedReader(new InputStreamReader(System.in));
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
    }

}
