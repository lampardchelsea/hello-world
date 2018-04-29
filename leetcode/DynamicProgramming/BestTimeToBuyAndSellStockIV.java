/**
 * Refer to
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/description/
 * Say you have an array for which the ith element is the price of a given stock on day i.

    Design an algorithm to find the maximum profit. You may complete at most k transactions.

    Note:
    You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

    Example 1:

    Input: [2,4,1], k = 2
    Output: 2
    Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
    Example 2:

    Input: [3,2,6,5,0,3], k = 2
    Output: 7
    Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
                 Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 *
 *
 *
 * Solution
 * leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/discuss/54113/A-Concise-DP-Solution-in-Java/55584
*/
class Solution {
    /**
        Consider two big states: buy and sell.

        b[k] ----> maxProfit with at most k transactions with last operation is BUY
        s[k] ----> maxProfit with at most k transactions with last operation is SELL

        Now look at the states transitions (where p is the price at index i):

        s[ 0 ][ i ] = 0

        b[ 1 ][ i ] = max( b[ 1 ][ i - 1 ], s[ 0 ][ i - 1 ] - p )
        s[ 1 ][ i ] = max( s[ 1 ][ i - 1 ], b[ 1 ][ i - 1 ] + p )

        b[ 2 ][ i ] = max( b[ 2 ][ i - 1 ], s[ 1 ][ i - 1 ] - p )
        s[ 2 ][ i ] = max( s[ 2 ][ i - 1 ], b[ 2 ][ i - 1 ] + p )

        .........

        b[ k ][ i ] = max( b[ k ][ i - 1 ], s[ k-1 ][ i - 1 ] - p )
        s[ k ][ i ] = max( s[ k ][ i - 1 ], b[ k ][ i - 1 ] + p)

        So we can see for each price at index i, s[ k ] depends on previous state of b[ k ] 
        while b[ k ] depends on previous state of s[ k-1 ].

        To avoid previous state of b[ k ] get overwritten, s[ k ] will update before b[ k ].
        To avoid previous state of s[ k-1 ] get overwritten, b[ k ] will update before s[ k-1 ].

        So for each price, the update order should like this:
        s[ k ], b[ k ], s [ k - 1 ], b[ k - 1 ], ....... s[ 1 ], b[ 1 ]

        So leads to below simple code snippet
    */
    public int maxProfit(int k, int[] prices) {
        int len = prices.length;
        if (k >= len / 2) return quickSolve(prices);
        
        int[] sell = new int[k + 1];
        int[] buy = new int[k + 1];
        for(int i = 0; i < buy.length; i++)
            buy[i] = Integer.MIN_VALUE;
        for(int price : prices) {
            for(int i = k; i >= 1; i--) {
                sell[i] = Math.max(sell[i], buy[i] + price);
                buy[i] = Math.max(buy[i], sell[i - 1] - price);
            }
        }
        return sell[k];
    }
    
    private int quickSolve(int[] prices) {
        int len = prices.length;
        int profit = 0;
        for (int i = 1; i < len; i++)
            // as long as there is a price gap, we gain a profit.
            if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
        return profit;
    }
}
