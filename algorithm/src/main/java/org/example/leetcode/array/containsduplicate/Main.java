package org.example.leetcode.array.containsduplicate;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/contains-duplicate/
 * 정렬을 했기 때문에 같은 숫자가 있다면 바로 옆에 있을 것이다.
 */
public class Main {

    private static int[] nums = {1, 2, 3, 1};
    public static void main(String[] args) {
        int range = nums.length;
        boolean answer = false;
        Arrays.sort(nums);
        for (int i = 0; i < range - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                answer = true;
                break;
            }
        }
        System.out.println(answer);
    }
}
