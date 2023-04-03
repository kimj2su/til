package org.example.백준.브론즈.최소최대;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int a = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[a];
        for (int i = 0; i < a; i++) {
            int n = Integer.parseInt(st.nextToken());
            arr[i] = n;
        }
        Arrays.sort(arr);
        System.out.println(arr[0] + " " + arr[arr.length - 1]);
    }
}
