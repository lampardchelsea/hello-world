/**
 Refer to
 http://leetcode.liangjiateng.cn/leetcode/longest-line-of-consecutive-one-in-matrix/description
 Given a 01 matrix M, find the longest line of consecutive one in the matrix. 
 The line could be horizontal, vertical, diagonal or anti-diagonal.
  Example:
  Input:
  [[0,1,1,0],
   [0,1,1,0],
   [0,0,0,1]]
  Output: 3
 Hint: The number of elements in the given matrix will not exceed 10,000.
*/

// Solution 1: Native DFS
// Refer to
// https://massivealgorithms.blogspot.com/2017/04/leetcode-562-longest-line-of.html
// https://discuss.leetcode.com/topic/87228/java-strightforward-solution
/**
 递归的解法，同时maintain一个最大值，注意8个方向中只需要走4个方向即可。
 下面我们来优化空间复杂度，用一种类似于DFS的思路来解决问题，我们在遍历到为1的点时，对其水平方向，竖直方向，
 对角线方向和逆对角线方向分别不停遍历，直到越界或者遇到为0的数字，同时用计数器来累计1的个数，这样就可以用
 来更新结果res了，就不用把每个中间结果都保存下来
*/
class Solution {
    public int longestLine(int[][] M) {
        if (M == null || M.length == 0) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                if (M[i][j] == 1) {
                    result = Math.max(result, helper(M, i, j));
                }
            }
        }
        return result;
    }
    // Only keep 4 direction then no overlap
    int[] dx = new int[] {0, 1, 1, 1};
    int[] dy = new int[] {1, 0, 1, -1};
    private int helper(int[][] M, int i, int j) {
        int result = 1;
        for (int k = 0; k < 4; k++) {
            // Each direction will have a result
            int curDirCount = 1;
            int new_i = i + dx[k];
            int new_j = j + dy[k];
            // Keep on current direction with while loop
            while (new_i < M.length && new_i >= 0 &&
                new_j < M[0].length && new_j >= 0 &&
                M[new_i][new_j] == 1) {
                new_i += dx[k];
                new_j += dy[k];
                curDirCount++;
            }
            // Compare all (4) direction results
            result = Math.max(result, curDirCount);
        }
        return result;
    }
}

// Solution 2: 3D DP
// Refer to
// https://massivealgorithms.blogspot.com/2017/04/leetcode-562-longest-line-of.html
// https://discuss.leetcode.com/topic/87197/java-o-nm-time-dp-solution
/**
 dp[i][j][k]表示从开头遍历到数字nums[i][j]为止，第k种情况的连续1的个数. 
 k的值为0，1，2，3，分别对应水平，竖直，对角线和逆对角线这四种情况。
 更新dp数组过程:
 如果如果数字为0的情况直接跳过，然后水平方向就加上前一个的dp值，竖直方向加上上面一个数字的dp值，
 对角线方向就加上右上方数字的dp值，逆对角线就加上左上方数字的dp值，然后每个值都用来更新结果res.
*/
class Solution {
    public int longestLine(int[][] M) {
        if (M == null || M.length == 0) {
            return 0;
        }
        int result = 0;
        int[][][] dp = new int[M.length][M[0].length][4];
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                if (M[i][j] == 1) {
                    for (int k = 0; k < 4; k++) {
                        dp[i][j][k] = 1;
                    }
                    // Horizontal line
                    if (j > 0 && M[i][j - 1] == 1) {
                        dp[i][j][0] += dp[i][j - 1][0];
                    }
                    // Vertical line
                    if (i > 0 && M[i - 1][j] == 1) {
                        dp[i][j][1] += dp[i - 1][j][1];
                    }
                    // Diagonal line
                    if (i > 0 && j > 0 && M[i - 1][j - 1] == 1) {
                        dp[i][j][2] += dp[i - 1][j - 1][2];
                    }
                    // Anti-diagonal line
                    if (i > 0 && j < M[0].length - 1 && M[i - 1][j + 1] == 1) {
                        dp[i][j][3] += dp[i - 1][j + 1][3];
                    }
                    result = Math.max(result, Math.max(dp[i][j][0], dp[i][j][1]));
                    result = Math.max(result, Math.max(dp[i][j][2], dp[i][j][3]));
                }
            }
        }
        return result;
    }
}
