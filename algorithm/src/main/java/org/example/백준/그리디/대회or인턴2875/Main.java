package org.example.백준.그리디.대회or인턴2875;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int K;

    static int A;

    public static void main(String[] args) {
        input();
        getResult();
    }
    public static void getResult() {
        int result = 0;
        while (N >= 2 && K >= 1 && N + K >= K + 3) {
            N -= 2;
            K -= 1;
            result++;
        }
        System.out.println(result);
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        K = reader.nextInt();
        A = reader.nextInt();
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
