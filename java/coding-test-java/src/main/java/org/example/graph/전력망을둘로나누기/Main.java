package org.example.graph.전력망을둘로나누기;

import java.util.ArrayList;

public class Main {
    private static boolean[] visited;
    private static ArrayList<Integer>[] adjList;
    private static int N, answer;

    public static int solution(int n, int[][] wires) {
        N = n;
        answer = n - 1;

        // 1. 전선의 연결 정보를 저장할 인접 리스트 초기화
        adjList = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }

        // 2. 전선의 연결 정보를 인접 리스트에 저장
        for (int[] wire : wires) {
            adjList[wire[0]].add(wire[1]);
            adjList[wire[1]].add(wire[0]);
        }

        visited = new boolean[n + 1];

        dfs(1);
        return answer;
    }

    private static int dfs(int now) {
        visited[now] = true;

        // 4. 자식 노드의 수를 저장하고 반환할 변수 선언
        int sum = 0;

        // 5. 연결된 모든 전선을 확인
        for (int next : adjList[now]) {
            if (!visited[next]) {
                // 6. (전체노드 - 자식 트리이ㅡ 노드 수) - (자식 트리의 노드 수) 의 절대값이 가장 작은 값을 구함
                int cnt = dfs(next);
                answer = Math.min(answer, Math.abs(N - 2 * cnt));
                sum += cnt;
            }
        }

        // 7. 현재 노드를 포함한 서브트리의 노드 수를 반환
        return sum + 1;
    }
    public static void main(String[] args) {
        System.out.println(solution(9, new int[][]{{1, 3}, {2, 3}, {3, 4}, {4, 5}, {4, 6}, {4, 7}, {7, 8}, {7, 9}})); // 3
    }
}
