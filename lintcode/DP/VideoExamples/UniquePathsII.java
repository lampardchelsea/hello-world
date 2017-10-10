/**
 * Refer to
 * http://www.lintcode.com/en/problem/unique-paths-ii/
 * https://leetcode.com/problems/unique-paths-ii/description/
 *
 * Solution
 * https://www.jiuzhang.com/solution/unique-paths-ii/
*/
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        // State: f[x][y] from (0,0) to (x,y)
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] f = new int[m][n];
        // initialize
        // 1st column
        for(int i = 0; i < m; i++) {
            if(obstacleGrid[i][0] != 1) {
                f[i][0] = 1;
            } else {
                // Must break to assign further position as 1
                // because no position is able to assign to 1
                // anymore when obstacle happen
                break;
            }
        }
        // 1st row
        for(int i = 0; i < n; i++) {
            if(obstacleGrid[0][i] != 1) {
                f[0][i] = 1;
            } else {
                break;
            }
        }
        
        // function
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                if(obstacleGrid[i][j] != 1) {
                    f[i][j] = f[i - 1][j] + f[i][j - 1];
                } else {
                    f[i][j] = 0;
                }
            }
        }
        
        // answer
        return f[m - 1][n - 1];
    }
}
