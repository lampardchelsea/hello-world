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

// Solution 2: Top Down DP Memoization
// Previous work history
/**
// Solution 2: DFS + Memoization
// Wrong solution with 'i' on recursive
class Solution {
    public int coinChange(int[] coins, int amount) {
        // key = amount, value = number of coins
        //Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
        int[] memo = new int[amount];
        int result = helper(coins, amount, 0, memo);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int helper(int[] coins, int amount, int index, int[] memo) {
        // Complete, be careful, its return 0 not 1
        if(amount == 0) {
            return 0;
        }
        // Invalid
        if(amount < 0) {
           return -1; 
        }
        if(memo[amount - 1] != 0) {
            return memo[amount - 1];
        }
        int min = Integer.MAX_VALUE;
        for(int i = index; i < coins.length; i++) {
            int temp = helper(coins, amount - coins[i], i, memo);
            if(temp >= 0 && temp < min) {
                min = temp + 1;
            }
        }
        memo[amount - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return min;
    }
}

// Right Solutoin
// It should be 'index' on the recursive to make it right
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] memo = new int[amount];
        int result = helper(coins, amount, 0, memo);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int helper(int[] coins, int amount, int index, int[] memo) {
        // Complete, be careful, its return 0 not 1
        if(amount == 0) {
            return 0;
        }
        // Invalid
        if(amount < 0) {
           return -1; 
        }
        if(memo[amount - 1] != 0) {
            return memo[amount - 1];
        }
        int min = Integer.MAX_VALUE;
        for(int i = index; i < coins.length; i++) {
            // Recursive strategy same as Combination Sum IV
            // as we can duplicately use the same number
            // so not i + 1 and also not i as we need to revert
            // back to use previous used denominations
            // int temp = helper(coins, amount - coins[i], i, map);
            int temp = helper(coins, amount - coins[i], index, memo);
            if(temp >= 0 && temp < min) {
                min = temp + 1;
            }
        }
        memo[amount - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return min;
    }
}


// And if you want to print all combinations (including the minimum length one), need to remove the 'memo'
// since it will quickly return and not print all matching condition result, and use the same strategy
// from Permutations https://leetcode.com/problems/permutations/
// e.g
// given {1,2,5}, 5 will have 8 combinations
// [[1, 1, 1, 1, 1], [1, 1, 1, 2], [1, 1, 2, 1], [1, 2, 1, 1], [1, 2, 2], [2, 1, 1, 1], [2, 1, 2], [2, 2, 1], [5]]
class Solution {
    public int coinChange(int[] coins, int amount) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        int result = helper(coins, amount, 0, new ArrayList<Integer>(), list);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int helper(int[] coins, int amount, int index, List<Integer> list, List<List<Integer>> combinations) {
        // Complete, be careful, its return 0 not 1
        if(amount == 0) {
        	combinations.add(new ArrayList<Integer>(list));
            return 0;
        }
        // Invalid
        if(amount < 0) {
           return -1; 
        }
        int min = Integer.MAX_VALUE;
        for(int i = index; i < coins.length; i++) {
        	list.add(coins[i]);
            int temp = helper(coins, amount - coins[i], index, list, combinations);
            if(temp >= 0 && temp < min) {
                min = temp + 1;
            }
            list.remove(list.size() - 1);
        }
        return min;
    }
}


// And even we shift the storage from array to HashMap (also no need amount - 1 since that's only required when initialize
// an array with length as 'amount' (new int[amount]) rather than array length as amount + 1 (new int[amount + 1]))
class Solution {
    public int coinChange(int[] coins, int amount) {
        // key = amount, value = number of coins
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
        //int[] memo = new int[amount];
        int result = helper(coins, amount, 0, map);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int helper(int[] coins, int amount, int index, Map<Integer, Integer> map) {
        // Complete, be careful, its return 0 not 1
        if(amount == 0) {
            return 0;
        }
        // Invalid
        if(amount < 0) {
           return -1; 
        }
        if(map.containsKey(amount)) {
            return map.get(amount);
        }
        // if(memo[amount - 1] != 0) {
        //     return memo[amount - 1];
        // }
        int min = Integer.MAX_VALUE;
        for(int i = index; i < coins.length; i++) {
            int temp = helper(coins, amount - coins[i], index, map);
            if(temp >= 0 && temp < min) {
                min = temp + 1;
            }
        }
        int temp = (min == Integer.MAX_VALUE ? -1 : min);
        map.put(amount, temp);
        //memo[amount - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return min;
    }
}
*/

// Refer to
// https://leetcode.com/problems/coin-change/discuss/141064/Unbounded-Knapsack
/**
This problem follows the Unbounded Knapsack pattern.
A brute-force solution could be to try all combinations of the given coins to select the ones that sum up to amount with minimum coins.
There are overlapped subproblems, e.g.
amount = 10, coins = [1, 2, 5]

select 2:
10 - 2 = 8

select 1, select 1:
10 - 1 - 1 = 8

both cases become to get the fewest number of coins that you need to make up 8
We can use memoization to overcome the overlapping sub-problems.
*/
// Style 1:
class Solution {
    public int coinChange(int[] coins, int amount) {
        // If not create as int[amount + 1] need to shift back 1 index
        // to map dp[amount - 1] to value calculate out from dfs, since
        // index start from 0 but amount start from 1
        int[] dp = new int[amount];
        int result = helper(coins, amount, 0, dp);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int helper(int[] coins, int remain, int index, int[] dp) {
        if(remain < 0) {
            return -1;
        }
        if(remain == 0) {
            return 0;
        }
        if(dp[remain - 1] != 0) {
            return dp[remain - 1];
        }
        int min = Integer.MAX_VALUE;
        for(int i = index; i < coins.length; i++) {
            int tmp = helper(coins, remain - coins[i], index, dp);
            if(tmp >= 0 && tmp < min) {
                min = tmp + 1;
            }
        }
        dp[remain - 1] = min;
        return min;
    }
}

// Style 2:
class Solution {
    public int coinChange(int[] coins, int amount) {
        // '+1' for make the array easy to use, e.g dp[amount] is easy
        // to assign value rather than dp[amount - 1]
        // Also use 'Integer' instead of 'int' is easy to check when
        // comes to 'dp[remain] != null'
        Integer[] dp = new Integer[amount + 1];
        int result = helper(coins, amount, 0, dp);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int helper(int[] coins, int remain, int index, Integer[] dp) {
        if(remain < 0) {
            return -1;
        }
        if(remain == 0) {
            return 0;
        }
        if(dp[remain] != null) {
            return dp[remain];
        }
        int min = Integer.MAX_VALUE;
        for(int i = index; i < coins.length; i++) {
            int tmp = helper(coins, remain - coins[i], index, dp);
            if(tmp >= 0 && tmp < min) {
                min = tmp + 1;
            }
        }
        dp[remain] = min;
        return min;
    }
}

// Solution 3: 2D-DP (But not recommand in Unbounded Knapsack, since in Unbounded Knapsack usually only go with 1D-DP)
// Correct Solution with Double.POSITIVE_INFINITY to present infinite number in Java, 
// otherwise if using Integer.MAX_VALUE will encounter crossing Integer.MAX_VALUE issue.
// Refer to
// https://www.kodefork.com/learn/algorithms/dynamic-programming/
// https://leetcode.com/problems/coin-change/discuss/139725/3-different-solutions%3A-(1)-Backtracking-with-memo-(2)-2d-DP-(3)-1d-DP
class Solution {
    /**
        dp[i][j] means fewest number of coins based on i type of coins for j amount
        e.g We have 4 denomination of coins as [1, 5, 6, 8], and target 11.
        Build a 2D array as x-axis -> amount = 1 to 11, y-axis -> coins type = 1, 5, 6, 8. 
        Take a random cell as example, dp[2][6] have i = 2 means we have 2 coins 
        type as denomination = 1 and 5, j = 6 means our target amount = 6,
        dp[2][6] = 2 means at least it requires 2 coins to get target amount as 1 coin
        of denomination of 1 and 1 coin of denomination of 5.
        If target = 0(i = 0), for all denomiation of coins we need 0 coins to reach target,
        fill first column as 0 by dp[0][j] = 0.
        If denomination = 0(j = 0), for all target amount we have no way to reach target,
        it can be omitted, just for understanding as we have initialized with infinity,
        and in Java, Integer.MAX_VALUE not infinity, use Double.POSITIVE_INFINITY implement.
        -------------------------------------------------------------------------------
        |     |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |  10 |  11 |
        -------------------------------------------------------------------------------
        |  0  |  0  | INF | INF | INF | INF | INF | INF | INF | INF | INF | INF | INF |
        -------------------------------------------------------------------------------
        |  1  |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |  10 |  11 |
        -------------------------------------------------------------------------------
        |  5  |  0  |  1  |  2  |  3  |  4  |  1  |  2  |  3  |  4  |  5  |  2  |  3  |
        -------------------------------------------------------------------------------
        |  6  |  0  |  1  |  2  |  3  |  4  |  1  |  1  |  2  |  3  |  4  |  2  |  2  |
        -------------------------------------------------------------------------------
        |  8  |  0  |  1  |  2  |  3  |  4  |  1  |  1  |  2  |  1  |  2  |  2  |  2  |
        -------------------------------------------------------------------------------
    */
    public int coinChange(int[] coins, int amount) {
        double[][] dp = new double[1 + coins.length][1 + amount];
        dp[0][0] = 0;
        for(int i = 1; i <= coins.length; i++) {
            dp[i][0] = 0;
        }
        for(int i = 1; i <= amount; i++) {
            dp[0][i] = Double.POSITIVE_INFINITY;
        }
        for(int i = 1; i <= coins.length; i++) {
            for(int j = 1; j <= amount; j++) {
                // Not pick up current denomination coin
                dp[i][j] = dp[i - 1][j];
                if(j >= coins[i - 1]) {
                    // Pick up current denomination coin, hence number of coins
                    // used will increase 1, then compare with not pick up case
                    // to find minimum number of coins required
                    dp[i][j] = Math.min(dp[i][j], 1 + dp[i][j - coins[i - 1]]);
                }
            }
        }
        return (int)(dp[coins.length][amount] == Double.POSITIVE_INFINITY ? -1 : dp[coins.length][amount]);
    }
}

// Solution 4: 1D-DP
// Refer to
// https://leetcode.com/discuss/general-discussion/458695/dynamic-programming-patterns#Minimum-(Maximum)-Path-to-Reach-a-Target
/**
Minimum (Maximum) Path to Reach a Target
Problem list: https://leetcode.com/list/55ac4kuc

Generate problem statement for this pattern

Statement
Given a target find minimum (maximum) cost / path / sum to reach the target.

Approach
Choose minimum (maximum) path among all possible paths before the current state, then add value for the current state.

routes[i] = min(routes[i-1], routes[i-2], ... , routes[i-k]) + cost[i]
Generate optimal solutions for all values in the target and return the value for the target.

Top-Down
for (int j = 0; j < ways.size(); ++j) {
    result = min(result, topDown(target - ways[j]) + cost/ path / sum);
}
return memo[state parameters] = result;
Bottom-Up
for (int i = 1; i <= target; ++i) {
   for (int j = 0; j < ways.size(); ++j) {
       if (ways[j] <= i) {
           dp[i] = min(dp[i], dp[i - ways[j]] + cost / path / sum) ;
       }
   }
}
 
return dp[target]
==============================================================================
322. Coin Change

Top-Down
for (int i = 0; i < coins.size(); ++i) {
    if (coins[i] <= target) { // check validity of a sub-problem
        result = min(ans, CoinChange(target - coins[i], coins) + 1);
    }
}
return memo[target] = result;

Bottom-Up
for (int j = 1; j <= amount; ++j) {
   for (int i = 0; i < coins.size(); ++i) {
       if (coins[i] <= j) {
           dp[j] = min(dp[j], dp[j - coins[i]] + 1);
       }
   }
}
*/

// Style 1: Additional check for 'dp[i - coins[j]] != Integer.MAX_VALUE' required since (Integer.MAX_VALUE + 1) will cause a problem
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // Initialize as Integer.MAX_VALUE need additional check later
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i = 1; i <= amount; i++) {
            for(int j = 0; j < coins.length; j++) {
                // Additional check to avoid overflow
                if(coins[j] <= i && dp[i - coins[j]] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}

// Style 2: Since at most we only need (amount + 1) coins to make up 'amount', instead of initialize as Integer.MAX_VALUE, we can initialize as (ammount + 1)
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // Initialize as amount + 1 no need additional check later
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for(int i = 1; i <= amount; i++) {
            for(int j = 0; j < coins.length; j++) {
                if(coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
