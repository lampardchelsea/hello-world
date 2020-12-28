/**
Refer to
https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/
Given a m * n matrix grid which is sorted in non-increasing order both row-wise and column-wise. 

Return the number of negative numbers in grid.

Example 1:
Input: grid = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
Output: 8
Explanation: There are 8 negatives number in the matrix.

Example 2:
Input: grid = [[3,2],[1,0]]
Output: 0

Example 3:
Input: grid = [[1,-1],[-1,-1]]
Output: 3

Example 4:
Input: grid = [[-1]]
Output: 1

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 100
-100 <= grid[i][j] <= 100
*/

// Solution 1: Binary sort since matrix has been sorted in non-increasing order
// Refer to
// https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/discuss/510249/JavaPython-3-2-similar-O(m-%2B-n)-codes-w-brief-explanation-and-analysis.
/**
Please refer to the perspicacious elaboration from @ikeabord as follows:
This solution uses the fact that the negative regions of the matrix will form a "staircase" shape, e.g.:

++++++
++++--
++++--
+++---
+-----
+-----
What this solution then does is to "trace" the outline of the staircase.

Start from bottom-left corner of the matrix, count in the negative numbers in each row.

    public int countNegatives(int[][] grid) {
        int m = grid.length, n = grid[0].length, r = m - 1, c = 0, cnt = 0;
        while (r >= 0 && c < n) {
            if (grid[r][c] < 0) {
                --r;
                cnt += n - c; // there are n - c negative numbers in current row.
            }else {
                ++c;
            }
        }
        return cnt;
    }

Simlarly, you can also start from top-right corner, whichever you feel comfortable with, count in the negative numers in each column.

    public int countNegatives(int[][] grid) {
        int m = grid.length, n = grid[0].length, r = 0, c = n - 1, cnt = 0;
        while (r < m && c >= 0) {
            if (grid[r][c] < 0) {
                --c;
                cnt += m - r; // there are m - r negative numbers in current column.
            }else {
                ++r;
            }
        }
        return cnt;
    }

Analysis:

At most move m + n steps.

Time: O(m + n), space: O(1).

For more practice of similar problem, please refer to:
240. Search a 2D Matrix II , -- credit to @cerb668.
1428. Leftmost Column with at Least a One
*/
class Solution {
    public int countNegatives(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;
        // Start from bottom left corner
        int r = m - 1;
        int c = 0;
        // Binary sort since matrix has been sorted in non-increasing order
        while(r >= 0 && c < n) {
            if(grid[r][c] < 0) {
                count += n - c;
                r--;
            } else {
                c++;
            }
        }
        return count;
    }
}
