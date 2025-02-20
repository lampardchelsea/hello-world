/**
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * If you were only permitted to complete at most one transaction (ie, buy one and sell 
 * one share of the stock), design an algorithm to find the maximum profit.
 * Example 1:
 * Input: [7, 1, 5, 3, 6, 4]
 * Output: 5
 * max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
 * 
 * Example 2:
 * Input: [7, 6, 4, 3, 1]
 * Output: 0
 * In this case, no transaction is done, i.e. max profit = 0.
 * 
 * Analyze
 * We need to find out the maximum difference (which will be the maximum profit) between two numbers in the given array. 
 * Also, the second number (selling price) must be larger than the first one (buying price).
 * In formal terms, we need to find max(prices[j]−prices[i]), 
 * for every i and j such that j > i.
 * 
 * Complexity Analysis
 * Time complexity : O(n^2) Loop runs n(n-1)/2 times.
 * Space complexity : O(1). Only two variables - maxprofit and profit are used.
 */
public class Solution {
    public int maxProfit(int[] prices) {
        int length = prices.length;
        int maxProfit = 0;
        for(int i = 0; i < length; i++) {
            for(int j = i + 1; j < length; j++) {
                int profit = prices[j] - prices[i];
                if(profit >= maxProfit) {
                    maxProfit = profit;
                }
            }
        }
        
        return maxProfit;
    }
}
