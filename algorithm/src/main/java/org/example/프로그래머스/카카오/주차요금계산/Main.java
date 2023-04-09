package org.example.프로그래머스.카카오.주차요금계산;

import java.util.*;

public class Main {

    public int[] solution(int[] frees, String[] records) {
        int[] answer = null;
        Map<String, Integer> in = new HashMap<>();
        TreeMap<String, Integer> result = new TreeMap<>();

        for (String record : records) {
            if (record.split(" ")[2].equals("IN")) {
                String carNumber = record.split(" ")[1];
                String time = record.split(" ")[0];

                int index = time.indexOf(":");
                String hour = time.substring(0, index);
                String minute = time.substring(index + 1);
                int intHour = Integer.parseInt(hour);
                int intMinute = Integer.parseInt(minute);
                int inTime = intHour * 60 + intMinute;

                in.put(carNumber, inTime);
            } else {
                String carNumber = record.split(" ")[1];
                String time = record.split(" ")[0];

                int index = time.indexOf(":");
                String hour = time.substring(0, index);
                String minute = time.substring(index + 1);
                int intHour = Integer.parseInt(hour);
                int intMinute = Integer.parseInt(minute);
                int outTime = intHour * 60 + intMinute;

                int resultTime = outTime - in.get(carNumber);

                in.remove(carNumber);

                result.put(carNumber, result.getOrDefault(carNumber, 0) + resultTime);
            }
        }
        if (in.size() > 0) {
            for (String next : in.keySet()) {
                int maxTime = 23 * 60 + 59;
                System.out.println("maxTime = " + maxTime);
                int resultTime = maxTime - in.get(next);
                System.out.println("resultTime = " + resultTime);
                result.put(next, result.getOrDefault(next, 0) + resultTime);
            }
        }
        System.out.println("in = " + in);
        System.out.println("result = " + result);
        answer = new int[result.size()];
        int idx = 0;
        for (int value : result.values()) {
            answer[idx] = frees[1];
            if (value > frees[0]) {
                answer[idx] = (int) (frees[1] + Math.ceil((value - frees[0]) / (double) frees[2]) * frees[3]);
            }
            ++idx;
        }

        return answer;
    }


    public static void main(String[] args) {
        int[] frees = {180, 5000, 10, 600};
        String[] records = {"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"};
        Main main = new Main();
        System.out.println(Arrays.toString(main.solution(frees, records)));

//        int[][] v = {{1, 4}, {3, 4}, {3, 10}};
//        int[] answer = new int[2];
//        int x = v[0][0];
//        int y = v[0][1];
//
//        int tmp1 = 0;
//        int tmp2 = 0;
//        boolean check = false;
//        boolean check2 = false;
//        for (int i = 1; i < v.length; i++) {
//            if (x != v[i][0]) {
//                tmp1 = v[i][0];
//            } else {
//                check = true;
//            }
//            if (y != v[i][1]) {
//                tmp2 = v[i][1];
//            } else {
//                check2 = true;
//            }
//        }
//        if (!check) {
//            tmp1 = x;
//        }
//        if (!check2) {
//            tmp2 = y;
//        }
//        System.out.println("tmp1 = " + tmp1);
//        System.out.println("tmp2 = " + tmp2);
//        answer[0] = tmp1;
//        answer[1] = tmp2;
//        System.out.println("answer = " + Arrays.toString(answer));
    }
}
