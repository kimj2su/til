package org.example.백준.그리디.ATM11399;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] A;
    static Reader reader = new Reader();

    public static void main(String[] args) {
        input();
        getResult();
    }

    public static void getResult() {
        Arrays.sort(A);
        int result = 0;
        for (int i = 0; i < A.length; i++) {
            result = A[i] + result; // 1,
//            int tmp = 0;
            for (int j = 0; j < i; j++) {
//                tmp += A[j];
                result = result + A[j];
            }
        }
        System.out.println("result = " + result);
    }

    private static void input() {
        N = reader.nextInt();
        A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = reader.nextInt();
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
