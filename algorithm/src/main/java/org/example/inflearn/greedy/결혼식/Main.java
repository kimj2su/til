package org.example.inflearn.greedy.결혼식;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static List<ExistsMember> list = new ArrayList<>();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        input();
        getResult();
        System.out.println(sb.toString());
    }

    private static class ExistsMember implements Comparable<ExistsMember> {
        public int time;
        public char state;

        public ExistsMember(int time, char state) {
            this.time = time;
            this.state = state;
        }

        @Override
        public int compareTo(ExistsMember other) {
            if (this.time == other.time) return this.state - other.state;
            return this.time - other.time;
        }
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        for (int i = 0; i < N; i++) {
            list.add(new ExistsMember(reader.nextInt(), 's'));
            list.add(new ExistsMember(reader.nextInt(), 'e'));
        }
        Collections.sort(list);
    }

    private static void getResult() {
        int cnt = 0;
        int result = 0;
        for (ExistsMember existsMember : list) {
            if (existsMember.state == 's') cnt++;
            else cnt--;
            result = Math.max(result, cnt);
        }

        sb.append(result);
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
    }
}
