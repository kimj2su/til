package org.example.백준.이분탐색.두용액2470;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] A;
    static int tmp;
    static Reader reader = new Reader();
    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        Arrays.sort(A, 1, N + 1);

        int ans = 0;
        for (int i = 1; i <= N; i++) {
            // A[i] 보다 작은 B[j]의 개수를 찾자 !
//            int cnt = lowerBound(A, 1, M, A[i]);
//            ans += cnt;
            lowerBound(A, 1, N, A[i]);
        }

        System.out.println("ans = " + ans);
    }

    static void lowerBound(int[] A, int left, int right, int target) {
        // A[L..R] 에서 X 미만의 수(X 보다 작은 수) 중 제일 오른쪽 인덱스를 return 하는 함수
        // 그런게 없다면 L - 1 을 return 한다.
        int result = left - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
//            int abs = Math.abs(A[mid] + target);
            int plus = A[mid] + target;
            if (plus < 0) {
                left = mid + 1;
            }
//            if (A[mid] < target) {
//                result = mid;
//                left = mid + 1;
//            } else {
//                right = mid - 1;
//            }
        }
        System.out.println(A[result]);
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
