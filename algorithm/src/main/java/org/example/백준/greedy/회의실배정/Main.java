package org.example.백준.greedy.회의실배정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

//    static int N;
//    static int[] selected, dy;
//    static int result = 1;
//    static void input() {
//        Reader reader = new Reader();
//        N = reader.nextInt();
//        selected = new int[N];
//        dy = new int[N];
//
//        int start = reader.nextInt();
//        int end = reader.nextInt();
//        dy[0] = end;
//        for (int i = 1; i < N ; i++) {
//            int newStart = reader.nextInt();
//            int newEnd = reader.nextInt();
//            operation(i, dy[i - 1], newStart, newEnd);
//        }
//    }
//
//    static void operation(int index, int end, int newStart, int newEnd) {
//        if (newStart >= end) {
//            dy[index] = newEnd;
//            result++;
//        } else {
//            dy[index] = end;
//        }
//    }
//
//    public static void main(String[] args) {
//        input();
//        System.out.println(result);
//    }
//
//    static class Reader {
//        BufferedReader br;
//        StringTokenizer st;
//
//        public Reader() {
//            br = new BufferedReader(new InputStreamReader(System.in));
//        }
//
//        String next() {
//            while (st == null || !st.hasMoreElements()) {
//                try {
//                    st = new StringTokenizer(br.readLine());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return st.nextToken();
//        }
//
//        int nextInt() {
//            return Integer.parseInt(next());
//        }
//    }

    public static void main(String[] args) throws IOException {
        //입력 시작
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine()); //회의 수
        int[][] arr = new int[n][2];

        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }
        //입력 끝

        //회의가 끝나는 시간 기준으로 오름차순 정렬
        Arrays.sort(arr, (o1, o2) -> {
            // 종료 시간이 같을 경우 시작 시간이 빠른순으로 정렬
            // 양수면 o2가 먼저 음수면 01이 먼저 같으면 같다.
            if(o1[1] == o2[1]) {
                return o1[0] - o2[0];
            } else{
                return o1[1] - o2[1];
            }
        });

        int count = 0; //하루 최대 회의 수
        int endTime = 0;
        for(int i=-0; i<n; i++) {
            if(endTime <= arr[i][0]) {
                endTime = arr[i][1];
                count++;
            }
        }

        System.out.println(count);
    }
}
