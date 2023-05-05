package org.example.inflearn.greedy.최대수입스케줄;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static class Lecture implements Comparable<Lecture> {
        public int money;
        public int date;

        public Lecture(int money, int time) {
            this.money = money;
            this.date = time;
        }

        @Override
        public int compareTo(Lecture other) {
            // 음수 면 내가 먼저
            // 양수면 재가 먼저
            // 0 같으면 친구
            // this가 앞이면 오름 차순 뒤면 내림차순
            return other.date - this.date;
        }
    }

    static int N;
    static List<Lecture> list = new ArrayList<>();
    static int max = Integer.MIN_VALUE;
    static int result;
    static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        for (int i = 0; i < N; i++) {
            int m = reader.nextInt();
            int d = reader.nextInt();
            list.add(new Lecture(m, d));
            if (d > max) max = d;
        }
        Collections.sort(list);
    }

    static void getResult() {
        //큰값을 우선으로하는 큐가 생성된다.
        PriorityQueue<Integer> pQ = new PriorityQueue<>(Collections.reverseOrder());
        int j = 0;
        //날짜순으로 정렬한 리슨트에서 날짜가 제일 큰 값이 max니깐 큰 값부터 작아진다.
        for (int i = max; i >= 1; i--) {
            for (; j < N; j++) {
                if (list.get(j).date < i) break; // 밖의 반복문을 제어하기 위한 브레이크
                pQ.offer(list.get(j).money);
            }
            if (!pQ.isEmpty()) result += pQ.poll();
        }
    }

    public static void main(String[] args) {
        input();
        getResult();
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
