package org.example.백준.완전탐색.ACM호텔;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static List<Hotel> list = new ArrayList<>();

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
//        int floor = 1; // 배정받은 방의 층수
//        int distance = 1; // 엘리베이터와의 거리
//        while (--N > 0) { // 101호를 배치한 상태이므로 N-1번만 진행
//            floor++; // 새손님에게 이전 방보다 한 층 높은 방을 배정
//            if (floor > H) {
//                floor = 1; // 해당 distance의 방이 모두 배정되었다면
//                distance++; // 다음 distance의 가장 낮은 층을 배정
//            }
//        }
        for (int i = 0; i < N; i++) {
            Hotel hotel = list.get(i);
            int distance = (hotel.N - 1) / hotel.H + 1;
            int floor = (hotel.N - 1) % hotel.H + 1;
            System.out.printf("%d%02d\n", floor, distance);
        }
    }

    private static class Hotel {
        public int H;
        public int W;
        public int N;

        public Hotel(int h, int w, int n) {
            H = h;
            W = w;
            N = n;
        }
    }
    private static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        for (int i = 0; i < N; i++) {
            list.add(new Hotel(reader.nextInt(), reader.nextInt(), reader.nextInt()));
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
