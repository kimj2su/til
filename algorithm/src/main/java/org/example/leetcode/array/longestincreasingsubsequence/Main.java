package org.example.leetcode.array.longestincreasingsubsequence;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/longest-increasing-subsequence/description/
 */
public class Main {

    // private final int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
    private final int[] nums = {1,3,6,7,9,4,10,5,6};

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.lengthOfLIS(main.nums));
    }

    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i])  { // 2,1,5,3,4 가 있을때 i=2일때 nums[j] = 1, nums[i] = 5
                    max = Math.max(max, dp[j]); // dp[0] = 1, dp[1] = 1
                }
            }
            dp[i] = max + 1; // dp[2] = 2
        }
        Arrays.sort(dp);
        return dp[nums.length - 1];
    }
}
