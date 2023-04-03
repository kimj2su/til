package org.example.백준.브론즈.이진수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        String str =  Integer.toBinaryString(N);

        System.out.println("str = " + str);
        String answer = "";
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 0; i < str.length(); i++) {
            String substring = sb.substring(i, i + 1);
            if ("1".equals(substring)) {
                answer += i + " ";
            }
        }
        System.out.println( answer);
    }
}