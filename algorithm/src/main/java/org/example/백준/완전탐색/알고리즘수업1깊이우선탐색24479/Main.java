package org.example.백준.완전탐색.알고리즘수업1깊이우선탐색24479;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    final static int MAX = 1000000 + 10;
    static ArrayList<Integer>[] graph;
    static boolean[] visited;
    static int N, M, R;
    static int[] answer;
    static int order;

    public static void main(String[] args) throws IOException {
        // 0. 입력 및 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        // 1. graph에 연결 정보 채우기
        graph = new ArrayList[MAX];

        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        visited = new boolean[MAX];
        answer = new int[MAX];
        // order 초기화
        order = 1;

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()); // u -> v
            int y = Integer.parseInt(st.nextToken());
            graph[x].add(y);
            graph[y].add(x);
        }

        // 2. 오름차순 정렬
        for (int i = 1; i <= N; i++) {
            graph[i].sort((o1, o2) -> o1 - o2);
//            Collections.sort(graph[i]);
        }

        System.out.println("graph = " + graph);

        // 3. dfs(재귀함수 호출)
        dfs(R);

        // 4. 출력
        for (int i = 1; i <= N; i++) {
            bw.write(String.valueOf(answer[i]));
            bw.newLine();
        }
        bw.flush();
        bw.close();
        br.close();
    }

    // 1. 내가 index 번호로 들어왔다면 방문했다고 표시하고
    // 2. 내가 지금 몇등인지 answer에 기록한다,
    // 3. 그리고 나랑 연결되어 있는 ArrayList에서 찾아서 방문한다.
    // 4. 방문하는 기준은 아직 방문하지 않은 노드들만 방문한다.
    public static void dfs(int idx) {
        // 1. 방문 처리
        visited[idx] = true;
        // 2. 현재 노드의 order를 answer에 저장
        answer[idx] = order;
        order++;

        // 3. 현재 노드와 연결된 노드들을 탐색
        for (int i = 0; i < graph[idx].size(); i++) {
            // graph의 연결된 노드를 가져옴 ex) idx == 1, i == 1 일때, 2
            int next = graph[idx].get(i);
            // 4. 방문하지 않은 노드라면
            if (!visited[next]) {
                // 5. dfs 호출
                dfs(next);
            }
        }
    }

}
