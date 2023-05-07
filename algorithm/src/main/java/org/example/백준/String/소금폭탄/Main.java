package org.example.백준.String.소금폭탄;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static String str;
    static String str2;

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        String[] arr = str.split(":");
        String[] arr2 = str2.split(":");

        int currentHour = Integer.parseInt(arr[0]);
        int currentMinute = Integer.parseInt(arr[1]);
        int currentSecond = Integer.parseInt(arr[2]);

        int targetHour = Integer.parseInt(arr2[0]);
        int targetMinute = Integer.parseInt(arr2[1]);
        int targetSecond = Integer.parseInt(arr2[2]);

        int currentSecondAmount = currentHour * 3600 + currentMinute * 60 + currentSecond;
        int targetSecondAmount = targetHour * 3600 + targetMinute * 60 + targetSecond;

        int NeedSecondAmount = targetSecondAmount - currentSecondAmount;
        if (NeedSecondAmount <= 0) NeedSecondAmount += 24 * 3600;

        int needHour = NeedSecondAmount / 3600;
        int needMinute = (NeedSecondAmount % 3600) / 60;
        int needSecond = NeedSecondAmount % 60;

        System.out.println(String.format("%02d:%02d:%02d", needHour, needMinute, needSecond));
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
