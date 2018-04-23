/**
 * Refer to
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 
   4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

    Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

    Example 1:
    [[0,0,1,0,0,0,0,1,0,0,0,0,0],
     [0,0,0,0,0,0,0,1,1,1,0,0,0],
     [0,1,1,0,1,0,0,0,0,0,0,0,0],
     [0,1,0,0,1,1,0,0,1,0,1,0,0],
     [0,1,0,0,1,1,0,0,1,1,1,0,0],
     [0,0,0,0,0,0,0,0,0,0,1,0,0],
     [0,0,0,0,0,0,0,1,1,1,0,0,0],
     [0,0,0,0,0,0,0,1,1,0,0,0,0]]
    Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
    Example 2:
    [[0,0,0,0,0,0,0,0]]
    Given the above grid, return 0.
    Note: The length of each dimension in the given grid does not exceed 50.
 *
 * Soltion
 * https://leetcode.com/problems/max-area-of-island/discuss/108533/JavaC++-Straightforward-dfs-solution
*/
class Solution {
    int[] dx = {0,1,-1,0};
    int[] dy = {1,0,0,-1};
    public int maxAreaOfIsland(int[][] grid) {
        int result = 0;
        if(grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return result;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == 1 && !visited[i][j]) {
                    result = Math.max(result, dfs(i, j, grid, visited));
                }
            }
        }
        return result;
    }
    
    private int dfs(int i, int j, int[][] grid, boolean[][] visited) {
        if(i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j] == 1 && !visited[i][j]) {
            visited[i][j] = true;
            return 1 + dfs(i + 1, j, grid, visited) + dfs(i, j + 1, grid, visited) + dfs(i - 1, j, grid, visited) + dfs(i, j - 1, grid, visited);
        }
        return 0;
    }
}
