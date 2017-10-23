/**
 * Refer to
 * https://leetcode.com/problems/coin-change/description/
 * You are given coins of different denominations and a total amount of money amount. 
   Write a function to compute the fewest number of coins that you need to make up 
   that amount. If that amount of money cannot be made up by any combination of 
   the coins, return -1.

    Example 1:
    coins = [1, 2, 5], amount = 11
    return 3 (11 = 5 + 5 + 1)

    Example 2:
    coins = [2], amount = 3
    return -1.

    Note:
    You may assume that you have an infinite number of each kind of coin.
 *
 * Solution
 * https://leetcode.com/articles/coin-change/
 * http://www.cnblogs.com/grandyang/p/5138186.html
 * http://romanenco.com/coin-change-problem
*/
// Solution 1: Brute Force (DFS Traverse)
// Time complexity : O(S^n) In the worst case, complexity is exponential in the number of the coins n. 
//                   The reason is that every coin denomination Ci could have at most S/Ci values.
//                   Therefore, the number of combinations is (S/C1) * (S/C2) * (S/C3)... * (S/Cn)
// Space complexity : O(n). In the worst case the maximum depth of recursion is n. Therefore we need 
//                    O(n) space used by the system recursive stack.
class Solution {
    public int coinChange(int[] coins, int amount) {
        return helper(0, coins, amount);
    }
    
    private int helper(int coinIdx, int[] coins, int amount) {
        if(amount == 0) {
            return 0;
        }
        if(coinIdx < coins.length && amount > 0) {
            int maxCurrCoinNum = amount / coins[coinIdx];
            int minCoinsNum = Integer.MAX_VALUE;
            for(int i = 0; i <= maxCurrCoinNum; i++) {
                if(amount >= i * coins[coinIdx]) {
                    int additionalCoinsNeed = helper(coinIdx + 1, coins, amount - i * coins[coinIdx]);
                    if(additionalCoinsNeed != -1) {
                        minCoinsNum = Math.min(minCoinsNum, additionalCoinsNeed + i);
                    }
                }
            }
            return (minCoinsNum == Integer.MAX_VALUE) ? -1 : minCoinsNum;
        }
        return -1;
    }
}

// Solution 2:
