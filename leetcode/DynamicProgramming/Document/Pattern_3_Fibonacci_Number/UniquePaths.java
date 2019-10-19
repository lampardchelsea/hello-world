/** 
 Refer to
 https://leetcode.com/problems/unique-paths/
 A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach 
the bottom-right corner of the grid (marked 'Finish' in the diagram below).

How many possible unique paths are there?
Above is a 7 x 3 grid. How many possible unique paths are there?
Note: m and n will be at most 100.

Example 1:
Input: m = 3, n = 2
Output: 3
Explanation:
From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
1. Right -> Right -> Down
2. Right -> Down -> Right
3. Down -> Right -> Right

Example 2:
Input: m = 7, n = 3
Output: 28
*/

// Solution 1: Native DFS
// Refer to
// https://leetcode.com/problems/unique-paths/discuss/182143/Recursive-memoization-and-dynamic-programming-solutions
class Solution {
    public int uniquePaths(int m, int n) {
        // Minus 1 because the real distance between
        // top left corner and bottom right corner
        // is m - 1 and n - 1
        return helper(m - 1, n - 1);
    }
    
    private int helper(int m, int n) {
        if(m < 0 || n < 0) {
            return 0;
        }
        if(m == 0 || m == 0) {
            return 1;
        }
        return helper(m - 1, n) + helper(m, n - 1);
    }
}

// Solution 2: Top down DP (DFS + memoization)
// Refer to
// https://leetcode.com/problems/unique-paths/discuss/182143/Recursive-memoization-and-dynamic-programming-solutions
class Solution {
    public int uniquePaths(int m, int n) {
        // Minus 1 because the real distance between
        // top left corner and bottom right corner
        // is m - 1 and n - 1
        Integer[][] memo = new Integer[m][n];
        return helper(m - 1, n - 1, memo);
    }
    
    private int helper(int m, int n, Integer[][] memo) {
        if(m < 0 || n < 0) {
            return 0;
        }
        if(m == 0 || m == 0) {
            return 1;
        }
        if(memo[m][n] != null) {
            return memo[m][n];
        }
        int result = helper(m - 1, n, memo) + helper(m, n - 1, memo);
        memo[m][n] = result;
        return result;
    }
}
