// Refer to
// https://leetcode.com/problems/search-a-2d-matrix/#/description
/**
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 * For example,
 * Consider the following matrix:
  [
    [1,   3,  5,  7],
    [10, 11, 16, 20],
    [23, 30, 34, 50]
  ]
 * Given target = 3, return true.
*/

// Solution
// Refer to
// https://discuss.leetcode.com/topic/4846/binary-search-on-an-ordered-matrix
// https://discuss.leetcode.com/topic/3227/don-t-treat-it-as-a-2d-matrix-just-treat-it-as-a-sorted-list
/**
 * Use binary search.
 * n * m matrix convert to an array => matrix[x][y] => a[x * m + y]
 * an array convert to n * m matrix => a[x] =>matrix[x / m][x % m];
*/
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        if(rows == 0) {
            return false;
        }
        int columns = matrix[0].length;
        int lo = 0;
        int hi = rows * columns - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo)/2;
            int midValue = matrix[mid / columns][mid % columns];
            if(midValue == target) {
                return true;
            } else if(midValue > target) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return false;
    }
}
