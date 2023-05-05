package org.example.백준.완전탐색.N과M;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main2 {

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

    public static void dfs(int at, int depth) {

        if(depth == M) {
		/*
		 깊이가 M이랑 같을경우 출력
		*/
            for (int val : arr) {
                sb.append(val).append(' ');
            }
            sb.append('\n');
            return;
        }
        /*
        재귀하면서 백트래킹 할
        반복문 구현
        */
        /*
        i 는 at 부터 탐색하도록 한다.
        */
        for(int i = at; i <= N; i++) {
            // 현재 깊이를 index로 하여 해당 위치에 i 값을 담는다
            arr[depth] = i;

		/*
		 재귀호출 :
		 현재 i 값보다 다음 재귀에서 더 커야하므로
		 i + 1 의 값을 넘겨주고, 깊이 또한 1 증가시켜 재귀호출해준다
		*/
            dfs(i + 1, depth + 1);
        }

    }


    public static void main(String[] args) throws IOException {
        input();
        dfs(1, 0);
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
