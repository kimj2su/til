package org.example.백준.완전탐색.홀수홀릭호석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main {
    static int N, ans_min, ans_max;
    static PrintWriter out = new PrintWriter(System.out);

    static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
    }
    static void pro() {
        ans_min = 0x7fffffff;
        ans_max = 0;
        //시작하는 순간에는 N이라는 숫자와, 여기에 속한 홀수 개수 만큼이 포함된다.
        dfs(N, get_odd_cnt(N));
        System.out.println(ans_min + " " + ans_max);
    }
    // x라는 수 안에 홀수의 객수를 리턴하는 함수
    static int get_odd_cnt(int x) {
        int res = 0;
        while (x > 0) {
            int  digit = x % 10;
            if (digit % 2 == 1) res++;
            x /= 10;
        }
        return  res;
    }
    // x 라는 수에 도달했으며, 이때까지 등장한 홀수 자릿수가 total_odd_cnt 만큼 있을 때, 남은 경우를 모두 잘라보는 함수
    static void dfs(int x, int total_odd_cnt) {

        //1. 만약 한자리 수면 더 이상 아무것도 하지 못하고 종료한다.
        if (x <= 9) {
            ans_min = Math.min(ans_min, total_odd_cnt);
            ans_max = Math.max(ans_max, total_odd_cnt);
            return;
        }

        //2. 만약  두자리 수면 나눠서 합을 구하여 새로운 수로 생각한다 -> 재귀 호출한다.
        if (x <= 99) {
            int next_x = (x / 10) + (x %10);
            dfs(next_x, total_odd_cnt + get_odd_cnt(next_x));
            return;
        }

        //3. 만약 세자리 수면 가능한 3가지 자르는 방법을 모두 진행한다.
        String s  = Integer.toString(x);
        for (int i = 0; i <= s.length() - 3; i++) {
            for (int j = i + 1; j <= s.length() - 2; j++) {
                String x1 = s.substring(0, i + 1);
                String x2 = s.substring(i + 1, j + 1);
                String x3 = s.substring(j + 1, s.length());

                int next_x = Integer.parseInt(x1) + Integer.parseInt(x2) + Integer.parseInt(x3);
                dfs(next_x, total_odd_cnt + get_odd_cnt(next_x));
            }
        }
    }

    public static void main(String[] args) {
        input();
        pro();
    }

    static class Reader {
        BufferedReader br;
        StringTokenizer st;

        public Reader() {
            this.br = new BufferedReader(new InputStreamReader(System.in));
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
    }
}


// 주어지는 N중에 홀수를 최대한 많이 보고 싶어한다.
//
