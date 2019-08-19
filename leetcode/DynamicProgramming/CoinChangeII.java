/**
 * Refer to
 * https://leetcode.com/problems/coin-change-2/description/
 * You are given coins of different denominations and a total amount of money. Write a function to 
   compute the number of combinations that make up that amount. You may assume that you have 
   infinite number of each kind of coin.

    Note: You can assume that

    0 <= amount <= 5000
    1 <= coin <= 5000
    the number of coins is less than 500
    the answer is guaranteed to fit into signed 32-bit integer
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
 *
 * Solution
 * https://discuss.leetcode.com/topic/79071/knapsack-problem-java-solution-with-thinking-process-o-nm-time-and-o-m-space
 * This is a classic knapsack problem. Honestly, I'm not good at knapsack problem, it's really tough for me.

    dp[i][j] : the number of combinations to make up amount j by using the first i types of coins
    State transition:

    not using the ith coin, only using the first i-1 coins to make up amount j, then we have dp[i-1][j] ways.
    using the ith coin, since we can use unlimited same coin, we need to know how many way to make up amount j - coins[i] 
    by using first i coins(including ith), which is dp[i][j-coins[i]]
    Initialization: dp[i][0] = 1

    Once you figure out all these, it's easy to write out the code:

        public int change(int amount, int[] coins) {
            int[][] dp = new int[coins.length+1][amount+1];
            dp[0][0] = 1;

            for (int i = 1; i <= coins.length; i++) {
                dp[i][0] = 1;
                for (int j = 1; j <= amount; j++) {
                    dp[i][j] = dp[i-1][j] + (j >= coins[i-1] ? dp[i][j-coins[i-1]] : 0);
                }
            }
            return dp[coins.length][amount];
        }
    Now we can see that dp[i][j] only rely on dp[i-1][j] and dp[i][j-coins[i]], then we can optimize the space by only 
    using one-dimension array.

        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;
            for (int coin : coins) {
                for (int i = coin; i <= amount; i++) {
                    dp[i] += dp[i-coin];
                }
            }
            return dp[amount];
        }
*/
// New tries
// Solution 1: Native DFS
// Refer to Combination Sum
class Solution {
    public int change(int amount, int[] coins) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(amount, coins, 0, new ArrayList<Integer>(), result);
        return result.size();
    }
    
    private void helper(int amount, int[] coins, int index, List<Integer> list, List<List<Integer>> result) {
        if(amount < 0) {
            return;
        }
        if(amount == 0) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = index; i < coins.length; i++) {
            if(amount >= coins[i]) {
                list.add(coins[i]);
                helper(amount - coins[i], coins, i, list, result);
                list.remove(list.size() - 1);
            }
        }
    }
}

// Solution 2: DFS + Memoization
// We have 3 styles of DFS + Memoization
// https://leetcode.com/problems/coin-change-2/discuss/99239/C-DFS-with-memorization-of-course-DP-is-better
// https://leetcode.com/problems/coin-change-2/discuss/260768/Recursion-with-memoization-O(n*amount)
// https://leetcode.com/problems/coin-change-2/discuss/349651/Java-Solution-top-down-bottom-up-and-optimized-bottom-up
// Choice same style as Coin Change, no prune, just introduce memo to store
// key = amount, index together to build string, index represent how many denominations we can use
// value = how many ways we have under given amount and index
class Solution {
    public int change(int amount, int[] coins) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        return helper(amount, coins, 0, map);
    }

    private int helper(int amount, int[] coins, int index, Map<String, Integer> map) {
        // 'return 1' condition should before 'return 0', e.g amount = 0, coins = []
        if(amount == 0) {
            return 1;
        }
        if(amount < 0 || index == coins.length) {
            return 0;
        }
        String str = amount + "_" + index;
        if(map.containsKey(str)) {
            return map.get(str);
        }
        int result = 0;
        for(int i = index; i < coins.length; i++) {
            if(amount >= coins[i]) {
                result += helper(amount - coins[i], coins, i, map);
            }
        }
        map.put(str, result);
        return result;
    }
}


// Solution 3: 2D-DP
// Refer to
// https://www.kodefork.com/learn/algorithms/dynamic-programming/
class Solution {
    public int change(int amount, int[] coins) {
        int[][] dp = new int[coins.length + 1][amount + 1];
		// amount = 0 and no coins, 1 solution since no need any coin
        dp[0][0] = 1;
		// When no coins, no solution
        for(int i = 1; i <= amount; i++) {
            dp[0][i] = 0;
        }
		// When amount = 0 and have coins, always 1 solution since no need any coin
        for(int i = 1; i <= coins.length; i++) {
            dp[i][0] = 1;
        }
        for(int i = 1; i <= coins.length; i++) {
            for(int j = 1; j <= amount; j++) {
				// Knapsnak issue, we can either choose not use ith coin or use ith coin
				// the result should plus both case together
				// Don't use ith coin case
                dp[i][j] = dp[i - 1][j];
				// Plus use ith coin case
                if(j >= coins[i - 1]) {
                    dp[i][j] += dp[i][j - coins[i - 1]];
                }
            }
        }
        return dp[coins.length][amount];
    }
}


// Solution 4: 1D-DP
// Refer to
// https://leetcode.com/problems/coin-change-2/discuss/99212/Knapsack-problem-Java-solution-with-thinking-process-O(nm)-Time-and-O(m)-Space
// Now we can see that dp[i][j] only rely on dp[i-1][j] and dp[i][j-coins[i]], 
// then we can optimize the space by only using one-dimension array.
class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for(int coin : coins) {
            for(int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
}






















class Solution {
    public int change(int amount, int[] coins) {
        // state: dp[i][j] : the number of combinations to make up amount j by using the first i types of coins
        int[][] dp = new int[coins.length + 1][amount + 1];
        // initialize
        // Question: 
        // Refer to
        // https://discuss.leetcode.com/topic/79071/knapsack-problem-java-solution-with-thinking-process-o-nm-time-and-o-m-space/8
        // A question on why we have Initialization: dp[i][0] = 1, as described by tankztc, 
        // dp[i][j] represent the number of combinations to make up amount j by the first i 
        // types of coins, so dp[i][0] should represent the number of combinations to make 
        // up 0 by the first i types of coins, the number of combinations is 0, 
        // so dp[i][0] = 0 right ? Why initialize as 1 ?
        // 有两种理解方式：
        // 1. “什么硬币都不用”是一种凑钱方式。凑出来的金额只有0一种可能。
        // 2. 为了保证 dp[i][coin[i]] = 1 的一种简化了的初始化方式。
        dp[0][0] = 1;
        for(int i = 1; i < coins.length + 1; i++) {
            dp[i][0] = 1;
        }
        // function
        for(int i = 1; i < coins.length + 1; i++) {
            for(int j = 1; j < amount + 1; j++) {
                // State transition:
                // (1) not using the ith coin, only using the first i-1 coins to 
                //     make up amount j, then we have dp[i-1][j] ways.
                // (2) using the ith coin, since we can use unlimited same coin, 
                //     we need to know how many way to make up amount j - coins[i] 
                //     by using first i coins(including ith), which is dp[i][j-coins[i]]
                dp[i][j] = dp[i - 1][j] + (j >= coins[i - 1] ? dp[i][j - coins[i - 1]] : 0);
            }
        }
        // answer
        return dp[coins.length][amount];
    }
}


// More concies solution
class Solution {
    public int change(int amount, int[] coins) {
        // Now we can see that dp[i][j] only rely on dp[i-1][j] and dp[i][j-coins[i]], 
        // then we can optimize the space by only using one-dimension array.
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for(int coin : coins) {
            for(int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
}





