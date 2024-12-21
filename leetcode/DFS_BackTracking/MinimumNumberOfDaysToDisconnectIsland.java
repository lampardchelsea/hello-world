https://leetcode.com/problems/minimum-number-of-days-to-disconnect-island/description/
You are given an m x n binary grid grid where 1 represents land and 0 represents water. An island is a maximal 4-directionally (horizontal or vertical) connected group of 1's.
The grid is said to be connected if we have exactly one island, otherwise is said disconnected.
In one day, we are allowed to change any single land cell (1) into a water cell (0).
Return the minimum number of days to disconnect the grid.

Example 1:

Input: grid = [[0,1,1,0],[0,1,1,0],[0,0,0,0]]
Output: 2
Explanation: We need at least 2 days to get a disconnected grid.Change land grid[1][1] and grid[0][2] to water and get 2 disconnected island.

Example 2:

Input: grid = [[1,1]]
Output: 2
Explanation: Grid of full water is also disconnected ([[1,1]] -> [[0,0]]), 0 islands.
 
Constraints:
- m == grid.length
- n == grid[i].length
- 1 <= m, n <= 30
- grid[i][j] is either 0 or 1.
--------------------------------------------------------------------------------
Attempt 1: 2024-12-16
Solution 1: DFS + Math (60 min)
本题在初始计算有多少个 island 的 DFS 过程中，和 L200.Number of Islands 的区别在于，本题需要考虑后续的计算仍需要使用原始 grid 的问题，比如计算完有多少个 island 以后再尝试把某个 grid 中的 cell 的数值从 1 改成 0 (陆地变水)，所以不要像 L200 中为了省略 visited array 而就地改数值，原则上不应该改动原始 grid 中的任何值，所以本题在计算有多少个 island 的 DFS 过程中使用 visited array
class Solution {
    public int minDays(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int numOfIslands = countIslands(grid, m, n);
        // If no island or more than one island initially, no days required
        if(numOfIslands == 0 || numOfIslands > 1) {
            return 0;
        }
        // Try to change any one land cell to water(brute force way),
        // then count the number of islands again, need to restore
        // this modified cell back to land for further attempts
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 1) {
                    grid[i][j] = 0;
                    int numOfIslandsAfterDayOne = countIslands(grid, m, n);
                    // If no island or more than one island after day 1(change any land cell in day 1), directly return 1
                    if(numOfIslandsAfterDayOne == 0 || numOfIslandsAfterDayOne > 1) {
                        return 1;
                    }
                    grid[i][j] = 1;
                }
            }
        }
        // If cannot disconnect island in day 1, then at least two 
        // cells need to be removed to disconnect both islands,
        // then we need day 2, and mathematical we only need 2 days
        // to guarantee disconnection of islands happen
        return 2;
    }

    private int countIslands(int[][] grid, int m, int n) {
        // Differ than L200.Number Of Islands, consider later attempt
        // on modification of land cell to water cell, in count number
        // of islands task we should not modify original grid, we have
        // to use 'visited' matrix to perform DFS rather than modify
        // original grid cell value for the saving of use 'visited' matrix
        boolean[][] visited = new boolean[m][n];
        int count = 0;
        // Peform DFS to find number of islands on all potential cells
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                // Only start a new DFS on unvisited land cell
                if(grid[i][j] == 1 && !visited[i][j]) {
                    helper(grid, i, j, m, n, visited);
                    count++;
                }
            }
        }
        return count;
    }

    int[] dx = new int[] {0, 0, 1, -1};
    int[] dy = new int[] {1, -1, 0, 0};
    private void helper(int[][] grid, int x, int y, int m, int n, boolean[][] visited) {
        // Only process inboundary, unvisited and land cell
        if(x < 0 || x >= m || y < 0 || y >= n || visited[x][y] || grid[x][y] == 0) {
            return;
        }
        // Mark cell as visited
        visited[x][y] = true;
        for(int k = 0; k < 4; k++) {
            helper(grid, x + dx[k], y + dy[k], m, n, visited);
        }
    }
}

Time Complexity: O(m * n * m * n) in the worst case
First DFS for checking connectivity: O(m * n), for each land cell, simulate removing it and check connectivity.
Space Complexity: O(m * n)

Refer to chatGPT
LeetCode 1568, "Minimum Number of Days to Disconnect Island", asks you to determine the minimum number of days required to make the grid disconnected by removing at most one cell per day. If the grid is already disconnected, return 0.
This problem involves:
1.Checking connectivity of the grid.
2.Trying to disconnect the grid by removing one cell at a time.
Here's the Java solution:
Solution Explanation
1.Key Steps:
- Use Depth First Search (DFS) or Breadth First Search (BFS) to determine if the grid is connected.
- If the grid is already disconnected, return 0.
- Try removing each land cell (1) one by one, and check if the grid becomes disconnected.
- If one cell removal disconnects the grid, return 1.
- If no single cell removal works, return 2 (at least two days are required).
2.Observations:
- A grid is disconnected if there are fewer than 2 pieces of land.
- The maximum number of days needed to disconnect is 2 because removing two strategically placed cells can always disconnect the grid.
3.Time Complexity:
- O(m⋅n⋅(m⋅n)) in the worst case:
- First DFS for checking connectivity: O(m⋅n).
- For each land cell, simulate removing it and check connectivity.
4.Space Complexity:
- O(m⋅n) for the visited array during DFS.
class Solution {
    public int minDays(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        // Check if grid is already disconnected
        if (!isConnected(grid)) {
            return 0;
        }

        // Try removing one land cell at a time
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // Temporarily remove the cell
                    grid[i][j] = 0;

                    // Check if this makes the grid disconnected
                    if (!isConnected(grid)) {
                        return 1;
                    }

                    // Restore the cell
                    grid[i][j] = 1;
                }
            }
        }

        // If no single cell removal disconnects the grid, return 2
        return 2;
    }

    // Check if the grid is connected using DFS
    private boolean isConnected(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int landCount = 0;

        // Find the first land cell to start DFS
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    landCount++;
                }
            }
        }

        // If there is no land, the grid is not connected
        if (landCount == 0) {
            return false;
        }

        // Start DFS from the first land cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    dfs(grid, visited, i, j);
                    break;
                }
            }
        }

        // Check if all land cells are visited
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // Perform DFS to mark all connected land cells
    private void dfs(int[][] grid, boolean[][] visited, int x, int y) {
        int m = grid.length, n = grid[0].length;

        // Base case: check bounds and if already visited or water
        if (x < 0 || x >= m || y < 0 || y >= n || visited[x][y] || grid[x][y] == 0) {
            return;
        }

        // Mark the cell as visited
        visited[x][y] = true;

        // Explore all 4 directions
        dfs(grid, visited, x + 1, y);
        dfs(grid, visited, x - 1, y);
        dfs(grid, visited, x, y + 1);
        dfs(grid, visited, x, y - 1);
    }
}
How to proof we at most need 2 days to disconnect the island ?
The proof that at most 2 days are needed to disconnect the island relies on the structure of the grid and basic properties of graph theory. Here's how we can reason through it step by step:
Key Observations
1.Island Structure and Connectivity:
- The grid is essentially a graph where:
- Nodes are land cells (1).
- Edges represent adjacency between land cells (horizontally or vertically).
- An "island" is a connected component of this graph.
- To "disconnect the island" means breaking the graph into two or more disjoint connected components.
2.Removing Land Cells:
- Removing a cell from the grid corresponds to removing a node from the graph.
- The minimum number of nodes (land cells) that need to be removed to disconnect the graph depends on its structure:
- A graph with a "cut-point" (or articulation point) can be disconnected by removing one node.
- A graph without such a cut-point (like a cycle or biconnected component) requires at least two nodes to disconnect.
Why At Most 2 Days?
1.Case 1: The Grid Has a Cut-Point:
- If the graph has a cut-point (a land cell whose removal disconnects the graph), removing that single cell is sufficient to disconnect the island.
- In this case, the grid can be disconnected in 1 day.
2.Case 2: The Grid Has No Cut-Point:
- If the graph has no cut-point, it must form a biconnected component (a cycle-like structure where every node is part of multiple paths).
- In such cases, disconnecting the graph requires removing at least two nodes.
- Removing any two adjacent nodes from a cycle or biconnected component will disconnect it.
- Therefore, in the worst-case scenario, we can disconnect the island in 2 days.
3.Critical Property of the Grid:
- The grid's adjacency (4 directions) limits the possible connectivity of the graph. A land cell in the grid can connect to at most 4 other cells.
- This means the grid's graph is relatively sparse and cannot form highly redundant connectivity structures requiring more than two removals to disconnect.
Formal Proof Using Graph Theory
1.Graph Connectivity:
- In graph theory, a connected graph can be disconnected by removing at most the size of the minimum vertex cut.
- The vertex connectivity of the graph is defined as the smallest number of nodes that, when removed, disconnect the graph.
2.Vertex Connectivity of the Grid:
- A grid graph representing an island is planar and sparse. For such graphs:
- If the grid has one connected component, its vertex connectivity is at most 2.
- This means removing at most 2 nodes will disconnect the graph.
3.Two Nodes Are Sufficient:
- For any connected planar graph (like the grid graph), if the vertex connectivity is greater than 2, the graph must contain a cycle or a structure where every node is critical for connectivity. In such cases, removing any two adjacent nodes will break the cycle and disconnect the graph.
Conclusion
- 1 day is sufficient if the grid has a cut-point (common in sparse or linear structures).
- 2 days are sufficient in the worst case, where the graph has no cut-point but can always be disconnected by removing two carefully chosen nodes.
- It's impossible for a grid island to require more than 2 days because of its sparse connectivity and planar structure.
This reasoning guarantees that the maximum number of days to disconnect the grid is at most 2, regardless of its initial configuration.
--------------------------------------------------------------------------------
Refer to
https://leetcode.com/problems/minimum-number-of-days-to-disconnect-island/solutions/819303/python-you-need-at-most-2-days/
The tricky part of this question is to notice that, you can disconnect any given island formation in at most 2 days (and you need to convince yourself that this is true).
Day 0: Check islands at day 0, return 0 if you have less than or greater than one island.
Day 1: If not, try to add water at any given location, and check if that gives you a valid island formation.
Day 2: Else, just return 2!
Refer to
https://leetcode.com/problems/minimum-number-of-days-to-disconnect-island/solutions/819296/dfs-c-clean-code-with-explanation/comments/676908
class Solution {
    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    public int minDays(int[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        int cnt = countIsland(grid);
        if (cnt == 0 || cnt > 1) return 0;
        for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) {
            if (grid[i][j] == 1) {
                grid[i][j] = 0;
                cnt = countIsland(grid);
                grid[i][j] = 1;
                if (cnt == 0 || cnt > 1) return 1;
            }
        }
        return 2;
    }
    
    private int countIsland(int[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        boolean[][] visited = new boolean[r][c];
        int cnt = 0;
        for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) {
            if (!visited[i][j] && grid[i][j] == 1) {
                dfs(grid, i, j, visited);
                cnt++;
            }
        }
        return cnt;
    }
    
    private void dfs(int[][] grid, int r, int c, boolean[][] visited) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || visited[r][c] || grid[r][c] != 1) return;
        visited[r][c] = true;
        for (int[] d : dirs) {
            dfs(grid, r + d[0], c + d[1], visited);
        }
    }
}

--------------------------------------------------------------------------------
Refer to
L200.Number of Islands
L463.Island Perimeter
L695.Max Area of Island
L733.Flood Fill
L1034.Coloring A Border
L1192.Critical Connections in a Network
