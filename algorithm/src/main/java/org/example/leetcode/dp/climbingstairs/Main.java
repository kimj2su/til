package org.example.leetcode.dp.climbingstairs;

/**
 * <a href="https://leetcode.com/problems/climbing-stairs/">문제 링크</a>
 *
 */
public class Main {

    private static final int n = 3;
    public static void main(String[] args) {
        Main main = new Main();
        int result = main.climbStairs(n);
        System.out.println(result);
    }

    private int climbStairs(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2]; // i개일때
        }
        return dp[n];
    }
}
