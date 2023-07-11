package org.example.백준.이분탐색.문자열집합14425;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static String[] S;
    static int answer;

    static boolean isExist(String x) {
        int left = 1, right = N;
        while (left <= right) {
            int mid = (left + right) / 2;
            int compareResult = S[mid].compareTo(x);
            if (compareResult < 0 ) left = mid + 1;
            else if (compareResult > 0) right = mid - 1;
            else return true;
        }
        return false;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt((st.nextToken()));
        M = Integer.parseInt(st.nextToken());

        S = new String[N + 1];
        for (int i = 1; i <= N; i++) {
            S[i] = br.readLine();
        }

        Arrays.sort(S, 1, N + 1);
        while (M-- > 0) {
            String target = br.readLine();
            if (isExist(target)) {
                answer++;
            }

        }

        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

}
