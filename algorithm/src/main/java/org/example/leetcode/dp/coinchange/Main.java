package org.example.leetcode.dp.coinchange;

public class Main {

    private static final int[] coins = {1, 2, 5};
    private static final int amount = 11;

    public static void main(String[] args) {
        Main main = new Main();
        int result = main.coinChange(coins, amount);
        System.out.println("result = " + result);
    }

    private int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i - coin >= 0 && dp[i - coin] < min) {
                    min = dp[i - coin] + 1;
                }
            }
            dp[i] = min;
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
