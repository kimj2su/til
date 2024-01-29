package org.example.leetcode.array.besttimetobuyandsellstock;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
 */
public class Main {

    private static int[] prices = {7, 1, 5, 3, 6, 4};
    public static void main(String[] args) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            }
            if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }
        System.out.println(maxProfit);
    }
}
