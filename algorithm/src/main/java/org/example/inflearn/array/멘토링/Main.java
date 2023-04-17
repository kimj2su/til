package org.example.inflearn.array.멘토링;

import java.util.Arrays;
import java.util.Scanner;

class Main {
    private int solution(int n, int m, int[][] arr) {
        int answer = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int cnt = 0;
                for (int k = 0; k < m; k++) {
                    int pi = 0, pj = 0;
                    for (int s = 0; s < n; s++) {
                        if (arr[k][s] == i) pi = s;
                        if (arr[k][s] == j) pj = s;
                    }
                    if (pi < pj) cnt ++;
                }
                if (cnt == m) {
                    answer++;
                }
            }
        }
        return answer;
    }
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] arr = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = in.nextInt();
            }
        }
        Main main = new Main();
        System.out.println("arr = " + Arrays.deepToString(arr));
        System.out.println(main.solution(n, m, arr));
    }
}
