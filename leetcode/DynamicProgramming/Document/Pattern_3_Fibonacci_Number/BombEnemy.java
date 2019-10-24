/**
 Refer to
 http://leetcode.liangjiateng.cn/leetcode/bomb-enemy/description
 Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty '0' (the number zero), 
 return the maximum enemies you can kill using one bomb.
 The bomb kills all the enemies in the same row and column from the planted point until it hits 
 the wall since the wall is too strong to be destroyed.
 Note that you can only put the bomb at an empty cell.
 
 Example:
 For the given grid
 0 E 0 0
 E 0 W E
 0 E 0 0
 return 3. (Placing a bomb at (1,1) kills 3 enemies)
*/

// Solution 1: Native DFS
// Refer to
// https://massivealgorithms.blogspot.com/2016/06/leetcode-361-bomb-enemy.html
class Solution {
    public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '0') {
                    result = Math.max(result, helper(grid, i, j));
                }
            }
        }
        return result;
    }

    int[] dx = new int[] {0, 0, 1, -1};
    int[] dy = new int[] {1, -1, 0, 0};
    private int helper(char[][] grid, int i, int j) {
        int result = 0;
        for (int k = 0; k < 4; k++) {
            int new_i = i + dx[k];
            int new_j = j + dy[k];
            while (new_i >= 0 && new_i < grid.length &&
                new_j >= 0 && new_j < grid[0].length &&
                grid[new_i][new_j] != 'W') {
                if (grid[new_i][new_j] == 'E') {
                    result++;
                }
                new_i += dx[k];
                new_j += dy[k];
            }
        }
        return result;
    }
}

// Solution 2: DP
// Refer to
// 
