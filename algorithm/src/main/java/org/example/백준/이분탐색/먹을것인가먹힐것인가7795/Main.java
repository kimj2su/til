package org.example.백준.이분탐색.먹을것인가먹힐것인가7795;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] A;
    static Reader reader = new Reader();
    static StringBuffer sb = new StringBuffer();
    public static void main(String[] args) {
        input();
        getResult();
    }

    static void pro() {
        // A 배열에 대해 이분 탐색을 할 예정이니깐, 정렬을 해주자 !
        Arrays.sort(A, 1, N + 1);

        int best_num = Integer.MAX_VALUE;
        int v1 = 0, v2 = 0;
        for (int left = 1; left <= N - 1; left++) {
            // A[left] 용액을 쓸 것이다. 고로 -A[left] 보다 작은 수를 찾아야 한다.
            int result = lowerBound(A, left + 1, N, -A[left]);

            // A[result - 1] 와 A[result] 중에 A[left]와 섞었을때의 정보를 정답에 갱신시킨다.
            if (left + 1 <= result - 1 && result - 1 <= N && Math.abs(A[result - 1] + A[left]) < best_num) {
                best_num = Math.abs(A[left] + A[result - 1]);
                v1 = A[left];
                v2 = A[result - 1];
            }

            if (left + 1 <= result && result <= N && Math.abs(A[result] + A[left]) < best_num) {
                best_num = Math.abs(A[left] + A[result - 1]);
                v1 = A[left];
                v2 = A[result - 1];
            }
        }

        sb.append(v1).append(' ').append(v2);
        System.out.println(sb.toString());
    }

    static int lowerBound(int[] A, int left, int right, int target) {
       // A[L..R] 에서 X 이상의 수 중 제일 왼쪽 인덱스를 return 하는 함수
        // 그런게 없다면 R + 1 을 return 한다.
        int result = right + 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (A[mid] >= target) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    private static void getResult() {
        pro();
    }

    private static void input() {
        N = reader.nextInt();
        A = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = reader.nextInt();
        }
    }

    static class Reader {
        BufferedReader br;
        StringTokenizer st;

        public Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
