/**
 Refer to
 https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/
 Given a m * n grid, where each cell is either 0 (empty) or 1 (obstacle). In one step, you can move up, down, 
 left or right from and to an empty cell.
 
 Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m-1, n-1) 
 given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.

Example 1:
Input: 
grid = 
[[0,0,0],
 [1,1,0],
 [0,0,0],
 [0,1,1],
 [0,0,0]], 
k = 1
Output: 6
Explanation: 
The shortest path without eliminating any obstacle is 10. 
The shortest path with one obstacle elimination at position (3,2) is 6. Such path is (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).

Example 2:
Input: 
grid = 
[[0,1,1],
 [1,1,1],
 [1,0,0]], 
k = 1
Output: -1
Explanation: 
We need to eliminate at least two obstacles to find such a walk.

Constraints:
grid.length == m
grid[0].length == n
1 <= m, n <= 40
1 <= k <= m*n
grid[i][j] == 0 or 1
grid[0][0] == grid[m-1][n-1] == 0
*/

// Solution 1: BFS + 2D array to track eliminate chances for all possible path but keep updating value for minimum obstacles(most eliminate chance remains) encounter path
// Refer to
// https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/discuss/452184/Clean-Java-BFS-with-comments.
class Solution {
    public int shortestPath(int[][] grid, int k) {
        int[] dx = new int[] {0,0,1,-1};
        int[] dy = new int[] {1,-1,0,0};
        int m = grid.length;
        int n = grid[0].length;
        int[][] eliminateChanceRemains = new int[m][n];
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> q = new LinkedList<int[]>();
        // Initially have k chances to eliminate obstacles
        eliminateChanceRemains[0][0] = k;
        q.offer(new int[] {0, 0, eliminateChanceRemains[0][0]});
        visited[0][0] = true;
        int distance = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int[] cur = q.poll();
                int x = cur[0];
                int y = cur[1];
                int currPathRemain = cur[2];
                if(x == m - 1 && y == n - 1) {
                    return distance;
                }
                for(int j = 0; j < 4; j++) {
                    int new_x = x + dx[j];
                    int new_y = y + dy[j];
                    // Two conditions able to add into queue for next move:
                    // 1. Not visited before
                    // 2. Visited before on other path but encounter more obstacles 
                    //    than current path, which means the remains chances less 
                    //    than current path, if that happened, we will replace 
                    //    previous path with current path, also update the 2D array 
                    //    used to record
                    if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n && (!visited[new_x][new_y] || eliminateChanceRemains[new_x][new_y] < currPathRemain)) {
                        if(grid[new_x][new_y] == 0) {
                            // Since current cell value is 0 not consuming chance
                            eliminateChanceRemains[new_x][new_y] = currPathRemain;
                            q.offer(new int[] {new_x, new_y, currPathRemain});
                        } else if(grid[new_x][new_y] == 1 && currPathRemain > 0) {
                            // Since current cell value is 1 consuming 1 chance
                            eliminateChanceRemains[new_x][new_y] = currPathRemain - 1;
                            q.offer(new int[] {new_x, new_y, currPathRemain - 1});
                        }
                        visited[new_x][new_y] = true;
                    }
                }
            }
            distance++;
        }
        return -1;
    }
}
