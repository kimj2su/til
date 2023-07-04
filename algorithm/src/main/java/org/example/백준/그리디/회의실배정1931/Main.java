package org.example.백준.그리디.회의실배정1931;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int start;
    static int end;

    static int[][] two;
    static Reader reader = new Reader();

    public static void main(String[] args) {
        input();
        getResult();
    }

    public static void getResult() {
        Arrays.sort(two, (o1, o2) -> {
            if (o1[1] == o2[1]) {
                return Integer.compare(o1[0], o2[0]);
            }
            return Integer.compare(o1[1], o2[1]);
        });

        int result = 1;
        int end = two[0][1];

        for (int i = 1; i < N; i++) {
            if (end <= two[i][0]) {
                result++;
                end = two[i][1];
            }
        }

        System.out.println(result);
    }

    private static void input() {
        N = reader.nextInt();
        two = new int[N][2];
        for (int i = 0 ; i < N; i++) {
            two[i][0] = reader.nextInt();
            two[i][1] = reader.nextInt();
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
