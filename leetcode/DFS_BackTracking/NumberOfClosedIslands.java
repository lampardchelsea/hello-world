/**
 Refer to
 https://leetcode.com/problems/number-of-closed-islands/
 Given a 2D grid consists of 0s (land) and 1s (water).  An island is a maximal 4-directionally connected group of 0s and 
 a closed island is an island totally (all left, top, right, bottom) surrounded by 1s.

Return the number of closed islands.
Example 1:
Input: grid = [[1,1,1,1,1,1,1,0],[1,0,0,0,0,1,1,0],[1,0,1,0,1,1,1,0],[1,0,0,0,0,1,0,1],[1,1,1,1,1,1,1,0]]
Output: 2
Explanation: 
Islands in gray are closed because they are completely surrounded by water (group of 1s).

Example 2:
Input: grid = [[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]]
Output: 1

Example 3:
Input: grid = [[1,1,1,1,1,1,1],
               [1,0,0,0,0,0,1],
               [1,0,1,1,1,0,1],
               [1,0,1,0,1,0,1],
               [1,0,1,1,1,0,1],
               [1,0,0,0,0,0,1],
               [1,1,1,1,1,1,1]]
Output: 2

Constraints:
1 <= grid.length, grid[0].length <= 100
0 <= grid[i][j] <=1
*/

// Solution 1: Same as 1020. Number of Enclaves
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/NumberOfEnclaves.java
// https://leetcode.com/problems/number-of-closed-islands/discuss/425150/JavaC%2B%2B-with-picture-Number-of-Enclaves
// https://leetcode.com/problems/number-of-closed-islands/discuss/425150/JavaC++-with-picture-Number-of-Enclaves/383306
// Complexity Analysis
// Time: O(n), where n is the total number of cells. We flood-fill all land cells once.
// Memory: O(n) for the stack. Flood fill is DFS, and the maximum depth is n.
class Solution {
    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    public int closedIsland(int[][] grid) {
        // First, we need to remove all land connected to the edges using flood fill.
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(i == 0 || i == grid.length - 1 || j == 0 || j == grid[0].length - 1) {
                    helper(i, j, grid);
                }
            }
        }
        // Then, we can count and flood-fill the remaining islands.
        int result = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 0) {
                    result++;
                    helper(i, j, grid);
                }
            }
        }
        return result;
    }
    
    private void helper(int x, int y, int[][] grid) {
        // A little different than 1020. Number of Enclaves, grid[x][y] == 0 instead
        // of grid[x][y] == 1, since 0 means land not 1 here
        if(x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 0) {
            grid[x][y] = 1;
            for(int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                helper(new_x, new_y, grid);
            }
        }
    }
}

// Solution 2:
