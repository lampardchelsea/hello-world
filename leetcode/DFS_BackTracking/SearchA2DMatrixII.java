
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
