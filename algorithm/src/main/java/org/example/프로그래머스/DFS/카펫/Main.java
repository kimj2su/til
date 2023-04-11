package org.example.프로그래머스.DFS.카펫;

import java.util.Arrays;

public class Main {
    public int[] solution() {
        int brown = 10;
        int yellow = 2;

        for (int width = 3; width <= 5000; width++) {
            for (int height = 3; height <= width; height++) {
                //조건 검사
                int boundary = (width + height - 2) * 2;
                int center = width * height - boundary;
                if (brown == boundary && yellow == center) {
                    return new int[]{width, height};
                }
            }
        }
        return null;
    }
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(Arrays.toString(main.solution()));
    }
}
