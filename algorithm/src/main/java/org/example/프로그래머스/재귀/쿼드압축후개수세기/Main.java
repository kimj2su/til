package org.example.프로그래머스.재귀.쿼드압축후개수세기;

public class Main {
    private static class Count {
        public final int zero;
        public final int one;

        public Count(int zero, int one) {
            this.zero = zero;
            this.one = one;
        }

        private Count count(int offsetX, int offSetY, int size, int[][] arr) {
            for (int x = offsetX; x < offsetX + size; x++) {
                for (int y = offSetY; y < offSetY + size; y++) {
                    if (arr[y][x] != arr[offSetY][offsetX]) {
                        return count(offsetX, offSetY, size / 2, arr);
                                // .add(count(offsetX + size / 2, offSetY, size / 2, arr))
                                // .add(count(offsetX, offSetY + size / 2, size / 2, arr))
                                // .add(count(offsetX + size / 2, offSetY + size / 2, size / 2, arr));
                    }
                }
            }
            return new Count(arr[offSetY][offsetX] == 0 ? 1 : 0, arr[offSetY][offsetX] == 1 ? 1 : 0);
        }
    }
}
