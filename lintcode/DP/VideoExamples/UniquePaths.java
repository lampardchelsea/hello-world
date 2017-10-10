/**
 * Refer to
 * http://www.lintcode.com/en/problem/unique-paths/
 * https://leetcode.com/problems/unique-paths/description/
 * 
 * Solution
 * https://www.jiuzhang.com/solution/unique-paths/
*/
public class Solution {
    /*
     * @param m: positive integer (1 <= m <= 100)
     * @param n: positive integer (1 <= n <= 100)
     * @return: An integer
     */
    public int uniquePaths(int m, int n) {
        // state: from (0,0) to (x,y)
        int[][] f = new int[m][n];
        // initialize
        // 1st column
        for(int i = 0; i < m; i++) {
            f[i][0] = 1;
        }
        // 1st row
        for(int i = 0; i < n; i++) {
            f[0][i] = 1;
        }
        // function
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                f[i][j] = f[i - 1][j] + f[i][j - 1];
            }
        }
        // answer
        return f[m - 1][n - 1];
    }
}
