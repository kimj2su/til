package org.example.백준.이분탐색.세수의합2295;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static int[] A, B;
    static StringBuffer sb = new StringBuffer();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt((br.readLine()));
        A = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }
        M = Integer.parseInt((br.readLine()));
        B = new int[M + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= M; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }
        search(A, B);
        bw.close();
        br.close();
    }

    private static void search(int[] A, int[] B) {
        Arrays.sort(A);
        for (int i = 1; i <= M; i++) {
            // 찾고 싶은 값
            int target = B[i];
            // 이분 탐색 맨처음 인덱스는 왼쪽 == 1이다,
            int left = 1;
            // right는 배열의 제일 마지막값 N이다.
            int right = N;
            // 중간값 0으로 초기화
            int mid = 0;
            // 왼쪽 인덱스 값이 오른쪽 값을 넘어설때까지 반복한다.
            while (left <= right) {
//                // 왼쪽 인덱스와 오른쪽 인덱스의 중앙 값
//                mid = (left + right) / 2;
//                // 중간 값이 찾고자 하는 값과 같다면 1 출력
//                if (A[mid] == target) {
//                    System.out.println(1);
//                    break;
//                } else if (A[mid] > target) {
//                    right = mid - 1;
//                } else {
//                    left = mid + 1;
//                }
                mid = (left + right) / 2;
                if (A[mid] > target) {
                    right = mid - 1;
                } else if (A[mid] < target) {
                    left = mid + 1;
                } else if (A[mid] == target) {
                    System.out.println(1);
                    break;
                }
            }
            if (A[mid] != target) {

                System.out.println(0);
            }
        }
    }
}
