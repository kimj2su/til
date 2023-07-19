package org.example.백준.구현.방번1475;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    static int N;
    static String s;
    static int[] arr = new int[10];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
//        N = Integer.parseInt(br.readLine());
        s = br.readLine();
        getResult();
    }

    private static void getResult() {
        for (int i = 0; i < s.length(); i++) {
            int tmp = s.charAt(i) - '0';
            caseLoop(tmp);
        }

        int ans = (arr[6] + arr[9] + 1) / 2;
        for (int i = 0; i < 9; i++)
            if (i != 6) ans = Math.max(ans, arr[i]);
        System.out.println(ans);
    }

    private static void caseLoop(int value) {
        switch (value) {
            case 1:
                arr[1]++;
                break;
            case 2:
                arr[2]++;
                break;
            case 3:
                arr[3]++;
                break;
            case 4:
                arr[4]++;
                break;
            case 5:
                arr[5]++;
                break;
            case 6:
                arr[6]++;
                break;
            case 7:
                arr[7]++;
                break;
            case 8:
                arr[8]++;
                break;
            case 9:
                arr[9]++;
                break;
        }
    }
}
