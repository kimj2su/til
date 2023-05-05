package org.example.inflearn.array.피보나치수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
    }

    static void getResult() {
        int[] answer = new int[N];
        answer[0] = 1;
        answer[1] = 1;

        for (int i = 2; i < N; i++) {
            answer[i] = answer[i-1] + answer[i -2];
        }

        System.out.println(Arrays.toString(answer));
    }

    static void getResult2() {
        int a = 1, b = 1, c;
        System.out.println(a+ " " + b + " ");
        for (int i = 2; i < N; i++) {
            c = a + b;
            System.out.println(c + " ");
            a = b;
            b = c;
        }
    }

    public static void main(String[] args) {
        input();
        getResult2();
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
    }
}
