/**
Refer to
https://leetcode.com/problems/minimum-falling-path-sum/
Given an n x n array of integers matrix, return the minimum sum of any falling path through matrix.

A falling path starts at any element in the first row and chooses the element in the next row that is either directly 
below or diagonally left/right. Specifically, the next element from position (row, col) will be (row + 1, col - 1), 
(row + 1, col), or (row + 1, col + 1).

Example 1:
Input: matrix = [[2,1,3],[6,5,4],[7,8,9]]
Output: 13
Explanation: There are two falling paths with a minimum sum underlined below:
[[2,1,3],      [[2,1,3],
 [6,5,4],       [6,5,4],
 [7,8,9]]       [7,8,9]]

Example 2:
Input: matrix = [[-19,57],[-40,-5]]
Output: -59
Explanation: The falling path with a minimum sum is underlined below:
[[-19,57],
 [-40,-5]]

Example 3:
Input: matrix = [[-48]]
Output: -48

Constraints:
n == matrix.length
n == matrix[i].length
1 <= n <= 100
-100 <= matrix[i][j] <= 100
*/

// Wrong solution
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        int c = matrix[0].length;
        for(int i = 0; i < c; i++) {
            min = Math.min(min, helper(matrix, i));
        }
        return min;
    }
    
    private int helper(int[][] matrix, int index) {
        int[] dx = new int[] {1, 0, -1};
        int start = matrix[0][index];
        int r = matrix.length;
        int c = matrix[0].length;
        int prev_x = index;
        int next_x = -1;
        for(int i = 1; i < r; i++) {
            int tmp = 101;
            // Wrong way to track the previous x index
            // each time for loop may overwrite same row
            // previous candidate x index, not the correct
            // way to update previous x, need standard DFS
            // to resolve
            for(int j = 0; j < 3; j++) {
                next_x = prev_x + dx[j];
                if(next_x >= 0 && next_x < c) {
                    tmp = Math.min(tmp, matrix[i][next_x]);
                    prev_x = next_x;
                }
            }
            start += tmp;
        }
        return start;
    }
}

// Solution 1: Native DFS (TLE)
// Refer to
// https://leetcode.com/problems/minimum-falling-path-sum/discuss/201812/Recursive-with-memoization-3ms-beats-%22100%22
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        int c = matrix[0].length;
        for(int i = 0; i < c; i++) {
            min = Math.min(min, helper(matrix, 0, i));
        }
        return min;
    }
    
    private int helper(int[][] matrix, int row, int col) {
        if(row == matrix.length - 1) {
            return matrix[row][col];
        }
        int min = Integer.MAX_VALUE;
        int val = matrix[row][col];
        // [-1, 1] direction
        if(col - 1 >= 0) {
            min = Math.min(min, helper(matrix, row + 1, col - 1) + val);
        }
        // [1, 1] direction
        if(col + 1 < matrix[0].length) {
            min = Math.min(min, helper(matrix, row + 1, col + 1) + val);
        }
        // [0, 1] direction
        min = Math.min(min, helper(matrix, row + 1, col) + val);
        return min;
    }
}

// Solution 2: Top Down DP Memoization
// Refer to
// https://leetcode.com/problems/minimum-falling-path-sum/discuss/201812/Recursive-with-memoization-3ms-beats-%22100%22

// Style 1: Initialize memo as int[][]
/**
I think the problem you mention is when a memoized sum is 0. The 2d memo array is initialized with 0's, 
and if we find any memo[row][col] sum that is actually 0, then we end up skipping it and performing the 
calculation again, since we think it hasn't been computed.

I used Integer.MIN_VALUE, but theoretically the same thing can happen. The safest implementation is 
probably to use a 2d array of Integers (Integer[][]) and check if the value is non-null.
*/
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        int r = matrix.length;
        int c = matrix[0].length;
        int[][] memo = new int[r][c];
        for(int i = 0; i < c; i++) {
            min = Math.min(min, helper(matrix, 0, i, memo));
        }
        return min;
    }
    
    private int helper(int[][] matrix, int row, int col, int[][] memo) {
        if(memo[row][col] != 0) {
            return memo[row][col];
        }
        if(row == matrix.length - 1) {
            return matrix[row][col];
        }
        int min = Integer.MAX_VALUE;
        int val = matrix[row][col];
        // [-1, 1] direction
        if(col - 1 >= 0) {
            min = Math.min(min, helper(matrix, row + 1, col - 1, memo) + val);
        }
        // [1, 1] direction
        if(col + 1 < matrix[0].length) {
            min = Math.min(min, helper(matrix, row + 1, col + 1, memo) + val);
        }
        // [0, 1] direction
        memo[row][col] = Math.min(min, helper(matrix, row + 1, col, memo) + val);
        return memo[row][col];
    }
}

// Style 2: Initialize memo as Integer[][]
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        int r = matrix.length;
        int c = matrix[0].length;
        Integer[][] memo = new Integer[r][c];
        for(int i = 0; i < c; i++) {
            min = Math.min(min, helper(matrix, 0, i, memo));
        }
        return min;
    }
    
    private int helper(int[][] matrix, int row, int col, Integer[][] memo) {
        if(memo[row][col] != null) {
            return memo[row][col];
        }
        if(row == matrix.length - 1) {
            return matrix[row][col];
        }
        int min = Integer.MAX_VALUE;
        int val = matrix[row][col];
        // [-1, 1] direction
        if(col - 1 >= 0) {
            min = Math.min(min, helper(matrix, row + 1, col - 1, memo) + val);
        }
        // [1, 1] direction
        if(col + 1 < matrix[0].length) {
            min = Math.min(min, helper(matrix, row + 1, col + 1, memo) + val);
        }
        // [0, 1] direction
        memo[row][col] = Math.min(min, helper(matrix, row + 1, col, memo) + val);
        return memo[row][col];
    }
}

// Solution 3: Bottom Up DP
// Refer to
// https://leetcode.com/problems/minimum-falling-path-sum/discuss/776004/Easy-Java-Solution-using-DP
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        int[][] dp = new int[r][c];
        for(int i = 0; i < c; i++) {
            dp[0][i] = matrix[0][i];
        }
        for(int i = 1; i < r; i++) {
            for(int j = 0; j < c; j++) {
                if(j == 0) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j + 1]) + matrix[i][j];
                } else if(j == c - 1) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + matrix[i][j];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i - 1][j + 1], dp[i - 1][j - 1])) + matrix[i][j];
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < c; i++) {
            min = Math.min(min, dp[r - 1][i]);
        }
        return min;
    }
}
