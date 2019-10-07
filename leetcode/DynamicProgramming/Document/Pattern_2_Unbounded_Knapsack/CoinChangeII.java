/**
 Refer to
 https://leetcode.com/problems/coin-change-2/
 You are given coins of different denominations and a total amount of money. Write a function to 
 compute the number of combinations that make up that amount. You may assume that you have infinite 
 number of each kind of coin.

Example 1:
Input: amount = 5, coins = [1, 2, 5]
Output: 4
Explanation: there are four ways to make up the amount:
5=5
5=2+2+1
5=2+1+1+1
5=1+1+1+1+1

Example 2:
Input: amount = 3, coins = [2]
Output: 0
Explanation: the amount of 3 cannot be made up just with coins of 2.

Example 3:
Input: amount = 10, coins = [10] 
Output: 1

Note:
You can assume that

0 <= amount <= 5000
1 <= coin <= 5000
the number of coins is less than 500
the answer is guaranteed to fit into signed 32-bit integer
*/

// Solution 1: Native DFS (TLE)
class Solution {
    public int change(int amount, int[] coins) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(result, new ArrayList<Integer>(), amount, coins, 0);
        return result.size();
    }
    
    private void helper(List<List<Integer>> result, List<Integer> list, int amount, int[] coins, int index) {
        if(amount == 0) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = index; i < coins.length; i++) {
            if(amount >= coins[i]) {
                list.add(coins[i]);
                helper(result, list, amount - coins[i], coins, i);
                list.remove(list.size() - 1);
            }    
        }
    }
}

// Solution 2: Native DFS without result.size()
class Solution {
    public int change(int amount, int[] coins) {
        return helper(amount, coins, 0);
    }
    
    private int helper(int amount, int[] coins, int index) {
        if(amount == 0) {
            return 1;
        }
        int result = 0;
        for(int i = index; i < coins.length; i++) {
            if(amount >= coins[i]) {
                result += helper(amount - coins[i], coins, i);
            }    
        }
        return result;
    }
}

// Solution 3: Top down DFS + Memoization
// Refer to
// https://leetcode.com/problems/coin-change-2/discuss/162839/Another-approach-%3A-memorized-recursion-Java
class Solution {
    public int change(int amount, int[] coins) {
        // Test out by amount = 0, coins = []
        if(amount == 0) {
            return 1;
        }
        // Test out by amount = 7, coins = []
        if(coins.length == 0) {
            return 0;
        }
        Integer[][] memo = new Integer[coins.length][1 + amount];
        return helper(amount, coins, 0, memo);
    }
    
    private int helper(int amount, int[] coins, int index, Integer[][] memo) {
        if(amount == 0) {
            return 1;
        }
        if(memo[index][amount] != null) {
            return memo[index][amount];
        }
        int result = 0;
        for(int i = index; i < coins.length; i++) {
            if(amount >= coins[i]) {
                result += helper(amount - coins[i], coins, i, memo);
            }    
        }
        memo[index][amount] = result;
        return result;
    }
}

// Solution 4: Bottom up DP
// Refer to
// https://leetcode.com/problems/coin-change-2/discuss/176706/Beginner-Mistake%3A-Why-an-inner-loop-for-coins-doensn't-work-Java-Soln
// Be careful how we initialize the 1st row
class Solution {
    public int change(int amount, int[] coins) {
        // Test out by amount = 0, coins = []
        if(amount == 0) {
            return 1;
        }
        // Test out by amount = 7, coins = []
        if(coins.length == 0) {
            return 0;
        }
        int[][] dp = new int[coins.length][1 + amount];
        for(int i = 0; i < coins.length; i++) {
            dp[i][0] = 1;
        }
        // Initialize the 1st row with tricky, since infinite coins supplied, 
        // not require 'i == coins[0]', just 'i % coins[0] == 0' is fine
        for(int i = 0; i <= amount; i++) {
            dp[0][i] = (i % coins[0] == 0 ? 1 : 0);
        }
        for(int i = 1; i < coins.length; i++) {
            for(int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i - 1][j];
                if(j >= coins[i]) {
                    dp[i][j] += dp[i][j - coins[i]];
                }
            }
        }
        return dp[coins.length - 1][amount];
    }
}
