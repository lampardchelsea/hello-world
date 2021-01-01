/**
Refer to
https://leetcode.com/problems/special-positions-in-a-binary-matrix/
Given a rows x cols matrix mat, where mat[i][j] is either 0 or 1, return the number of special positions in mat.

A position (i,j) is called special if mat[i][j] == 1 and all other elements in row i and column j are 0 (rows and columns are 0-indexed).

Example 1:
Input: mat = [[1,0,0],
              [0,0,1],
              [1,0,0]]
Output: 1
Explanation: (1,2) is a special position because mat[1][2] == 1 and all other elements in row 1 and column 2 are 0.

Example 2:
Input: mat = [[1,0,0],
              [0,1,0],
              [0,0,1]]
Output: 3
Explanation: (0,0), (1,1) and (2,2) are special positions. 

Example 3:
Input: mat = [[0,0,0,1],
              [1,0,0,0],
              [0,1,1,0],
              [0,0,0,0]]
Output: 2

Example 4:
Input: mat = [[0,0,0,0,0],
              [1,0,0,0,0],
              [0,1,0,0,0],
              [0,0,1,0,0],
              [0,0,0,1,1]]
Output: 3

Constraints:
rows == mat.length
cols == mat[i].length
1 <= rows, cols <= 100
mat[i][j] is 0 or 1
*/
// Solution 1: Native ones collection based on rows and cols
// Refer to
// https://leetcode.com/problems/special-positions-in-a-binary-matrix/discuss/843949/C%2B%2B-2-passes
/**
First pass, count number of ones in rows and cols.
Second pass, go through the matrix, and for each one (mat[i][j] == 1) check it it's alone in the corresponding 
row (rows[i] == 1) and column (cols[j] == 1).
*/
class Solution {
    public int numSpecial(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;
        int[] row_has_ones = new int[rows];
        int[] col_has_ones = new int[cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(mat[i][j] == 1) {
                    row_has_ones[i]++;
                    col_has_ones[j]++;
                }
            }
        }
        int result = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(mat[i][j] == 1 && row_has_ones[i] == 1 && col_has_ones[j] == 1) {
                    result++;
                }
            }
        }
        return result;
    }
}
