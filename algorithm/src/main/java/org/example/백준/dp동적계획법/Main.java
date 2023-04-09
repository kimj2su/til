package org.example.백준.dp동적계획법;

import java.util.Arrays;
import java.util.Scanner;

class Main {
    final static int INF = 1 << 30;
    static int[] a;
    static int[][] dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 0. 입력 받으며 중복 숫자 제거
        int N = sc.nextInt();
        int K = sc.nextInt();
        a = new int[N + 1];
        dp = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            a[i] = sc.nextInt();
            if (a[i] == a[i - 1]) {
                N--;
                i--;
            }
        }

        // 1. dp 배열 초기화 : i부터 j까지의 전구를 하나로 통일하기 위한 최소 변경 수
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                dp[i][j] = INF;
            }
            dp[i][i] = 0; // i부터 i까지의 전구를 하나로 통일하기 위한 변경의 수
        }

        System.out.println("dp = " + Arrays.deepToString(dp));

        // 2. 두 개 이상의 전구를 간의 최솟값 계싼

        sc.close();
    }
}
