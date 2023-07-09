package org.example.백준.완전탐색.트리의부모찾기11725;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static int MAX = 1000000 + 10;
    static ArrayList<Integer>[] graph;
    static boolean[] visited;
    static int[] answer;
    static int N;

    public static void main(String[] args) throws IOException {
        // 0. 입력 및 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());

        // 1. graph에 연결 정보 채우기
        graph = new ArrayList[MAX];

        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        visited = new boolean[MAX];
        answer = new int[MAX];

        for (int i = 1; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            graph[x].add(y);
            graph[y].add(x);
        }

        // 2. dfs(재귀함수 호출)
        dfs(1);

        // 3. 출력
        for (int i = 2; i <= N; i++) {
            bw.write(answer[i] + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }

    public static void dfs(int idx) {
        visited[idx] = true;
        for (int i = 0; i < graph[idx].size(); i++) {
            // graph[idx][i] graph 의 index와 i가 연결되어 있을 떼
            Integer next = graph[idx].get(i);
            if (!visited[next]) {
                answer[next] = idx;
                dfs(next);
            }
        }
    }

}
