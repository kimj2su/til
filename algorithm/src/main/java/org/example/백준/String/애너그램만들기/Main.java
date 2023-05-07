package org.example.백준.String.애너그램만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static String a;
    static String b;
    public static void main(String[] args) {
        input();
        getResult();
    }
    public static int[] getAlphabetCount(String str) {
        int[] count = new int[26];
        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i) - 'a']++;
        }
        return count;
    }
    private static void getResult() {
        int answer = 0;
        int[] countA = getAlphabetCount(a);
        int[] countB = getAlphabetCount(b);

        for (int i = 0; i < countA.length; i++) {
            if ((countA[i] - countB[i]) != 0) {
                answer += Math.abs(countA[i] - countB[i]);
            }
        }

        System.out.println(answer);
    }

    private static void input() {
        Reader reader = new Reader();
        a = reader.nextLine();
        b = reader.nextLine();
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
