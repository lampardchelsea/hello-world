/**
 Refer to
 https://leetcode.com/problems/minimum-path-sum/
 Given a m x n grid filled with non-negative numbers, find a path from top left to 
 bottom right which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

Example:
Input:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
Output: 7
Explanation: Because the path 1→3→1→1→1 minimizes the sum.
*/

// New try
// Refer to
// https://leetcode.com/problems/minimum-path-sum/discuss/23471/DP-with-O(N*N)-space-complexity/135764
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        for(int i = 1; i < rows; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        for(int i = 1; i < cols; i++) {
            grid[0][i] += grid[0][i - 1];
        }
        for(int i = 1; i < rows; i++) {
            for(int j = 1; j < cols; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[rows - 1][cols - 1];
    }
}



// Solution 1: DP (backtracking) + Memoization
// Refer to
// https://loveforprogramming.quora.com/Backtracking-Memoization-Dynamic-Programming
// https://leetcode.com/problems/minimum-path-sum/discuss/23493/My-Java-solution-using-DP-with-memorization-(beats-about-98-submissions)/273665
// Runtime: 1 ms, faster than 99.80% of Java online submissions for Minimum Path Sum.
// Memory Usage: 35.4 MB, less than 100.00% of Java online submissions for Minimum Path Sum.
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        // Initial memo to store already computed back tracking
        // path value on each corresponding position =>
        // Each cell in memo mapping to cell in given grid and
        // store the minimum path sum calculated backwards from
        // bottom right corner cell to this cell
        int[][] memo = new int[m][n];
        return helper(grid, 0, 0, memo);
    }
    
    private int helper(int[][] grid, int i, int j, int[][] memo) {
        if(i < grid.length && j < grid[0].length) {
            if(i == grid.length - 1 && j == grid[0].length - 1) {
                // Store current cell value in memo if we reach
                // to bottom right and return it
                memo[i][j] = grid[i][j];
                return memo[i][j];
            }
            // Consult memo in case we have already calculated routes
            // for a particular cell, if the result not as initial as
            // 0, return it, otherwise use the usual recursion on
            // bottom and right direction
            if(memo[i][j] != 0) {
                return memo[i][j];
            }
            int bottom = helper(grid, i + 1, j, memo);
            int right = helper(grid, i, j + 1, memo);
            // Take the minimum value for a cell on a decision tree
            int min = Math.min(bottom, right);
            // Add cell value to the minimum value from left or right child
            memo[i][j] = min + grid[i][j];
            return memo[i][j];
        }
        return Integer.MAX_VALUE;
    }
}
