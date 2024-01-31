package org.example.백준.dp동적계획법.동전1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 동전의 종류
        int k = scanner.nextInt(); // 만들어야 하는 금액

        int[] coins = new int[n];
        int[] dp = new int[k + 1];
        for (int i = 0; i < n; i++) {
            coins[i] = scanner.nextInt();
        }

        dp[0] = 1; // 0원을 만드는 방법은 1가지
        for (int i = 0; i < n; i++) {
            for (int j = 0; j + coins[i] <= k; j++) {
                dp[j + coins[i]] += dp[j];
            }

            // 방법 2
            // for (int j = coins[i]; j <= k; j++) {
            //     dp[j] += dp[j - coins[i]];
            // }
        }

        System.out.println(dp[k]);
    }
}
