/**
 * Refer to
 * https://leetcode.com/problems/unique-paths-ii/#/description
 * Follow up for "Unique Paths":
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
 * For example,
	There is one obstacle in the middle of a 3x3 grid as illustrated below.
	
	[
	  [0,0,0],
	  [0,1,0],
	  [0,0,0]
	]

 * The total number of unique paths is 2.
 * Note: m and n will be at most 100.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/10974/short-java-solution
 * 
 * https://segmentfault.com/a/1190000003502805
 * 动态规划
 * 复杂度
 * 时间 O(NM) 空间 O(NM)
 * 思路
 * 解法和上题一模一样，只是要判断下当前格子是不是障碍，如果是障碍则经过的路径数为0。需要注意的是，
 * 最上面一行和最左边一列，一旦遇到障碍就不再赋1了，因为沿着边走的那条路径被封死了。
 */
public class UniquePathsII {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++) {
            if(obstacleGrid[i][0] == 1) {
                break;
            }
            dp[i][0] = 1;
        }
        for(int j = 0; j < n; j++) {
            if(obstacleGrid[0][j] == 1) {
                break;
            }
            dp[0][j] = 1;
        }
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                if(obstacleGrid[i][j] != 1) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return dp[m - 1][n - 1];
    }
    
    public static void main(String[] args) {
    	
    }
}

