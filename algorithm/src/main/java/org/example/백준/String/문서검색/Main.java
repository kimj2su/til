package org.example.백준.String.문서검색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int[] arr;
    static String str;
    static String str2;

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
//        int count = 0;
//        int startIndex = 0;
//        while (true) {
//            int findIndex = str.indexOf(str2, startIndex);
//            if (findIndex < 0) {
//                break;
//            }
//            count++;
//            startIndex = findIndex + str2.length();
//        }
//
//        System.out.println(count);

        String replaced = str.replace(str2, "");
        int length = str.length() - replaced.length();
        int count = length / str2.length();
        System.out.println(count);
    }

    private static void input() {
        Reader reader = new Reader();
        str = reader.nextLine();
        str2 = reader.nextLine();
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
