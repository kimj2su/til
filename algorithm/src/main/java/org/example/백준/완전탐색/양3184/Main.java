package org.example.백준.완전탐색.양3184;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static int MAX = 250 + 10;
    static String[] str;
    static boolean[][] visited;
    static int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static ArrayList<Integer> sheeps;
    static ArrayList<Integer> wolfs;
    static int sheep;
    static int wolf;

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        sheeps = new ArrayList<>();
        wolfs = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (str[i].charAt(j) != '#' && !visited[i][j]) {
                    sheep = 0;
                    wolf = 0;
                    dfs(i, j);
                    if (wolf < sheep) {
                        wolf = 0;
                    } else {
                        sheep = 0;
                    }
                    sheeps.add(sheep);
                    wolfs.add(wolf);
                }
            }
        }
        int answerSheep = 0;
        int answerWolf = 0;
        for (Integer integer : sheeps) {
            answerSheep += integer;
        }
        for (Integer integer : wolfs) {
            answerWolf += integer;
        }
        System.out.println(answerSheep + " " + answerWolf);
    }

    static void dfs(int x, int y) {
        visited[x][y] = true;

        if (str[x].charAt(y) == 'v') {
            wolf++;
        } else if (str[x].charAt(y) == 'o'){
            sheep++;
        }

        for (int i = 0; i < 4; i++) {
            int nextX = x + dir[i][0];
            int nextY = y + dir[i][1];

            if (nextX < 0 || nextY < 0 || nextX >= N || nextY >= M) {
                continue;
            }

            if (str[nextX].charAt(nextY) != '#' && !visited[nextX][nextY]) {
                dfs(nextX, nextY);
            }
        }
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        M = reader.nextInt();
        visited = new boolean[MAX][MAX];
        str = new String[N];
        for (int i = 0; i < N; i++) {
            str[i] = reader.nextLine();
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
    // static int []dx = {-1,0,1,0};
    // static int []dy = {0,1,0,-1};
    // static char [][]map;
    // static boolean [][]visit;
    // static int R;
    // static int C;
    // static int []ans = new int[2];
    // public static void main(String[] args) throws IOException {
    //     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //     StringTokenizer st = new StringTokenizer(br.readLine());
    //
    //     R = Integer.parseInt(st.nextToken());
    //     C = Integer.parseInt(st.nextToken());
    //     // .: 땅, #: 울타리, V: 늑대, O: 양
    //     map = new char[R][C];
    //     visit = new boolean[R][C];
    //
    //     int s = 0;
    //     int w = 0;
    //
    //     for(int i=0;i<R;i++){
    //         String temp = br.readLine();
    //         for(int j=0;j<C;j++){
    //             map[i][j] = temp.charAt(j);
    //         }
    //     }
    //
    //     for(int i=0;i<R;i++){
    //         for(int j=0;j<C;j++){
    //             if(!visit[i][j] && !(map[i][j] == '#')){
    //                 ans[0]=ans[1]=0;
    //                 int []x = dfs(i,j);
    //                 if( !(x[0] == 0 && x[1] == 0)){
    //                     if(x[1] >= x[0]){
    //                         w += x[1];
    //                     }else if(x[1] < x[0]){
    //                         s += x[0];
    //                     }
    //                 }
    //
    //             }
    //         }
    //     }
    //     System.out.println(s);
    //     System.out.println(w);
    // }
    // static int[] dfs(int x,int y){
    //
    //     visit[x][y] = true;
    //     if(map[x][y] == 'o')
    //         ans[0]++;
    //     if(map[x][y] == 'v')
    //         ans[1]++;
    //
    //     for(int i=0;i<4;i++){
    //         int nx = x+dx[i];
    //         int ny = y+dy[i];
    //
    //         if(nx < 0 || ny < 0 || nx >= R || ny >=C) continue;
    //         if(visit[nx][ny] || map[nx][ny]=='#') continue;
    //         dfs(nx,ny);
    //     }
    //
    //     return ans;
    // }
}
