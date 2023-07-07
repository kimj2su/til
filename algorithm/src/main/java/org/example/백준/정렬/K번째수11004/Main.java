package org.example.백준.정렬.K번째수11004;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {

    static int N;
    static int K;
    static int[] A;

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        Arrays.sort(A);
        System.out.println(A[K - 1]);
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
