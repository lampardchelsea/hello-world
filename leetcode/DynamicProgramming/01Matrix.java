/**
 Refer to
 https://leetcode.com/problems/01-matrix/
 Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.
The distance between two adjacent cells is 1.
Example 1:
Input:
[[0,0,0],
 [0,1,0],
 [0,0,0]]
Output:
[[0,0,0],
 [0,1,0],
 [0,0,0]]
 
Example 2:
Input:
[[0,0,0],
 [0,1,0],
 [1,1,1]]
Output:
[[0,0,0],
 [0,1,0],
 [1,2,1]]
 
Note:
The number of elements of the given matrix will not exceed 10,000.
There are at least one 0 in the given matrix.
The cells are adjacent in only four directions: up, down, left and right.
*/
// Solution 2: DP
// Refer to
// https://leetcode.com/problems/01-matrix/discuss/101051/Simple-Java-solution-beat-99-(use-DP)
// https://leetcode.com/problems/01-matrix/discuss/101051/Simple-Java-solution-beat-99-(use-DP)/113437
/**
 The first iteration is from upper left corner to lower right. It's trying to update dis[i][j] to be the distance to the nearest 0 
 that is in the top left region relative to (i,j). If there's a nearer 0 to the right or to the bottom of (i,j), it won't catch that. 
 And because of the direction of the double loop, it's already in correct iterative order, meaning, you must have dealt with 
 dis[i-1][j] and dis[i][j-1] before you deal with dis[i][j]
 Then in the second loop, it goes the opposite direction from the lower right corner. So it'll find the distance to the nearest 0 
 in the bottom right region. Now combine that with the result from the first loop, it'll cover nearest 0 in all directions. 
 That is where dis[i][j] takes the min of its previous value from the first loop, and the new value (distance to the nearest 0 
 in the lower right region)
*/
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return matrix;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] result = new int[m][n];
        // Update result[i][j] to be the distance to the nearest 0 
        // that is in the top left region relative to (i,j)
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(matrix[i][j] == 0) {
                    result[i][j] = 0;
                } else {
                    int up_cell = 0;
                    if(i > 0) {
                        up_cell = result[i - 1][j];
                    } else {
                        up_cell = m + n; // maximum range for distance
                    }
                    int left_cell = 0;
                    if(j > 0) {
                        left_cell = result[i][j - 1];
                    } else {
                        left_cell = m + n;
                    }
                    result[i][j] = Math.min(up_cell, left_cell) + 1;
                }
            }
        }
        // Update result[i][j] to be the distance to the nearest 0 
        // that is in the bottom right region relative to (i,j)     
        for(int i = m - 1; i >= 0; i--) {
            for(int j = n - 1; j >= 0; j--) {
                if(matrix[i][j] == 0) {
                    result[i][j] = 0;
                } else {
                    int down_cell = 0;
                    if(i < m - 1) {
                        down_cell = result[i + 1][j];
                    } else {
                        down_cell = m + n;
                    }
                    int right_cell = 0;
                    if(j < n - 1) {
                        right_cell = result[i][j + 1];
                    } else {
                        right_cell = m + n;
                    }
                    result[i][j] = Math.min(Math.min(down_cell, right_cell) + 1, result[i][j]);
                }
            }
        }
        return result;
    }
}
