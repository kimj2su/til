package org.example.백준.완전탐색.연결요소의개수11724;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    final static int MAX = 1000 + 10;
    static boolean[][] graph;
    static boolean[] visited;

    static int N, M;
    static int answer;

    public static void main(String[] args) throws IOException {
        // 0. 입력 및 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 1. graph에 연결 정보 채우기
        graph = new boolean[N + 1][N + 1];
        visited = new boolean[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            graph[u][v] = true;
            graph[v][u] = true;
        }

        // 2. dfs
        for (int i = 1; i <= N; i++) {
            // 모든 요소를 탐색해야하기때문에 dfs함수 안에서 재귀 호출이 아닌 밖에서 dfs를 호출해줘야 한다.
            // vistied 가 false라면 아직 방문한적이 없으니 dfs 호출
            // i 가 1일때 의 덩어리가 모두 true가 되고 dfs를 통해 방문처리 되었기 때문에 answer++ 해준다.
            if (!visited[i]) {
                dfs(i);
                answer++;
            }
        }

        // 3. 출력
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    public static void dfs(int idx) {
        visited[idx] = true;

        for (int i = 1; i <= N; i++) {
            if (graph[idx][i] && !visited[i]) {
                dfs(i);
            }
        }
    }
}
