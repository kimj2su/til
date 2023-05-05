package org.example.백준.dp동적계획법.일로만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int number = Integer.parseInt(br.readLine());
        int[] dp = new int[number + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i <= number; i++) {
            dp[i] = dp[i - 1] + 1;
            if (i % 2 == 0) dp[i] = Math.min(dp[i], dp[i / 2] + 1);
            if (i % 3 == 0) dp[i] = Math.min(dp[i], dp[i / 3] + 1);
        }
        System.out.println(dp[number]);
        br.close();

    }

    public int[] solution(long begin, long end) {
        int[] answer = {};
        long[] dy = new long[(int) (end + 1L)];

        dy[1] = 1;
        dy[2] = 1;
        for (int i = 2; i <= end; i++) {
            dy[i] = (long) i * i;
        }
        System.out.println(Arrays.toString(dy));
        return answer;
    }
}
