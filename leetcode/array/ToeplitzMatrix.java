/**
Refer to
https://leetcode.com/problems/toeplitz-matrix/
Given an m x n matrix, return true if the matrix is Toeplitz. Otherwise, return false.

A matrix is Toeplitz if every diagonal from top-left to bottom-right has the same elements.
Example 1:
Input: matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]
Output: true
Explanation:
In the above grid, the diagonals are:
"[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]".
In each diagonal all elements are the same, so the answer is True.

Example 2:
Input: matrix = [[1,2],[2,2]]
Output: false
Explanation:
The diagonal "[1, 2]" has different elements.

Constraints:
m == matrix.length
n == matrix[i].length
1 <= m, n <= 20
0 <= matrix[i][j] <= 99

Follow up:
What if the matrix is stored on disk, and the memory is limited such that you can only load at most one row of the matrix into the memory at once?
What if the matrix is so large that you can only load up a partial row into the memory at once?
*/

// Solution 1: Native loop check
// Refer to
// https://leetcode.com/problems/toeplitz-matrix/discuss/113417/Java-solution-4-liner.
class Solution {
    public boolean isToeplitzMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        for(int i = 0; i < m - 1; i++) {
            for(int j = 0; j < n - 1; j++) {
                if(matrix[i][j] != matrix[i + 1][j + 1]) {
                    return false;
                }
            }
        }
        return true;
    }
}

// Follow Up:
// Refer to
// https://leetcode.com/problems/toeplitz-matrix/discuss/271388/Java-Solution-for-Follow-Up-1-and-2
/**
Here are what I've thought so far, please let me know if you have any better ideas!

For the follow-up 1, we are only able to load one row at one time, so we have to use a buffer (1D data structure) to store the row info. 
When next row comes as a streaming flow, we can compare each value (say, matrix[i][j], i>=1, j>=1) to the "upper-left" value in the 
buffer (buffer[j - 1]); meanwhile, we update the buffer by inserting the 1st element of the current row (matrix[i][0]) to buffer at 
position 0 (buffer[0]), and removing the last element in the buffer.

class Solution {
    public boolean isToeplitzMatrix(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return true;
        int row = matrix.length;
        int col = matrix[0].length;
        List<Integer> buffer = new LinkedList<>();
        for(int j = 0; j < col; j++) buffer.add(matrix[0][j]);
        for(int i = 1; i < row; i++){
            for(int j = 1; j < col; j++){
                if(buffer.get(j - 1) != matrix[i][j]) return false;
            }
            buffer.remove(buffer.size() - 1);
            buffer.add(0, matrix[i][0]);
        }
        return true;
    }
}
For the follow-up 2, we can only load a partial row at one time. We could split the whole matrix vertically into several sub-matrices, 
while each sub-matrix should have one column overlapped. We repeat the same method in follow-up 1 for each sub-matrix.

For example:
[1 2 3 4 5 6 7 8 9 0]              [1 2 3 4]              [4 5 6 7]              [7 8 9 0]
[0 1 2 3 4 5 6 7 8 9]              [0 1 2 3]              [3 4 5 6]              [6 7 8 9]
[1 0 1 2 3 4 5 6 7 8]     -->      [1 0 1 2]       +      [2 3 4 5]       +      [5 6 7 8]
[2 1 0 1 2 3 4 5 6 7]              [2 1 0 1]              [1 2 3 4]              [4 5 6 7]
[3 2 1 0 1 2 3 4 5 6]              [3 2 1 0]              [0 1 2 3]              [3 4 5 6]
*/
class Solution {
    public boolean isToeplitzMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // Initial buffer by filling first row of matrix
        List<Integer> buffer = new LinkedList<Integer>();
        for(int i = 0; i < cols; i++) {
            buffer.add(matrix[0][i]);
        }
        // Read in new row (start from row 1) and compare with previous
        // row which stored in buffer, update buffer itself with new row
        // value by removing last element and insert first element from
        // new row ahead (at index 0)
        for(int i = 1; i < rows; i++) {
            // Compare all element on buffer with all element on current 
            // row, if any mismatch then not toeplitz
            for(int j = 1; j < cols; j++) {
                if(buffer.get(j - 1) != matrix[i][j]) {
                    return false;
                }
            }
            // If match then update buffer by removing last element on
            // current buffer and insert first element from current row
            // at buffer's first position
            buffer.remove(buffer.size() - 1);
            buffer.add(0, matrix[i][0]);
        }
        return true;
    }
}
