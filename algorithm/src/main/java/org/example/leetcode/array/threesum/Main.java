package org.example.leetcode.array.threesum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.com/problems/3sum/description/"> 문제 링크 </a>
 * <p>
 *     3개의 합이 0이 되어야 하기 때문에 sorted array를 기준으로 left, right를 이동하면서 3개의 합을 구한다.
 *     여기서 정렬한 이유는 3개의 합이 0이 되려면 적어도 1개의 음수, 양수가 있어야 하기 때문에 정렬을 통해 음수, 양수를 찾기 쉽게 하기 위함이다.
 *     세개의 합이 양수가 되는 시점에는 더 이상 연산을 할 필요가 없다.
 *     첫번째 값을 설정하고 이후 나머지 2개의 합은 left, right를 이동하면서 찾는다.
 * </p>
 */
public class Main {

    private static final int[] nums = {-1, 0, 1, 2, -1, -4};
    // private static int[] nums = {0, 1, 1};
    public static void main(String[] args) {
        Main main = new Main();
        List<List<Integer>> result = main.threeSum(nums);
        System.out.println(result);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> parent = new ArrayList<>();
        List<Integer> child = new ArrayList<>();
        if (nums.length >= 3) { // 3개 이상의 숫자가 있어야 함
            Arrays.sort(nums);

            for (int i = 0; i < nums.length; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) { // 중복된 숫자는 건너뛴다.
                    continue;
                }

                // left and right
                int left = i + 1;
                int right = nums.length - 1;

                while (left < right) {
                    int threesSum = nums[i] + nums[left] + nums[right]; // 3개의 합

                    if (threesSum > 0) { // 3개의 합이 양수가 되면 더 이상 연산할 필요가 없다.
                        right--;
                    } else if (threesSum < 0) { // 3개의 합이 음수가 되면 left를 이동한다.
                        left++;
                    } else { //threeSum == 0 일때 결과를 저장하고 left, right를 이동한다.
                        child.add(nums[i]);
                        child.add(nums[left]);
                        child.add(nums[right]);
                        parent.add(child);
                        child = new ArrayList<>();
                        left++; // left를 이동한다.
                        while (left < right && nums[left] == nums[left - 1]) { // 중복된 숫자는 건너뛴다.
                            left++;
                        }
                    }
                }
            }
        }
        return parent;
    }
}
