package org.example.백준.완전탐색.일이삼더하기9095;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 다이나믹 프로그래밍으로 해결할 수 있는 문제였습니다.
 * 특정 수 N을 1, 2, 3의 합으로 표현할 수 있는 경우의 수를 구하는 것이 핵심입니다.
 * 가령,  N = 1일 때는 1로만 표현가능하므로 경우의 수가 1가지이고, N = 2일 때는 1 + 1 또는 2로 표현가능하므로 경우의 수가 2가지이고,
 * N = 3일 때는 1 + 1 + 1, 1 + 2, 2 + 1, 3으로 표현가능하므로 경우의 수가 4가지입니다.
 * 물론, 위와 같이 전부 나열하면서 알 수도 있겠지만 좀 더 똑똑하게 N = 4부터 경우의 수를 구해봅시다.
 * N = 4일 때, 1, 2, 3으로 표현할 수 있는 경우는 크게 보면 1 + 3, 2 + 2, 3 + 1입니다.
 * N = 5일 때, 1, 2, 3으로 표현할 수 있는 경우는 크게 보면 2 + 3, 3 + 2, 4 + 1입니다.
 * 즉, N = i일 때, 1, 2, 3으로 표현할 수 있는 경우는 크게 보면 i - 3 + 3, i - 2 + 2, i - 1 + 1이므로
 * 우리는 N = i - 3, i - 2, i - 1일 때의 경우의 수를 모두 더하면 i일 때의 경우의 수를 얻어낼 수있습니다.
 * 따라서, 점화식으로는 dp[i] = dp[i - 3] + dp[i - 2] + dp[i - 1] 이라는 것을 알 수 있습니다.
 * 위 식을 이용하여 bottom-up 코드를 작성하면 됩니다.
 */
public class Main {
    static Reader reader = new Reader();
    static int[] dp = new int[11];

    public static void main(String[] args) throws IOException {
        getResult();
    }

    public static void getResult() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;

        for (int i = 4; i <= 10; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }

        int T = reader.nextInt();

        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            int N = reader.nextInt();
            sb.append(dp[N]).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
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
