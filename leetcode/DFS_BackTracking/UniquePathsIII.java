/**
 Refer to
 https://leetcode.com/problems/unique-paths-iii/
 On a 2-dimensional grid, there are 4 types of squares:
1 represents the starting square.  There is exactly one starting square.
2 represents the ending square.  There is exactly one ending square.
0 represents empty squares we can walk over.
-1 represents obstacles that we cannot walk over.
Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once.

Example 1:
Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
Output: 2
Explanation: We have the following two paths: 
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)

Example 2:
Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
Output: 4
Explanation: We have the following four paths: 
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)

Example 3:
Input: [[0,1],[2,0]]
Output: 0
Explanation: 
There is no path that walks over every empty square exactly once.
Note that the starting and ending square can be anywhere in the grid.
 
Note:
1 <= grid.length * grid[0].length <= 20
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/unique-paths-iii/discuss/221946/JavaPython-Brute-Force-Backstracking
// https://leetcode.com/problems/unique-paths-iii/discuss/221946/JavaPython-Brute-Force-Backstracking/698837
/**
DFS + Backtracking:
Use Backtracking for find all possibilities with DFS
1. DFS: It needs to iterate over all 4 possible directions;
Backtracking: After iterating over 4 possible direction of current cell, grid[x][y] needs 
to be reset to 0 or 1, and empty needs to increase by 1, i.e. reset to previous value of current cell.
2. Initially int empty = 1;, it represents the starting point. Because during backtracking, 
both grid[x][y] == 0 && grid[x][y] == 1 will be set to -2 firstly and reset back to 
grid[x][y] = 0, i.e. grid[x][y] == 1 is also counted as 0 during DFS + Backtracking.
*/
class Solution {
    int result = 0;
    int empty = 1;
    public int uniquePathsIII(int[][] grid) {
        int sx = 0;
        int sy = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 0) {
                    empty++;
                } else if(grid[i][j] == 1) {
                    sx = i;
                    sy = j;
                }
            }
        }
        helper(sx, sy, grid);
        return result;
    }
    
    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    private void helper(int x, int y, int[][] grid) {
        if(x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] != -1 && grid[x][y] != -2) {
            if(grid[x][y] == 2) {
                if(empty == 0) {
                    result++;   
                }
                return;
            }
            // Mark the cell visited
            grid[x][y] = -2;
            empty--;
            for(int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                helper(new_x, new_y, grid);
            }
            // Backtracking to mark the cell un-visited
            grid[x][y] = 0;
            empty++;
        }
    }
}
