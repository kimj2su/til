package org.example.inflearn.greedy.씨름선수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N;

    static class Body implements Comparable<Body> {
        public int h, w;

        public Body(int h, int w) {
            this.h = h;
            this.w = w;
        }


        @Override
        public int compareTo(Body other) {

            // 음수 면 내가 먼저
            // 양수면 재가 먼저
            // 0 같으면 친구
            // this가 앞이면 오름 차순 뒤면 내림차순
            return this.h - other.h;
        }
    }


    static void input() {
        Reader reader = new Reader();
        N = reader.nextInt();
        int[][] arr = new int[N][2];

        List<Body> list = new ArrayList<>();
        for(int i = 0; i < N; i++) {
//            arr[i][0] = reader.nextInt();
//            arr[i][1] = reader.nextInt();
            list.add(new Body(reader.nextInt(), reader.nextInt()));
        }
        Collections.sort(list);


//        Arrays.sort(arr, (o1, o2) -> {
//           return  o2[0] - o1[0];
//        });


        int count = 1;
        int endW = arr[0][1];

        for(int i = 1; i < N; i++) {
            if(endW <= arr[i][1]) {
                endW = arr[i][1];
                count++;
            }
        }
        System.out.println("count = " + count);
    }

    public static void main(String[] args) {
            input();
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
