package org.example.백준.삼성기출문제.첫번째.연구소14502;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int[][] graph;
    static boolean[] visited;
    static int[] dx = {0,0,1,-1};
    static int[] dy = {1,-1,0,0};

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        dfs(0);
        System.out.println(maxSafeZone);
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        M = reader.nextInt();

        graph = new int[N + 1][ M + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                graph[i][j] = reader.nextInt();
            }
        }
    }

    private static void dfs(int index) {
        if (index == 3) {
            bfs();
            return;
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (graph[i][j] == 0) {
                    graph[i][j] = 1;
                    dfs(index + 1);
                    graph[i][j] = 0;
                }
            }
        }
    }

    static void bfs() {
        Queue<Node> q = new LinkedList<>();

        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= M; j++) {
                if(graph[i][j] == 2) {
                    q.add(new Node(i,j));
                }
            }
        }

        //원본 연구소를 바꾸지 않기 위한 카피맵 사용
        int[][] copyMap = new int[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            copyMap[i] = graph[i].clone();
        }

        //BFS 탐색 시작
        while(!q.isEmpty()) {
            Node now = q.poll();
            int x = now.x; // 현재 값
            int y = now.y; //

            for(int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];

                //연구소 범위 밖이 아니고 빈칸일 경우에만 바이러스를 퍼트린다.
                if(0 < nx && nx <= N && 0 < ny && ny <= M) {
                    if(copyMap[nx][ny] == 0) {
                        q.add(new Node(nx,ny));
                        copyMap[nx][ny] = 2;
                    }
                }
            }
        }

        //SafeZone 확인
        funcSafeZone(copyMap);
    }
    static int maxSafeZone = Integer.MIN_VALUE; //최대값을 찾기 위한 최소값 설정
    private static void funcSafeZone(int[][] copyMap) {
        int safeZone =0;
        for(int i = 1; i <= N ; i++) {
            for(int j = 1; j <= M; j++) {
                if(copyMap[i][j] == 0) {
                    safeZone++;
                }
            }
        }
        maxSafeZone = Math.max(maxSafeZone, safeZone);
    }

    //Queue에 좌표값 x,y를 넣기 위함.
    static class Node {
        int x;
        int y;
        Node(int x, int y){
            this.x = x;
            this.y = y;
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
