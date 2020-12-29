/**
Refer to
https://leetcode.com/problems/lucky-numbers-in-a-matrix/
Given a m * n matrix of distinct numbers, return all lucky numbers in the matrix in any order.

A lucky number is an element of the matrix such that it is the minimum element in its row and maximum in its column.

Example 1:
Input: matrix = [[3,7,8],[9,11,13],[15,16,17]]
Output: [15]
Explanation: 15 is the only lucky number since it is the minimum in its row and the maximum in its column

Example 2:
Input: matrix = [[1,10,4,2],[9,3,8,7],[15,16,17,12]]
Output: [12]
Explanation: 12 is the only lucky number since it is the minimum in its row and the maximum in its column.

Example 3:
Input: matrix = [[7,8],[1,2]]
Output: [7]

Constraints:
m == mat.length
n == mat[i].length
1 <= n, m <= 50
1 <= matrix[i][j] <= 10^5.
All elements in the matrix are distinct.
*/

// Solution 1: O(m * n)
// Find out and save the minimum of each row and maximum of each column in two lists, 
// Then scan through the whole matrix to identify the elements that satisfy the criteria.
class Solution {
    public List<Integer> luckyNumbers (int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] row_min = new int[rows];
        int[] col_max = new int[cols];
        for(int i = 0; i < rows; i++) {
            int cur_row_min = Integer.MAX_VALUE;
            for(int j = 0; j < cols; j++) {
                cur_row_min = Math.min(cur_row_min, matrix[i][j]);
            }
            row_min[i] = cur_row_min;
        }
        for(int i = 0; i < cols; i++) {
            int cur_col_max = Integer.MIN_VALUE;
            for(int j = 0; j < rows; j++) {
                cur_col_max = Math.max(cur_col_max, matrix[j][i]);
            }
            col_max[i] = cur_col_max;
        }
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(matrix[i][j] == row_min[i] && matrix[i][j] == col_max[j]) {
                    result.add(matrix[i][j]);
                }
            }
        }
        return result;
    }
}
