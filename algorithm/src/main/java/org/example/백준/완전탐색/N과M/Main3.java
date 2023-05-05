package org.example.백준.완전탐색.N과M;

import java.io.*;
import java.util.StringTokenizer;

public class Main3 {

    static int N, M;
    static int[] selected, visit;
    public static StringBuilder sb = new StringBuilder();

    static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        M = reader.nextInt();
        selected = new int[M + 1];
        visit = new int[N + 1];
    }

    // 만약 M개를 전부 고름 -> 조건에 맞는 탐색을 한가지 성공한것이다.
    // 아직 m개를 고르지 않음 -> k번째 부터 원소를 조건에 맞게 고르는 모든 방법을 시도한다.
    public static void dfs(int k) {
        // 재귀 깊이가 M과 같아지면 탐색과정에서 담았던 배열을 출력
        if (k == M + 1) {
            for (int i = 1; i <= M; i++) {
                sb.append(selected[i]).append(' ');
            }
            sb.append('\n');
            return;
        }

//        int start = selected[k -1];
//        if (start == 0) start = 1;
        for (int i = selected[k -1] + 1; i <= N; i++) {
                selected[k] = i;        // 해당 깊이를 index로 하여 i + 1 값 저장
                dfs(k + 1);    // 다음 자식 노드 방문을 위해 depth 1 증가시키면서 재귀호출
        }
    }


    public static void main(String[] args) throws IOException {
        input();
        //1 번째 원소부터 M번째 원소를 조건에 맞는 모든 방법을 찾아줘.
        dfs(1);
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
