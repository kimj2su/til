package org.example.leetcode.array.wordbreak;

import java.util.Arrays;
import java.util.List;

public class Main {

    private final String s = "leetcode";
    private final List<String> wordDict = Arrays.asList("leet", "code");
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.wordBreak(main.s, main.wordDict));
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (String word : wordDict) {
                int len = word.length();
                if (len <= i && word.equals(s.substring(i - len, i))) {
                    dp[i] = dp[i] || dp[i - len];
                }
            }
        }

        return dp[n];
    }
}
