package org.example.백준.그리디.동전11047;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int K;

    static int[] A;
    static int result = 0;

    public static void main(String[] args) {
        input();
        getResult();
    }
    public static void getResult() {
       for (int i = N - 1; i >= 0; i--) {
              if (K >= A[i]) {
                  int value = K / A[i];
                  K -= value * A[i];
                  result += value;
              }
       }
        System.out.println(result);
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        K = reader.nextInt();
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
