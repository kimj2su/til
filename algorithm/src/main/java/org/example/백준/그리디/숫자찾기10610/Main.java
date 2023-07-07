package org.example.백준.그리디.숫자찾기10610;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {

    static int N;
    static String S;

    public static void main(String[] args) {
        input();
        getResult();
    }

    public static void getResult() {
        // 3의 배수 판정을 위해 각 자리 수를 더함
        int sum = 0;
        for (int i = 0; i < S.length(); i++) {
            sum += S.charAt(i) - '0';
        }

        // 10의 배수가 아니거라 3의 배수가 아니라면 -1 출력
        if(!S.contains("0") || sum % 3 != 0) {
            System.out.println(-1);
            return;
        }

        // 10의 배수이고 3의 배수라면 내림차순으로 정렬
        char[] chars = S.toCharArray();
        Arrays.sort(chars);
        String result = new StringBuilder(new String(chars)).reverse().toString();
        System.out.println(result);
    }

//    public static void getResult() {
//        String s = String.valueOf(N);
//        String[] str = s.split("");
//        Arrays.sort(str, Comparator.reverseOrder());
//        String collect = String.join("", str);
//        int intValue = Integer.parseInt(collect);
//
////        int value = returnValue(intValue, s.length() - 1);
//        System.out.println("value = " + value);
//
//    }


//    private static int returnValue(int intValue, int index) {
//        if (intValue % 30 == 0) {
//            return intValue;
//        } else {
//            if (index > 0) {
//                String s = String.valueOf(intValue);
//                String[] str = s.split("");
//                String tmp1 = str[index];
//                String tmp2 = str[index - 1];
//                str[index] = tmp2;
//                str[index - 1] = tmp1;
//                String collect = String.join("", str);
//                int intValue1 = Integer.parseInt(collect);
//                return returnValue(intValue1, index - 1);
//            }
//            return -1;
//        }
//    }

    private static void input() {
        Reader reader = new Reader();
        S = reader.next();
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
