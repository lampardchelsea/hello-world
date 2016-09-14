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
