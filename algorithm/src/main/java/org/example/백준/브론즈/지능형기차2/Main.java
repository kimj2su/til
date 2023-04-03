package org.example.백준.브론즈.지능형기차2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        st.nextToken();
        int max = Integer.parseInt(st.nextToken());  //최대 사람 수
        int current = max;  //현재 사람 수
        for (int i = 1; i <= 9; i++) {
            st = new StringTokenizer(br.readLine());
            int out = Integer.parseInt(st.nextToken());  // 내린 사람 수
            int in = Integer.parseInt(st.nextToken());  // 탄 사람 수
            current = current - out + in;
            max = Math.max(max, current);
        }
        System.out.print(max);
    }
}
