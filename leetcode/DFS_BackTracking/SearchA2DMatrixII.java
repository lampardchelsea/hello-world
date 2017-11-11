/**
 * Refer to
 * https://leetcode.com/problems/search-a-2d-matrix-ii/description/
 * Write an efficient algorithm that searches for a value in an m x n matrix. 
   This matrix has the following properties:

    Integers in each row are sorted in ascending from left to right.
    Integers in each column are sorted in ascending from top to bottom.
    For example,

    Consider the following matrix:
    [
      [1,   4,  7, 11, 15],
      [2,   5,  8, 12, 19],
      [3,   6,  9, 16, 22],
      [10, 13, 14, 17, 24],
      [18, 21, 23, 26, 30]
    ]
    Given target = 5, return true.
    Given target = 20, return false.
 *
 * 
 * Solution
 * https://discuss.leetcode.com/topic/33240/java-an-easy-to-understand-divide-and-conquer-method?page=1
 * The coding seems to be much more complex than those smart methods such as this one, but the idea behind 
   is actually quite straightforward. Unfortunately, it is not as fast as the smart ones.

    First, we divide the matrix into four quarters as shown below:

      zone 1      zone 2
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    -----------------------
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
      zone 3      zone 4
    We then compare the element in the center of the matrix with the target. There are three possibilities:
    center < target. In this case, we discard zone 1 because all elements in zone 1 are less than target.
    center > target. In this case, we discard zone 4.
    center == target. return true.

    For time complexity, if the matrix is a square matrix of size nxn, then for the worst case,
    T(nxn) = 3T(n/2 x n/2)
    which makes
    T(nxn) = O(n^log3)
 * 
 * https://discuss.leetcode.com/topic/33240/java-an-easy-to-understand-divide-and-conquer-method/3?page=1
*/
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        if(matrix.length == 1 && matrix[0].length == 1) {
            return matrix[0][0] == target;
        }
        return helper(matrix, target, 0, matrix.length - 1, 0, matrix[0].length - 1);
    }
    
    private boolean helper(int[][] matrix, int target, int rowStart, int rowEnd, int colStart, int colEnd) {
        // If not including these 2 conditions will cause StackOverFlow issue
        // E.g
        // Runtime Error Message:
        // Line 26: java.lang.StackOverflowError
        // Last executed input:
        // [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]] and target = 20
        if(rowStart < 0 || rowStart >= matrix.length || colStart < 0 || colStart >= matrix[0].length 
           || rowStart > rowEnd || colStart > colEnd) {
            return false;
        }
        int rowMid = rowStart + (rowEnd - rowStart) / 2;
        int colMid = colStart + (colEnd - colStart) / 2;
        if(matrix[rowMid][colMid] == target) {
            return true;
        // If center value > target, get rid of the lower-right corner of matrix, and continue
        // compute other 3 parts
        } else if(matrix[rowMid][colMid] > target) {
            return helper(matrix, target, rowStart, rowMid - 1, colStart, colMid - 1) ||
                   helper(matrix, target, rowStart, rowMid - 1, colMid, colEnd) ||
                   helper(matrix, target, rowMid, rowEnd, colStart, colMid - 1);
        // If center value < target, get rid of the upper-left corner of matrix, and continue
        // compute other 3 parts
        } else {
            return helper(matrix, target, rowMid + 1, rowEnd, colMid + 1, colEnd) ||
                   helper(matrix, target, rowMid + 1, rowEnd, colStart, colMid) ||
                   helper(matrix, target, rowStart, rowMid, colMid + 1, colEnd);
        }
    }
}
