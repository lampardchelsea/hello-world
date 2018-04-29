/**
 * Refer to
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/description/
 * Say you have an array for which the ith element is the price of a given stock on day i.

    Design an algorithm to find the maximum profit. You may complete at most two transactions.

    Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).

    Example 1:

    Input: [3,3,5,0,0,3,1,4]
    Output: 6
    Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
                 Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
    Example 2:

    Input: [1,2,3,4,5]
    Output: 4
    Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
                 Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
                 engaging multiple transactions at the same time. You must sell before buying again.
    Example 3:

    Input: [7,6,4,3,1]
    Output: 0
    Explanation: In this case, no transaction is done, i.e. max profit = 0.
 *
 *
 * Solution
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/39611/Is-it-Best-Solution-with-O(n)-O(1).
  The thinking is simple and is inspired by the best solution from Single Number II (I read through the discussion after I use DP).
  Assume we only have 0 money at first;
  4 Variables to maintain some interested 'ceilings' so far:
  The maximum of if we've just buy 1st stock, if we've just sold 1nd stock, if we've just buy 2nd stock, if we've just sold 2nd stock.
  Very simple code too and work well. I have to say the logic is simple than those in Single Number II.
 * 
*/
class Solution {
    public int maxProfit(int[] prices) {
        // Assume we only have 0 money initially
        int release1 = 0;
        int release2 = 0;
        int hold1 = Integer.MIN_VALUE;
        int hold2 = Integer.MIN_VALUE;
        for(int price : prices) {
            hold1 = Math.max(hold1, -price);                // The maximum if we've just buy 1st stock so far.
            release1 = Math.max(release1, hold1 + price);   // The maximum if we've just sold 1st stock so far.
            hold2 = Math.max(hold2, release1 - price);      // The maximum if we've just buy 2nd stock so far.
            release2 = Math.max(release2, hold2 + price);   // The maximum if we've just sold 2nd stock so far.
        }
        return release2;
    }
}
