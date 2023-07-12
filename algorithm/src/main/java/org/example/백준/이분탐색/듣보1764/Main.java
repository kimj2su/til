package org.example.백준.이분탐색.듣보1764;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static String[] A, B, ans;
    static Reader reader = new Reader();
    public static void main(String[] args) {
        input();
        getResult();
    }

    static void getResult() {
        // A 배열에 대해 이분 탐색을 할 예정이니깐, 정렬을 해주자 !
        Arrays.sort(B, 1, M + 1);
        ans = new String[N + 1];
        int answer = 0;
        for (int i = 1; i <= N; i++) {
            // A[i] 보다 작은 B[j]의 개수를 찾자 !
            if (lowerBound(B, 1, M, A[i])) {
                answer++;
                ans[answer] = A[i];
            }
        }
        Arrays.sort(ans, 1, answer + 1);
        System.out.println(answer);
        for (int i = 1; i <= answer; i++) {
            System.out.println(ans[i]);
        }
    }

    static boolean lowerBound(String[] arr, int left, int right, String target) {
       // A[L..R] 에서 X 이상의 수 중 제일 왼쪽 인덱스를 return 하는 함수
        // 그런게 없다면 R + 1 을 return 한다.
        int result = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (B[mid].equals(target)) {
                return true;
            }
            if (B[mid].compareTo(target) < 0) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return false;
    }

    private static void input() {
        N = reader.nextInt();
        M = reader.nextInt();
        A = new String[N + 1];
        B = new String[M + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = reader.nextLine();
        }
        for (int i = 1; i <= M; i++) {
            B[i] = reader.nextLine();
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
