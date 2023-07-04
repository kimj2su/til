package org.example.백준.완전탐색.날짜계산1476;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {


    static int A;
    static int B;
    static int C;
    static Reader reader = new Reader();

    public static void main(String[] args) {
        input();
        getResult();
    }

    public static void getResult() {
        int year = 1;
        int a = 1;
        int b = 1;
        int c = 1;

        while (true) {
            if (a == A && b == B && c == C) {
                System.out.println(year);
                break;
            }

            a++;
            b++;
            c++;
            year++;

            if (a == 16) {
                a = 1;
            }
            if (b == 29) {
                b = 1;
            }
            if (c == 20) {
                c = 1;
            }
        }
    }

    private static void input() {
        A = reader.nextInt();
        B = reader.nextInt();
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
