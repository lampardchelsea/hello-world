/**
 Refer to
 https://leetcode.com/problems/number-of-enclaves/
 Given a 2D array A, each cell is 0 (representing sea) or 1 (representing land)

A move consists of walking from one land square 4-directionally to another land square, 
or off the boundary of the grid.

Return the number of land squares in the grid for which we cannot walk off the boundary 
of the grid in any number of moves.

Example 1:
Input: [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
Output: 3
Explanation: 
There are three 1s that are enclosed by 0s, and one 1 that isn't enclosed because its on the boundary.

Example 2:
Input: [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
Output: 0
Explanation: 
All 1s are either on the boundary or can reach the boundary.

Note:
1 <= A.length <= 500
1 <= A[i].length <= 500
0 <= A[i][j] <= 1
All rows have the same size.
*/
// Solution 1:
// Refer to
// https://leetcode.com/problems/number-of-enclaves/discuss/266168/Easy-Java-DFS-6ms-solution
// https://leetcode.com/problems/number-of-enclaves/discuss/265555/C%2B%2B-with-picture-DFS-and-BFS
// Intuition:
// We flood-fill the land (change 1 to 0) from the boundary of the grid. Then, we count the remaining land.
class Solution {
    int[] dx = new int[]{0,0,1,-1};
    int[] dy = new int[]{-1,1,0,0};
    public int numEnclaves(int[][] A) {
        if(A == null || A.length == 0 || A[0] == null || A[0].length == 0) {
            return 0;
        }
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < A[0].length; j++) {
                if(i == 0 || j == 0 || i == A.length - 1 || j == A[0].length - 1) {
                    helper(A, i, j);
                } 
            }
        }
        int count = 0;
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < A[0].length; j++) {
                if(A[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private void helper(int[][] A, int i, int j) {
        if(i >= 0 && j >= 0 && i <= A.length - 1 && j <= A[0].length - 1 && A[i][j] == 1) {
            A[i][j] = 0;
            for(int k = 0; k < 4; k++) {
                helper(A, i + dx[k], j + dy[k]);
            }
        }
    }
}
