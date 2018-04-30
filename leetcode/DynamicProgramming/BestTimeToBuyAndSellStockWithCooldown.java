/**
 * Refer to
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/
 * Say you have an array for which the ith element is the price of a given stock on day i.

   Design an algorithm to find the maximum profit. You may complete as many transactions as you 
   like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

   You may not engage in multiple transactions at the same time (ie, you must sell the stock 
   before you buy again).
   After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
   Example:

   prices = [1, 2, 3, 0, 2]
   maxProfit = 3
   transactions = [buy, sell, cooldown, buy, sell]
 *
 * Solution
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/126065/Two-DP-methods-very-easy-to-understand-with-detail-explanation
*/
class Solution {
    /**
      Refer to
      https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/126065/Two-DP-methods-very-easy-to-understand-with-detail-explanation
      Second method, also we can simplify state from three state to two state .

        sell: don't have stock(selling stock or rest)
        buy: have stock(buy or keep stock)

        buy[i] means the maxProfit on day[i] if in buy state, becasue we could 
        be in this state by keeping buy state or coming from sell state, but 
        one thing we should be careful: because of the cooldown, if we buy on 
        day[i], we can not sell on day[i-1], in this case sell[i-1] =sell[i-2]. 
        Actually, we coming from sell[i-2].
        buy[i] = Max(buy[i-1], sell[i-2] - price[i])

        sell[i] means the maxProfit on day[i] if in sell state, becasue we could 
        be in this state by selling stock or keeping rest:
        sell[i] = Max(buy[i-1] + price[i], sell[i-1])

        Base case:
        buy[0] = -prices[0]
        buy[1] = Max(-prices[1], -prices[0])
        sell[0] = 0
    */
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length <= 1) {
            return 0;
        }
        int len = prices.length;
        int[] buy = new int[len];
        int[] sell = new int[len];
        buy[0] = -prices[0];     
        for(int i = 1; i < len; i++) {
            if(i == 1) {
                buy[i] = Math.max(buy[i - 1], -prices[i]); 
            } else {
                buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);    
            }        
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }
        return sell[len - 1];
    }
}
