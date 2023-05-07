package org.example.백준.String.단어공부;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main1157 {
    static String str;

    public static void main(String[] args) {
        input();
        getResult();
    }

    public static int[] getAlphabetCount(String str) {
        int[] count = new int[26];
        str = str.toUpperCase();
        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i) - 'A']++;
        }
        return count;
    }

    private static void getResult() {
        int[] count = getAlphabetCount(str);

        int maxCount = Integer.MIN_VALUE;
        char maxAlphabet = '?';
        for (int i = 0; i < 26; i++) {
            if (count[i] > maxCount) {
                maxCount = count[i];
                maxAlphabet = (char) ('A' + i);
            } else if (count[i] == maxCount) {
                maxAlphabet = '?';
            }
        }
        System.out.println(maxAlphabet);
    }

    private static void input() {
        Reader reader = new Reader();
        str = reader.nextLine();
        str = str.toUpperCase();

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
