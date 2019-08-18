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

// Refer to
// https://leetcode.com/problems/coin-change/
// https://leetcode.com/problems/coin-change/discuss/77368/*Java*-Both-iterative-and-recursive-solutions-with-explanations
// Solution 1: Native DFS
class Solution {
    public int coinChange(int[] coins, int amount) {
        int result = helper(coins, amount, 0);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int helper(int[] coins, int amount, int index) {
        // Complete, be careful, its return 0 not 1
        if(amount == 0) {
            return 0;
        }
        // Invalid
        if(amount < 0) {
           return -1; 
        }
        int min = Integer.MAX_VALUE;
        for(int i = index; i < coins.length; i++) {
            // Recursive strategy same as Combination Sum I
            // as we can duplicately use the same number
            // so not i + 1
            int temp = helper(coins, amount - coins[i], index);
            if(temp >= 0 && temp < min) {
                min = temp + 1;
            }
        }
        return min;
    }
}

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


// Solution 3: 2D-DP
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

// Wrong 2D-DP solution with only use Integer.MAX_VALUE to present infinity 
// e.g Input [2],3 -> expected -1, result -2147483648
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[1 + coins.length][1 + amount];
        dp[0][0] = 0;
        for(int i = 1; i <= coins.length; i++) {
            dp[i][0] = 0;
        }
        for(int i = 1; i <= amount; i++) {
            dp[0][i] = Integer.MAX_VALUE;
        }
        for(int i = 1; i <= coins.length; i++) {
            for(int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i - 1][j];
                if(j >= coins[i - 1]) {
                    dp[i][j] = Math.min(dp[i][j], 1 + dp[i][j - coins[i - 1]]);
                }
            }
        }
        return dp[coins.length][amount] == Integer.MAX_VALUE ? -1 : dp[coins.length][amount];
    }
}


// Solution 4: 1D-DP
// Refer to
// https://leetcode.com/problems/coin-change/discuss/139725/3-different-solutions%3A-(1)-Backtracking-with-memo-(2)-2d-DP-(3)-1d-DP
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for(int i = 0; i < dp.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        dp[0] = 0;
        for(int j = 1; j <= amount; j++) {
            for(int i = 0; i < coins.length; i++) {
                if(j >= coins[i] && dp[j - coins[i]] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}




====================================================================================================================================




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
/**
 Algorithm
 Approach #2 (Dynamic programming - Top down = DFS memoization) [Accepted]
 The idea of the algorithm is to build the solution of the problem from top to bottom. 
 It applies the idea described above. It use backtracking and cut the partial solutions 
 in the recursive tree, which doesn't lead to a viable solution. Тhis happens when we 
 try to make a change of a coin with a value greater than the amount SS. To improve time 
 complexity we should store the solutions of the already calculated subproblems in a table.
 
 Complexity Analysis
 Time complexity : O(S*n). where S is the amount, n is denomination count. In the worst case 
                   the recursive tree of the algorithm has height of S and the algorithm solves 
                   only S subproblems because it caches precalculated solutions in a table. 
                   Each subproblem is computed with n iterations, one by coin denomination. 
                   Therefore there is O(S*n) time complexity.
 Space complexity : O(S), where S is the amount to change We use extra space for the memoization table.
*/
class Solution {
    public int coinChange(int[] coins, int amount) {
        if(amount < 1) {
            return 0;
        }
        int[] dp = new int[amount];
        return helper(coins, amount, dp);
    }
    
    // remained: remaining coins after the last step;
    // dp[remained - 1] means amount 'remained' at least requires how many coins
    // e.g dp[0] -> remained = 1, dp[1] -> remained = 2...
    private int helper(int[] coins, int remained, int[] dp) {
        // Not valid
        if(remained < 0) {
            return -1;
        }
        // Completed
        if(remained == 0) {
            return 0;
        }
        // Already computed, so reuse, this return condition is hard to get
        if(dp[remained - 1] != 0) {
            return dp[remained - 1];
        }
        int min = Integer.MAX_VALUE;
        for(int coin : coins) {
            int res = helper(coins, remained - coin, dp);
            if(res >= 0 && res < min) {
                min = res + 1;
            }
        }
        dp[remained - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return dp[remained - 1];
    }
}


// Solution 3:
/**
 Approach #3 (Dynamic programming - Bottom up = Classic DP) [Accepted]
 Complexity Analysis
 Time complexity : O(S*n). On each step the algorithm finds the next F(i) in nn iterations, where 1 <= i <= S. 
                     Therefore in total the iterations are S∗n.
 Space complexity : O(S). We use extra space for the memoization table.
*/
public class CoinChange {
    public int coinChange(int[] coins, int amount) {
        if(amount < 1) {
            return 0;
        }
        // state: dp[i] represent for amount = i the minimum coins we need
        int[] dp = new int[amount + 1];
        // initialize
        dp[0] = 0;
        for(int i = 1; i < amount + 1; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        // function
        for(int i = 1; i < amount + 1; i++) {
            for(int coin : coins) {
                // Refer to
                // https://discuss.leetcode.com/topic/32475/c-o-n-amount-time-o-amount-space-dp-solution/22?page=2
                // check on possible overflow problem (Integer.MAX_VALUE + 1 = Integer.MIN_VALUE)
                // e.g coins = {2}, amount = 3 -> if not adding 'dp[i - coin] != Integer.MAX_VALUE'
                // will cause output = -2147483648 which expected to be -1
                //if(i - coin >= 0) {
                if(i - coin >= 0 && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        // answer
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
   
   // For Solution 3 we can promote as below:
   // Runtime: 8 ms, faster than 92.36% of Java online submissions for Coin Change.
   // Memory Usage: 35.9 MB, less than 98.30% of Java online submissions for Coin Change.
   // Few changes:
   // (1) dp[i] initailize as 'amount + 1' instead of Integer.MAX_VALUE, then no need to add condition
   //     if(i - coin >= 0 && dp[i - coin] != Integer.MAX_VALUE) {...} for overflow check
   // (2) we can assign max value as 'amount + 1' since at most for 'amount' we only need 'amount'
   //     number of 1 cent, and it will not exceed 'amount + 1', so if after all we see dp[amount]
   //     keep as 'amount + 1', that means value as 'amount' not reachable, that's why we return -1.
   // (3) For 'return dp[amount] == amount + 1 ? -1 : dp[amount];' we use condition as == instead of >
   //     dp[amount] == amount + 1 -> instead of dp[amount] > amount, since the largest possible value
   //     for dp[amount] is always amount + 1, and this improve the runtime a lot.
   class Solution {
    public int coinChange(int[] coins, int amount) {
        if(amount < 1) {
            return 0;
        }
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        for(int i = 1; i < dp.length; i++) {
            dp[i] = amount + 1;
        }
        for(int i = 1; i <= amount; i++) {
            for(int coin : coins) {
                if(i - coin >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}
   
   
    public static void main(String[] args) {
    	CoinChange c = new CoinChange();
    	int[] coins = {2};
    	int amount = 3;
    	int result = c.coinChange(coins, amount);
    	System.out.print(result);
    }
}
