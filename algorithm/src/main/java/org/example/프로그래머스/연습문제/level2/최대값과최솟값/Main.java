package org.example.프로그래머스.연습문제.level2.최대값과최솟값;

public class Main {
    public String solution(String s) {
        String[] strs = s.split(" ");
        int max=0, min = 0;


        for (String str : strs) {
            if (min == 0) {
                min = Integer.parseInt(str);
                max = Integer.parseInt(str);
            }

            if (min > Integer.parseInt(str)) {
                min = Integer.parseInt(str);
            }

            if (max < Integer.parseInt(str)) {
                max = Integer.parseInt(str);
            }

        }
        String minStr = String.valueOf(min);
        String maxStr = String.valueOf(max);
        return minStr + " " + maxStr;
    }
    public static void main(String[] args) {
        String s = "1 2 3 4";
        Main main = new Main();
        System.out.println(main.solution(s));
    }
}
