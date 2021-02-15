/**
Refer to
https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/
Given a m x n matrix mat and an integer threshold. Return the maximum side-length of a square with a sum 
less than or equal to threshold or return 0 if there is no such square.

Example 1:
Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
Output: 2
Explanation: The maximum side length of square with sum less than 4 is 2 as shown.

Example 2:
Input: mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]], threshold = 1
Output: 0

Example 3:
Input: mat = [[1,1,1,1],[1,0,0,0],[1,0,0,0],[1,0,0,0]], threshold = 6
Output: 3

Example 4:
Input: mat = [[18,70],[61,1],[25,85],[14,40],[11,96],[97,96],[63,45]], threshold = 40184
Output: 2

Constraints:
1 <= m, n <= 300
m == mat.length
n == mat[i].length
0 <= mat[i][j] <= 10000
0 <= threshold <= 10^5
*/

// Solution 1: 2D preSum + BinarySearch
// Refer to
// https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/discuss/451871/Java-sum%2Bbinary-O(m*n*log(min(mn)))-or-sum%2Bsliding-window-O(m*n)
// https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/discuss/698422/Python-Prefix-Sum-(Explanation-with-Diagram)
/**
The main idea here is to form the largest possible square whose sum <= threshold.
-----------------------------------------------------------------------
1st Pass - O(MN) : Create a prefix sum matrix. Note how we have zero-padding on the top row and left column after the conversion
					           0 0 0 0 
1 1 1                          0 1 2 3 
1 0 0            --->          0 2 3 4 
1 0 0                          0 3 4 5 

2nd Pass - O(MN) : Update maximum length, starting from 0.
-----------------------------------------------------------------------
At each cell, we shall consider the following sums.
If you want to find Bottom-Right Rectangle (Red) -> 5,6,8,9 
==> we can use below 3 rectangles to compute:
Top Rectangle (Blue) -> 1,2,3
Left Rectangle (Green) -> 1,4,7
Top-Left Rectangle (Blue-Green Overlap) -> 1
-----------------------------------------------------------------------
      c1  c2
   1   2   3     sumRange(r1,c1,r2,c2)
                 = sum[r2][c2]
r1 4   5   6      - sum[r2][c1 - 1]
                  - sum[r1 - 1][c2]
r2 7   8   9      + sum[r1 - 1][c1 - 1]
-----------------------------------------------------------------------
The areas of the 3 rectangles are each given by a single position in the prefix-sum matrix (see code below)
*/

// How to create 2D preSum ?
/**
mat:
[[1,1,3,2,4,3,2],
 [1,1,3,2,4,3,2],
 [1,1,3,2,4,3,2]]

sum:
[[0, 0, 0, 0, 0, 0, 0, 0], 
 [0, 1, 2, 5, 7, 11, 14, 16], 
 [0, 2, 4, 10, 14, 22, 28, 32], 
 [0, 3, 6, 15, 21, 33, 42, 48]]
 
E.g
sum[i][j] = sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1] + mat[i-1][j-1];
sum[1][1] = sum[0][1] + sum[1][0] - sum[0][0] + mat[0][0];
          = 0 + 0 - 0 + 1 = 1
*/
class Solution {
    int m;
    int n;
    public int maxSideLength(int[][] mat, int threshold) {
        m = mat.length;
        n = mat[0].length;
        int[][] sum = new int[m + 1][n + 1];
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + mat[i - 1][j - 1];
            }
        }
        int lo = 0;
        int hi = Math.min(m, n);
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(isSquareExist(sum, mid, threshold)) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return hi;
    }
    
    private boolean isSquareExist(int[][] sum, int len, int threshold) {
        for(int i = len; i <= m; i++) {
            for(int j = len; j <= n; j++) {
                if(sum[i][j] - sum[i - len][j] - sum[i][j - len] + sum[i - len][j - len] <= threshold) {
                    return true;
                }
            }
        }
        return false;
    }
}
