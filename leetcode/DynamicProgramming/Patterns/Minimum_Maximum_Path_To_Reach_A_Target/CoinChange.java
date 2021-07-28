// Unbounded knapsack

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

// Don't use the 0-1 knapsack template to resolve unbounded knapsack question like coin change
// http://www.mathcs.emory.edu/~cheung/Courses/253/Syllabus/DynProg/knapsack2.html
/**
A recursive solution to the unbounded knapsack problem
Observation:
I can never exhaust any item because there are an unbounded supply of items.
Therefore:
The technique used in the 0,1 knapsack problem cannot be used.

Caveat:
You can only pack the item k into the knapsack if:
      W ≥ wi  !!!
      
Base case(s) in the unbounded knapsack problem
Base case:
When the knapsack is full, you cannot pack any item into the knapsack
Therefore, the total value of a 0 capacity knapsack = 0

In other words:
    M(v, w, 0) = 0;   (can't packet anything into a 0-capacity knapsack) 
*/


// Solution 1: Native DFS(TLE) similar to Combination Sum (different on Combination Sum calculate all possibilities by use backtracking, 
// here just need set up remain < 0 based condition to work with tmp >= 0 and initialize min = Integer.MAX_VALUE to calculate minimum)
// Refer to
// https://leetcode.com/problems/coin-change/discuss/77368/*Java*-Both-iterative-and-recursive-solutions-with-explanations 
class Solution {
    public int coinChange(int[] coins, int amount) {
        int result = helper(coins, amount, 0);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int helper(int[] coins, int remain, int index) {
        // One base condition set as negative (-1) for remain < 0
        // later dfs logic 'tmp >= 0' based on check this condition
        if(remain < 0) {
            return -1;
        }
        // Complete, be careful, its return 0 not 1, which means 0 remain amount
        // only require 0 coin to make it
        if(remain == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        // Recursive strategy same as Combination Sum I
        // as we can duplicately use the same number
        // so not 'i + 1' or 'i' but still use 'index'
        for(int i = index; i < coins.length; i++) {
            // We are using backtracking on combination sum is because 
            // we are calculate all possibilites, but here in coin change 
            // we just need to find minimum coins required to reach target, 
            // so no need to do backtracking
            int tmp = helper(coins, remain - coins[i], index);
            // tmp >= 0 means valid solution, since in base condition
            if(tmp >= 0 && tmp < min) {
                // Don't forget plus one more coin as in use
                min = tmp + 1;
            }
        }
        return min;
    }
}
