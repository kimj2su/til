package org.example.leetcode.array.TwoSum;

/**
 * https://leetcode.com/problems/two-sum/submissions/1160035006/
 */
public class TwoSum {
    private static int[] nums = {2, 7, 11, 15};
    private static int target = 9;
    public static void main(String[] args) {
        int range = nums.length;
        int[] result = new int[2];
        for (int i = 0; i < range; i++) {
            for (int j = i + 1; j < range; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
    }
}
