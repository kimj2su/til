package org.example.graph.네트워크;

public class Main {

    private static final int n = 3;
    private static final int[][] computers = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 1}
    };
    private static boolean[] visited;

    public int solution(int n, int[][] computers) {
        int answer = 0;
        visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i);
                answer++;
            }
        }

        return answer;
    }

    private void dfs(int now) {
        visited[now] = true;
        for (int i = 0; i < n; i++) {
            if (computers[now][i] == 1 && !visited[i]) {
                dfs(i);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        int solution = main.solution(n, computers);
        System.out.println("solution = " + solution);
    }
}
