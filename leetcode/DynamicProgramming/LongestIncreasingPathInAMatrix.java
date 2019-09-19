/**
 * Refer to
 * https://discuss.leetcode.com/topic/34835/15ms-concise-java-solution?page=1
 * To get max length of increasing sequences:
    Do DFS from every cell
    Compare every 4 direction and skip cells that are out of boundary or smaller
    Get matrix max from every cell's max
    Use matrix[x][y] <= matrix[i][j] so we don't need a visited[m][n] array
    The key is to cache the distance because it's highly possible to revisit a cell with dp[][]
    
  * http://www.cnblogs.com/grandyang/p/5148030.html
  * 这道题给我们一个二维数组，让我们求矩阵中最长的递增路径，规定我们只能上下左右行走，
    不能走斜线或者是超过了边界。那么这道题的解法要用递归和DP来解，用DP的原因是为了提高效率，
    避免重复运算。我们需要维护一个二维动态数组dp，其中dp[i][j]表示数组中以(i,j)为起点的
    最长递增路径的长度，初始将dp数组都赋为0，当我们用递归调用时，遇到某个位置(x, y), 
    如果dp[x][y]不为0的话，我们直接返回dp[x][y]即可，不需要重复计算。我们需要以数组中
    每个位置都为起点调用递归来做，比较找出最大值。在以一个位置为起点用DFS搜索时，对其四个
    相邻位置进行判断，如果相邻位置的值大于上一个位置，则对相邻位置继续调用递归，并更新一个
    最大值，搜素完成后返回即可
   
  * For Time Complexity O(m * n)
  * https://discuss.leetcode.com/topic/34835/15ms-concise-java-solution/17?page=1
  * the DFS here is basically like DP with memorization. Every cell that has been computed 
    will not be computed again, only a value look-up is performed. So this is O(mn), m and 
    n being the width and height of the matrix. 
    To be exact, each cell can be accessed 5 times at most: 4 times from the top, bottom, left 
    and right and one time from the outermost double for loop. 4 of these 5 visits will be 
    look-ups except for the first one. So the running time should be lowercase o(5mn), 
    which is of course O(mn).
  *
*/
class Solution {
    int[] dx = {0, 1, 0, -1};
    int[] dy = {-1, 0, 1, 0};
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int max = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                int len = dfs(i, j, m, n, matrix, dp);
                max = Math.max(len, max);
            }
        }
        return max;
    }
    
    private int dfs(int i, int j, int m, int n, int[][] matrix, int[][] dp) {
        if(dp[i][j] != 0) {
            return dp[i][j];
        }
        int curr_max = 1;
        for(int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if(x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] <= matrix[i][j]) {
                continue;
            }
            int len = 1 + dfs(x, y, m, n, matrix, dp);
            curr_max = Math.max(len, curr_max);
        }
        // Assign current path max length to cache into dp[i][j]
        // if encounter same position again, don't need to re-calculate
        // 我们需要维护一个二维动态数组dp，其中dp[i][j]表示数组中以(i,j)为起点的最长递
        // 增路径的长度，初始将dp数组都赋为0，当我们用递归调用时，遇到某个位置(x, y), 
        // 如果dp[x][y]不为0的话，我们直接返回dp[x][y]即可，不需要重复计算。
        dp[i][j] = curr_max;
        return curr_max;
    }
}

// New try with new style
// Refer to
// https://massivealgorithms.blogspot.com/2016/01/leetcode-329-longest-increasing-path-in.html
// http://www.voidcn.com/article/p-gmfwspuq-bqa.html
class Solution {
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix == null || matrix.length == 0) {
            return 0;
        }
        int[][] memo = new int[matrix.length][matrix[0].length];
        int max = 0;
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                // To include the start point itself must set intial 'prev'
                // as Integer.MIN_VALUE for comparison, otherwise not able
                // to start any path since it will block on 'prev >= matrix[i][j]'
                // condition
                int curMax = helper(matrix, Integer.MIN_VALUE, i, j, memo);
                max = Math.max(max, curMax);
            }
        }
        return max;
    }
    
    int[] dx = new int[]{0,0,1,-1};
    int[] dy = new int[]{1,-1,0,0};
    private int helper(int[][] matrix, int prev, int i, int j, int[][] memo) {
        if(i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length
          || prev >= matrix[i][j]) {
            return 0;
        }
        if(memo[i][j] != 0) {
            return memo[i][j];
        }
        int curMax = 0;
        for(int k = 0; k < 4; k++) {
            int new_i = i + dx[k];
            int new_j = j + dy[k];
            int curLen = helper(matrix, matrix[i][j], new_i, new_j, memo) + 1;
            curMax = Math.max(curLen, curMax);
        }
        memo[i][j] = curMax;
        return curMax;
    }
}
