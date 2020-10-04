/**
 Refer to
 https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/
 Given a m x n grid. Each cell of the grid has a sign pointing to the next cell you should visit 
 if you are currently in this cell. The sign of grid[i][j] can be:
1 which means go to the cell to the right. (i.e go from grid[i][j] to grid[i][j + 1])
2 which means go to the cell to the left. (i.e go from grid[i][j] to grid[i][j - 1])
3 which means go to the lower cell. (i.e go from grid[i][j] to grid[i + 1][j])
4 which means go to the upper cell. (i.e go from grid[i][j] to grid[i - 1][j])
Notice that there could be some invalid signs on the cells of the grid which points outside the grid.

You will initially start at the upper left cell (0,0). A valid path in the grid is a path which 
starts from the upper left cell (0,0) and ends at the bottom-right cell (m - 1, n - 1) following 
the signs on the grid. The valid path doesn't have to be the shortest.

You can modify the sign on a cell with cost = 1. You can modify the sign on a cell one time only.

Return the minimum cost to make the grid have at least one valid path.

Example 1:
Input: grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
Output: 3
Explanation: You will start at point (0, 0).
The path to (3, 3) is as follows. (0, 0) --> (0, 1) --> (0, 2) --> (0, 3) change the arrow to down with 
cost = 1 --> (1, 3) --> (1, 2) --> (1, 1) --> (1, 0) change the arrow to down with cost = 1 --> (2, 0) 
--> (2, 1) --> (2, 2) --> (2, 3) change the arrow to down with cost = 1 --> (3, 3)
The total cost = 3.

Example 2:
Input: grid = [[1,1,3],[3,2,2],[1,1,4]]
Output: 0
Explanation: You can follow the path from (0, 0) to (2, 2).

Example 3:
Input: grid = [[1,2],[4,3]]
Output: 1

Example 4:
Input: grid = [[2,2,2],[2,2,2]]
Output: 3

Example 5:
Input: grid = [[4]]
Output: 0

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 100
*/

// Solution 1: BFS + DFS (Similar thought as ShortestBridge.java)
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/BFS/ShortestBridge.java
// https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/discuss/524886/JavaC%2B%2BPython-BFS-and-DFS
/**
Intuition
One observation is that, (not sure if it's obvious)
we can greedily explore the grid.
We will never detour the path to a node that we can already reach.

In the view of graph,
the fleche indicates a directed edge of weight = 0.
The distance between all neighbours are at most 1.
Now we want to find out the minimum distance between top-left and bottom-right.

Explanation
1.Find out all reachable nodes without changing anything.
2.Save all new visited nodes to a queue bfs.
3.Now iterate the queue
  3.1 For each node, try changing it to all 3 other direction
  3.2 Save the new reachable and not visited nodes to the queue.
  3.3 repeat step 3

Complexity
Time O(NM)
Space O(NM)
*/
class Solution {
    int INF = (int) 1e9;
    // 1 -> go right, 2 -> go left, 3 -> go lower, 4 -> go upper
    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++) {
            Arrays.fill(dp[i], INF);
        }
        Queue<int[]> q = new LinkedList<int[]>();
        int cost = 0;
        // Use DFS to find out all reachable nodes without changing anything,
        // including following the given fleche direction to move to next node
        helper(grid, 0, 0, dp, cost, q);
        // The distance between all neighbours are at most 1, use level order
        // traverse to find cost for each reachable node
        while(!q.isEmpty()) {
            cost++;
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int[] cur = q.poll();
                int x = cur[0];
                int y = cur[1];
                // Not follow fleche since we can change direction for each node
                // for 1 time now, try all 4 directions for next move
                for(int j = 0; j < 4; j++) {
                    helper(grid, x + dx[j], y + dy[j], dp, cost, q);
                }
            }
        }
        return dp[m - 1][n - 1];
    }
    
    private void helper(int[][] grid, int x, int y, int[][] dp, int cost, Queue<int[]> q) {
        if(x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && dp[x][y] == INF) {
            dp[x][y] = cost;
            // Save all new visited nodes to a queue
            q.offer(new int[] {x, y});
            // Find next direction index mapping with fleche directions
            int nextDirIndex = grid[x][y] - 1;
            int new_x = x + dx[nextDirIndex];
            int new_y = y + dy[nextDirIndex];
            helper(grid, new_x, new_y, dp, cost, q);
        }
    }
}
