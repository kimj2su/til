package org.example.재귀.쿼드압축후개수세기;

import java.util.Arrays;

/*
문제 링크 https://school.programmers.co.kr/learn/courses/30/lessons/68936
 */
class Main {

    private Count count(int offsetX, int offsetY, int size, int[][] arr) {
        int h = size / 2;
        for (int x = offsetX; x < offsetX + size; x++) {
            for(int y = offsetY; y < offsetY + size; y++) {
                if (arr[y][x] != arr[offsetY][offsetX]) {
                    return count(offsetX, offsetY, h, arr)
                            .add(count(offsetX + h, offsetY, h, arr))
                            .add(count(offsetX, offsetY + h, h, arr))
                            .add(count(offsetX + h, offsetY + h, h, arr));
                }
            }
        }

        //모든 원소가 같은 값인 경우
        if (arr[offsetY][offsetX] == 1) {
            return new Count(0, 1);
        }

        return new Count(1, 0);
    }

    private static class Count {
        public final int zero;
        public final int one;

        public Count(int zero, int one) {
            this.zero = zero;
            this.one = one;
        }

        public Count add (Count other) {
            return new Count(zero + other.zero, one + other.one);
        }
    }

    public int[] solution(int[][] arr) {
        Count count = count(0, 0, arr.length, arr);
        return new int[] {count.zero, count.one};
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] arr = {{1,1,0,0},{1,0,0,0},{1,0,0,1},{1,1,1,1}};
        System.out.println(Arrays.toString(main.solution(arr)));
    }
}
