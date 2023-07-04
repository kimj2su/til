package org.example.백준.완전탐색.리모컨1107;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {


    static int A;
    static int B;
    static int[] C;
    static boolean[] isBroken = new boolean[10];  // 버튼 고장 여부
    static Reader reader = new Reader();

    public static void main(String[] args) {
        input();
        getResult();
    }

    public static void getResult() {

        if (A == 100) {
            System.out.println(0);
            return;
        }
        // 현재 채널 100번
        // + 또는 - 버튼만 눌러서 이동하는 경우
        int result = Math.abs(A - 100); // 초기 값은 숫자 없이 + or - 이동
        for (int i = 0; i < 1000000; i++) { //N의 최대값이 500,000이므로 6자리수 중 최대 값까지 검사한다
            String strChan = String.valueOf(i);

            // 숫자 버튼으로 이동 가능한지 체크
            boolean isPossible = true;
            for (int j = 0; j < strChan.length(); j++) {
                if (isBroken[strChan.charAt(j) - '0']) {
                    isPossible = false;
                    break; // 고장난 버튼이 있으면 다이렉트로 접근 불가능
                }
            }

            if (isPossible) {
                result = Math.min(result, Math.abs(A - i) + strChan.length()); // 숫자 이동 후 +, - 이동
            }
        }
        System.out.println(result);
    }

    private static void input() {
        A = reader.nextInt();
        B = reader.nextInt();
        C = new int[B];
        for (int i = 0; i < B; i++) {
            isBroken[reader.nextInt()] = true;
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
