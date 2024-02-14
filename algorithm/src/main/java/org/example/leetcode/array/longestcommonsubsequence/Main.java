package org.example.leetcode.array.longestcommonsubsequence;

public class Main {

    private static final String text1 = "abcde";
    private static final String text2 = "ace";
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.longestCommonSubsequence(text1, text2));
    }
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // dp[i][j]는 text1[0:i]와 text2[0:j]의 가장 긴 공통 부분 수열의 길이를 나타냅니다.
        int[][] dp = new int[m + 1][n + 1];

        // base case
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = 0;
        }


        for (int i = 1; i <= m; i++) {
            char c1 = text1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = text2.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }
}
