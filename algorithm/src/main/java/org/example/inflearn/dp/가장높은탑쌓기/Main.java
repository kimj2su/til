package org.example.inflearn.dp.가장높은탑쌓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static class Brick implements Comparable<Brick> {
        public int s, h, w;

        public Brick(int s, int h, int w) {
            this.s = s;
            this.h = h;
            this.w = w;
        }

        @Override
        public int compareTo(Brick o) {
            return o.s - this.s;
        }
    }

    static int[] dy;
    static int N;
    static List<Brick> list = new ArrayList<>();
    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        int answer = 0;
        Collections.sort(list);
        dy[0] = list.get(0).h;
        answer = dy[0];
        for (int i = 1; i < list.size(); i++) {
            int max_h = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (list.get(j).w > list.get(i).w && dy[j] > max_h) {
                    max_h = dy[j];
                }
            }
            dy[i] = max_h + list.get(i).h;
            answer = Math.max(answer, dy[i]);
        }

        System.out.println("answer = " + answer);
    }

    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        dy = new int[N];
        for (int i = 0; i < N; i++) {
            int a = reader.nextInt();
            int b = reader.nextInt();
            int c = reader.nextInt();
            list.add(new Brick(a, b, c));
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
    }
}
