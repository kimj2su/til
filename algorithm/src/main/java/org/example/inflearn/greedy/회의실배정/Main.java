package org.example.inflearn.greedy.회의실배정;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
    static int[][] arr;
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N + 10][3];
        for (int i = 1; i <= N; i++) {
            String[] s = br.readLine().split(" ");
            arr[i][1] = Integer.parseInt(s[0]);
            arr[i][2] = Integer.parseInt(s[1]);
        }

        Arrays.sort(arr, (o1, o2) -> {
            if (o1[2] == o2[2]) return o1[1] - o2[1];
            else return o1[2] - o2[2];
        });
        getResult();
    }

    private static void getResult() {
        int cnt = 0;
        int end = 0;
        for (int i = 1; i < arr.length; i++) {
            int start = arr[i][1];
            if (start != 0 && start >= end) {
                cnt++;
                end = arr[i][2];
            }
        }
        System.out.println(cnt);
    }
}
