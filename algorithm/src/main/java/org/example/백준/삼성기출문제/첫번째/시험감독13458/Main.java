package org.example.백준.삼성기출문제.첫번째.시험감독13458;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int B;
    static int C;
    static int[] A;
    static int[][] graph;
    static boolean[] visited;
    static String[] str;

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        long result = 0;
        for (int i = 1; i <= N; i++) {
            int num = A[i];
            num -= B;
            result++;
            if (num > 0) {
                result += num / C;
                if (num % C  > 0) {
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    private static void input() {
        Reader reader = new Reader();
        // 시험장의 개수
        N = reader.nextInt();
        // 각 시험장에 있는 응시자의 수
        A = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = reader.nextInt();
        }
        // 총감독관이 감시할 수 있는 수
        B = reader.nextInt();
        // 부감독관이 감시할 수 있는 수
        C = reader.nextInt();
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
