/**
 * Refer to
 * https://leetcode.com/problems/minimum-path-sum/description/
 * http://www.lintcode.com/en/problem/minimum-path-sum/
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom 
   right which minimizes the sum of all numbers along its path.
   Note: You can only move either down or right at any point in time.
 *
 * Solution
 * http://www.jiuzhang.com/solutions/minimum-path-sum/
*/
public class Solution {
    /*
     * @param grid: a list of lists of integers
     * @return: An integer, minimizes the sum of all numbers along its path
     */
    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0) {
            return -1;
        }
        // state: f[x][y] represent minimum path from (0,0) to (x,y)
        int m = grid.length;
        int n = grid[0].length;
        int[][] f = new int[m][n];
        
        // initialize:
        f[0][0] = grid[0][0];
        // 1st column
        for(int i = 1; i < m; i++) {
            f[i][0] = f[i - 1][0] + grid[i][0];
        }
        // 1st row
        for(int i = 1; i < n; i++) {
            f[0][i] = f[0][i - 1] + grid[0][i];
        }
        
        // function
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                f[i][j] = Math.min(f[i - 1][j], f[i][j - 1]) + grid[i][j];
            }
        }
        
        // answer
        return f[m - 1][n - 1];
    }
}
