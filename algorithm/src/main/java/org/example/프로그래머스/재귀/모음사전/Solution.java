package org.example.프로그래머스.재귀.모음사전;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    private static final char[] CHARS = "AEIOU".toCharArray();

    private void generate(String word, List<String> words) {
        words.add(word);

        if (word.length() == 5) return;
        for (char c : CHARS) {
            generate(word + c, words);
        }
    }
    public int solution(String word) {
        List<String> words = new ArrayList<>();
        generate("", words);
        return words.indexOf(word);
    }

    public static void main(String[] args) {
        System.out.println(new Solution().solution("AAAAE"));
    }
}
