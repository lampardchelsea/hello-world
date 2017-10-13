/** 
 * Refer to
 * https://leetcode.com/problems/maximal-square/description/
 * Given a 2D binary matrix filled with 0's and 1's, find the largest square 
   containing only 1's and return its area.

  For example, given the following matrix:

  1 0 1 0 0
  1 0 1 1 1
  1 1 1 1 1
  1 0 0 1 0
  Return 4.
 *
 * Solution
 * https://leetcode.com/articles/maximal-square/
*/

// Solution 1: Brute Force
/**
 Approach #1 Brute Force [Accepted]
 The simplest approach consists of trying to find out every possible square of 1’s that 
 can be formed from within the matrix. The question now is – how to go for it?
 We use a variable to contain the size of the largest square found so far and another 
 variable to store the size of the current, both initialized to 0. Starting from the left 
 uppermost point in the matrix, we search for a 1. No operation needs to be done for a 0. 
 Whenever a 1 is found, we try to find out the largest square that can be formed including 
 that 1. For this, we move diagonally (right and downwards), i.e. we increment the row 
 index and column index temporarily and then check whether all the elements of that row 
 and column are 1 or not. If all the elements happen to be 1, we move diagonally further 
 as previously. If even one element turns out to be 0, we stop this diagonal movement and 
 update the size of the largest square. Now we, continue the traversal of the matrix from 
 the element next to the initial 1 found, till all the elements of the matrix have been traversed.
 Complexity Analysis

Time complexity : O((mn)^2) In worst case, we need to traverse the complete matrix for every 1.
Space complexity : O(1). No extra space is used.
*/
class Solution {
    public int maximalSquare(char[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int max_sqlen = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                // Ignore all position as 0
                if(matrix[i][j] == '1') {
                    boolean flag = true;
                    int diagonal_len = 1;
                    // we increment the row index and column index temporarily and 
                    // then check whether all the elements of that row and column 
                    // are 1 or not. If all the elements happen to be 1, we move 
                    // diagonally further as previously. If even one element turns 
                    // out to be 0, we stop this diagonal movement and update the 
                    // size of the largest square.
                    while(flag && i + diagonal_len < m && j + diagonal_len < n) {
                        // Check if between column (index = j + diagonal_len) range
                        // [i, i + diagonal_len] contains '0', if contains not
                        // able to make square, break out
                        for(int k = i; k <= i + diagonal_len; k++) {
                            if(matrix[k][j + diagonal_len] == '0') {
                                flag = false;
                                break;
                            }
                        }
                        // Check if between row (index = j + diagonal_len) range
                        // [j, j + diagonal_len] contains '0', if contains not
                        // able to make square, break out
                        for(int k = j; k <= j + diagonal_len; k++) {
                            if(matrix[i + diagonal_len][k] == '0') {
                                flag = false;
                                break;
                            }
                        }
                        // If not contains '0' in above square, extend diagonal length
                        if(flag) {
                            diagonal_len++;
                        }
                    }
                    if(max_sqlen < diagonal_len) {
                        max_sqlen = diagonal_len;
                    }
                }
            }
        }
        return max_sqlen * max_sqlen;
    }
}

// Solution 2: DP
class Solution {
    /**
     * Refer to
     * http://www.cnblogs.com/grandyang/p/4550604.html
     * 我们还可以进一步的优化时间复杂度到O(n2)，做法是使用DP，简历一个二维dp数组，
       其中dp[i][j]表示到达(i, j)位置所能组成的最大正方形的边长。我们首先来考虑边界情况，
       也就是当i或j为0的情况，那么在首行或者首列中，必定有一个方向长度为1，那么就无法组成
       长度超过1的正方形，最多能组成长度为1的正方形，条件是当前位置为1。边界条件处理完了，
       再来看一般情况的递推公式怎么办，对于任意一点dp[i][j]，由于该点是正方形的右下角，
       所以该点的右边，下边，右下边都不用考虑，关心的就是左边，上边，和左上边。这三个位置
       的dp值suppose都应该算好的，还有就是要知道一点，只有当前(i, j)位置为1，dp[i][j]才
       有可能大于0，否则dp[i][j]一定为0。当(i, j)位置为1，此时要看dp[i-1][j-1], 
       dp[i][j-1]，和dp[i-1][j]这三个位置，我们找其中最小的值，并加上1，就是dp[i][j]
       的当前值了，这个并不难想，毕竟不能有0存在，所以只能取交集，最后再用dp[i][j]的值
       来更新结果res的值即可
     * 
     * https://leetcode.com/articles/maximal-square/
       We initialize another matrix (dp) with the same dimensions as the original 
       one initialized with all 0’s.
       dp(i,j) represents the side length of the maximum square whose bottom right 
       corner is the cell with index (i,j) in the original matrix.
       Starting from index (0,0), for every 1 found in the original matrix, we update 
       the value of the current element as
       dp(i,j) = min(dp(i−1, j), dp(i−1, j−1), dp(i, j−1))+1.
       We also remember the size of the largest square found so far. In this way, 
       we traverse the original matrix once and find out the required maximum size. 
       This gives the side length of the square (say maxsqlenmaxsqlen). 
       The required result is the area maxsqlen^2
       
       Complexity Analysis
       Time complexity : O(mn). Single pass.
       Space complexity : O(mn). Another matrix of same size is used for dp.
    */
    public int maximalSquare(char[][] matrix) {
        if(matrix == null || matrix.length == 0) {
            return 0;
        }
        // State
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int result = 0;
        // intialize and function
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(i == 0 || j == 0) {
                    dp[i][j] = matrix[i][j] - '0';
                } else if(matrix[i][j] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i - 1][j]), dp[i][j - 1]) + 1;
                }
                result = Math.max(result, dp[i][j]);
            }   
        }
        // answer
        return result * result;
    }
}


