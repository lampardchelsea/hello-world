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


