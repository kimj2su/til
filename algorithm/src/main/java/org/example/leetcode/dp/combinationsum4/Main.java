package org.example.leetcode.dp.combinationsum4;

/**
 * https://leetcode.com/problems/combination-sum-iv/description/
 */
public class Main {

    private static int[] nums = {1, 2, 3};
    private static int target = 4;

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.combinationSum4(nums, target));
    }

    /**
     * dp bottom-up
     * target의 경우의 수는 targets[0] = 1 을 넣고
     * comb[target] = comb[target - nums[0]] + comb[target - nums[1]] + ... + comb[target - nums[n]] 를 반복한다.
     * 결과적으로 targets에는 target까지의 결과 값을 들어있게 된다.
     */
    private int combinationSum4(int[] nums, int target) {
        int[] targets = new int[target + 1];
        targets[0] = 1;
        for (int i = 1; i < target + 1; i++) {
            for (int num : nums) {
                if (i >= num) {
                    targets[i] += targets[i - num];
                }
            }
        }
        return targets[target];
    }

    /**
     * dp top-down
     * target의 경우의 수는 targets[0] = 1 을 넣고
     * getComb(target] 함수는 targets 배열에 targets[target] 값이 없으면 실행해 해당 값을 구하고 배열에 넣는다.
     * 함수를 재귀적으로 실행해 목표 값에 필요한 연산을 한번씩 하면서 결과를 얻는다.
     */
}
