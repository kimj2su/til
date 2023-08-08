package org.example.백준.완전탐색.분해합2231;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int result = 0;

        for(int i = 1; i < n; i++) {
            int sum = 0;
            int temp = i;

            while(temp != 0) {
                sum += temp % 10;
                temp /= 10;
            }

            if(sum + i == n) {
                result = i;
                break;
            }
        }

        System.out.println(result);
    }
}
