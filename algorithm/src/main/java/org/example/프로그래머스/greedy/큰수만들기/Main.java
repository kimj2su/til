package org.example.프로그래머스.greedy.큰수만들기;

import java.util.Arrays;

public class Main {

    /**
     * 주어진 숫자 n에서 k개의 숫자를 빼서 가장 큰 수를 찾아라
     * n의 자릿 수 - k = 가장 큰 수의 길이
     *
     * 가장 큰 수의 첫번째 자리에 올 수 있는 수는 n에서 인덱스가 0~k까지
     * 두번째 자리에 올 수 있는 수는 n에서 인데스가 1~k+1에 해당하는 수
     * 세번째 자리에 올 수 있는 수는 n에서 인데스가 2~k+2에 해당하는 수 ...
     * 이런식으로 해당 자리에 오는 수 중 가장 큰 수를 하나씩 찾는데 그 전수 보다 앞자리에 올 수 는 없기 때문에
     * 마지막으로 찾은 자리 수의 인덱스 +1부터 자리에 올 수 있는 가장 마지막 수의 인덱스 까지 비교하며 찾는다.
     */
    public String solution(String number, int k) {

        StringBuilder sb = new StringBuilder();
        int idx = 0;
        int max;

        for (int i = 0; i < number.length() - k; i++) {
            max = Integer.MIN_VALUE;
            System.out.println("idx : " + i);
            for (int j = idx; j <= i + k; j++) {
                System.out.println("j :" + j);
                if (max < number.charAt(j) - '0') {
                    max = number.charAt(j) - '0';
                    System.out.println(max);
                    idx = j + 1;

                }
            }
            System.out.println("comp : " + max);
            sb.append(max);
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.solution("1924", 2));
    }
}
