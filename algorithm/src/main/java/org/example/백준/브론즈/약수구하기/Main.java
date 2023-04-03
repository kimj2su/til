package org.example.백준.브론즈.약수구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int result = 0;
        for(int i = 1; i <= N; i++) {
            if(N % i == 0) ++result;
            if(result == K) {
                System.out.println(i);
                return;
            }
        }
        System.out.println(0);
    }
}