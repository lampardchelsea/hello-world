# Leetcode dynamic programming patterns: Minimum (Maximum) Path to Reach a Target

=============================================================================================
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
return memo[/*state parameters*/] = result;
Bottom-Up
for (int i = 1; i <= target; ++i) {
   for (int j = 0; j < ways.size(); ++j) {
       if (ways[j] <= i) {
           dp[i] = min(dp[i], dp[i - ways[j]] + cost / path / sum) ;
       }
   }
}
 
return dp[target]
Similar Problems
746. Min Cost Climbing Stairs Easy

Top-Down
int result = min(minCost(n-1, cost, memo), minCost(n-2, cost, memo)) + (n == cost.size() ? 0 : cost[n]);
return memo[n] = result;
Bottom-Up
for (int i = 2; i <= n; ++i) {
   dp[i] = min(dp[i-1], dp[i-2]) + (i == n ? 0 : cost[i]);
}
 
return dp[n]
64. Minimum Path Sum Medium

Top-Down
int result = min(pathSum(i+1, j, grid, memo), pathSum(i, j+1, grid, memo)) + grid[i][j];
    
return memo[i][j] = result;
Bottom-Up
for (int i = 1; i < n; ++i) {
   for (int j = 1; j < m; ++j) {
       grid[i][j] = min(grid[i-1][j], grid[i][j-1]) + grid[i][j];
   }
}
 
return grid[n-1][m-1]
322. Coin Change Medium

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
931. Minimum Falling Path Sum Medium

983. Minimum Cost For Tickets Medium

650. 2 Keys Keyboard Medium

279. Perfect Squares Medium

1049. Last Stone Weight II Medium

120. Triangle Medium

474. Ones and Zeroes Medium

221. Maximal Square Medium

322. Coin Change Medium

1240. Tiling a Rectangle with the Fewest Squares Hard

174. Dungeon Game Hard

871. Minimum Number of Refueling Stops Hard

=============================================================================================


<p>Min Cost Climbing Stairs
<p>1. [Easy to understand C++ using DP with detailed explanation] (https://leetcode.com/problems/min-cost-climbing-stairs/discuss/110111/Easy-to-understand-C++-using-DP-with-detailed-explanation)
