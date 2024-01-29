package org.example.leetcode.array.productofarrayexceptself;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/product-of-array-except-self/submissions/1160114160/
 * https://www.youtube.com/watch?v=FKhisC6Uz0U
 */
public class Main {

    private static int[] nums = {1, 2, 3, 4};
    public static void main(String[] args) {
        // 오른쪽에 곱한 값과  왼쪽에 곱한 값을 곱했을때 자기 자신을 제외한 곱셈을 구할 수 있다.
        // 이걸 코드에서 구현은 왼쪽에서 쭉 곱한 값과 오른쪽부터 곱한 값을 곱해주면 된다.
        int left = 1;
        int right = 1;
        int[] result = new int[nums.length];

        // 1, 1, 2, 6
        for (int idx = 0; idx < nums.length; idx++) {
            result[idx] = left;
            left *= nums[idx];
        }
        // 뒤에서 부터 1부터 4,3,2 순으로 곱해준다.
        // 24, 12, 4, 1
        for (int idx = nums.length - 1; idx >= 0; idx--) {
            result[idx] *= right;
            right *= nums[idx];
        }

        // 위의 두 수를 곱하면 24, 12, 8, 6
        System.out.println(Arrays.toString(result));
    }
}
