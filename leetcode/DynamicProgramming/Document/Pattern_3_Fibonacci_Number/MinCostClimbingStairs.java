/**
 Refer to
 https://leetcode.com/problems/min-cost-climbing-stairs/
 On a staircase, the i-th step has some non-negative cost cost[i] assigned (0 indexed).

Once you pay the cost, you can either climb one or two steps. You need to find minimum 
cost to reach the top of the floor, and you can either start from the step with index 0, 
or the step with index 1.

Example 1:
Input: cost = [10, 15, 20]
Output: 15
Explanation: Cheapest is start on cost[1], pay that cost and go to the top.

Example 2:
Input: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
Output: 6
Explanation: Cheapest is start on cost[0], and only step on 1s, skipping cost[3].

Note:
cost will have a length in the range [2, 1000].
Every cost[i] will be an integer in the range [0, 999].
*/

// Soltion 1: Native DFS (TLE)
// Refer to
// https://leetcode.com/problems/min-cost-climbing-stairs/discuss/221821/Recursion-Top-Down-Memoization-Bottom-up-DP-Java-100-AC
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        return Math.min(helper(cost, 0), helper(cost, 1));
    }
    
    private int helper(int[] cost, int n) {
        if(n == cost.length - 1 || n == cost.length - 2) {
            return cost[n];
        }
        int onestep = helper(cost, n + 1);
        int twosteps = helper(cost, n + 2);
        return Math.min(onestep, twosteps) + cost[n];
    }
}

// Solution 2: Top-down DP
// Refer to
// https://leetcode.com/problems/min-cost-climbing-stairs/discuss/221821/Recursion-Top-Down-Memoization-Bottom-up-DP-Java-100-AC
// Runtime: 1 ms, faster than 99.89% of Java online submissions for Min Cost Climbing Stairs.
// Memory Usage: 38.2 MB, less than 91.07% of Java online submissions for Min Cost Climbing Stairs.
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        Integer[] memo = new Integer[cost.length + 1];
        return Math.min(helper(cost, 0, memo), helper(cost, 1, memo));
    }
    
    private int helper(int[] cost, int n, Integer[] memo) {
        if(n == cost.length - 1 || n == cost.length - 2) {
            return cost[n];
        }
        if(memo[n] != null) {
            return memo[n];
        }
        int onestep = helper(cost, n + 1, memo);
        int twosteps = helper(cost, n + 2, memo);
        int min = Math.min(onestep, twosteps) + cost[n];
        memo[n] = min;
        return min;
    }
}

// Solution 3: Bottom up DP
// Refer to
// https://leetcode.com/problems/min-cost-climbing-stairs/discuss/?currentPage=1&orderBy=most_votes&query=
