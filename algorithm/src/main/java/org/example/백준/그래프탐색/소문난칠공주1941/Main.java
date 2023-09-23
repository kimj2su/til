package org.example.백준.그래프탐색.소문난칠공주1941;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static String[][] str;
    static int[] students = new int[25];
    static boolean[] visited = new boolean[25];
    static List<Integer> pick = new ArrayList<>();

    public static void main(String[] args) {
        input();
        getResult();
    }

    private static void getResult() {
        System.out.println(nextCombination(0));
    }

    public static boolean isFriend(int a, int b) {
        int diff = Math.abs(a - b);
        int max = Math.max(a, b);

        if (diff == 1 && max % 5 != 0) return true;
        if (diff == 5) return true;

        return false;
    }

    static int nextCombination(int studentNum) {
        // base case
        if (pick.size() == 7) {
            // 이다솜파의 인원이 4명이상인지?
            int cnt = 0;
            for (int i = 0; i < 7; i++) {
                if (students[pick.get(i)] == 1) {
                    cnt++;
                }

            }

            // 이다솜파가 4명 미만이라면 조합으로 사용하지 않음
            if (cnt < 4) {
                return 0;
            }

            // DFS 탐색 전 초기화
            for (int i = 0; i < 7; i++) {
                visited[i] = false;
            }

            // 7명이 모두 인접해있다면 조합으로 인정함
            if (dfs(0) == 7) return 1;

            return 0;
        }

        // 불가능한 경우 (25명을 초과해서 뽑으려는 경우

        if (studentNum >= 25) return 0;

        // recursive case
        // 조항븨 개수
        int ret = 0;

        // studentNum 번째 학생을 포함하지 않는 경우
        ret += nextCombination(studentNum + 1);
        // studentNum 번째 학생을 포함하는 경우
        pick.add(studentNum);
        ret += nextCombination(studentNum + 1);
        pick.remove(pick.size() - 1);
        return ret;
    }

    static int dfs(int index) {
        int count = 1;
        visited[index] = true;
        for (int i = 1; i < 7; i++) {
            int me = pick.get(index);
            int you = pick.get(i);
            if (!visited[i] && isFriend(me, you)) {
                count += dfs(i);
            }
        }
        return count;
    }


    private static void input() {
        Reader reader = new Reader();
        int cnt = 0;
        for (int i = 0; i < 5; i++) {
            String st = reader.nextLine();
            for (int j = 0; j < 5; j++) {
                if (st.charAt(j) == 'S') {
                    students[cnt] = 1; // 이다솔파
                } else {
                    students[cnt] = 0; // 임다솜파
                }
                cnt++;
            }
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
