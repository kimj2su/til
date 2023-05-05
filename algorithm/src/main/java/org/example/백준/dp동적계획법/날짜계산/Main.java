package org.example.백준.dp동적계획법.날짜계산;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int E = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int a = 0;
        int b = 0;
        int c = 0;
        int result = 0;
        do {
            a++;
            b++;
            c++;
            result++;

            if (a == 16) {
                a = 1;
            }
            if (b == 29) {
                b = 1;
            }
            if (c == 20) {
                c = 1;
            }

        } while (a != E || b != S || c != M);

        bw.write(String.valueOf(result));
        bw.flush(); // write로 담은 내용 출력 후, 버퍼를 비움.
        bw.close();
        br.close();
    }
}
