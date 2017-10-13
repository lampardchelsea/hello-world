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
