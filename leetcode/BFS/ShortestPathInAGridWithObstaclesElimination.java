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

// Solution 2: 3D array
// Refer to
// https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/discuss/451787/Python-O(m*n*k)-BFS-Solution-with-Explanation
/**
 Solution Explanation
Because we are trying to find the shortest path, use BFS here to exit immediately when a path reaches the bottom right most cell.
Use a set to keep track of already visited paths. We only need to keep track of the row, column, and the eliminate obstacle usage count. 
We don't need to keep track of the steps because remember we are using BFS for the shortest path. That means there is no value storing 
a 4th piece of the data, the current steps. This will reduce the amount of repeat work.
m = rows
n = columns
k = allowed elimination usages

Time Complexity
O(m*n*k) time complexity
This is because for every cell (m*n), in the worst case we have to put that cell into the queue/bfs k times.

Runtime: 68 ms, faster than 33.33% of Python3 online submissions

Space Complexity
O(m*n*k) space complexity
This is because for every cell (m*n), in the worst case we have to put that cell into the queue/bfs k times which means we need to worst 
case store all of those steps/paths in the visited set.

Memory Usage: 13.9 MB, less than 100.00% of Python3 online submissions

Code
from collections import deque
class Solution:
    def shortestPath(self, grid: List[List[int]], k: int) -> int:
        if len(grid) == 1 and len(grid[0]) == 1:
            return 0

        queue = deque([(0,0,k,0)])
        visited = set([(0,0,k)])

        if k > (len(grid)-1 + len(grid[0])-1):
            return len(grid)-1 + len(grid[0])-1

        while queue:
            row, col, eliminate, steps = queue.popleft()
            for new_row, new_col in [(row-1,col), (row,col+1), (row+1, col), (row, col-1)]:
                if (new_row >= 0 and
                    new_row < len(grid) and
                    new_col >= 0 and
                    new_col < len(grid[0])):
                    if grid[new_row][new_col] == 1 and eliminate > 0 and (new_row, new_col, eliminate-1) not in visited:
                        visited.add((new_row, new_col, eliminate-1))
                        queue.append((new_row, new_col, eliminate-1, steps+1))
                    if grid[new_row][new_col] == 0 and (new_row, new_col, eliminate) not in visited:
                        if new_row == len(grid)-1 and new_col == len(grid[0])-1:
                            return steps+1
                        visited.add((new_row, new_col, eliminate))
                        queue.append((new_row, new_col, eliminate, steps+1))

        return -1
*/
// https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/discuss/452434/Java-BFS-Concise-and-Clean-O(m*n*k)
// https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/discuss/451796/Java-Straightforward-BFS-O(MNK)-time-or-O(MNK)-space
/**
 class Solution {
    int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
    public int shortestPath(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        Queue<int[]> q = new LinkedList();
        boolean[][][] visited = new boolean[n][m][k+1];
        visited[0][0][0] = true;
        q.offer(new int[]{0,0,0});
        int res = 0;
        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0; i<size; i++){
                int[] info = q.poll();
                int r = info[0], c = info[1], curK = info[2];
                if(r==n-1 && c==m-1){
                    return res;
                }
                for(int[] dir : dirs){
                    int nextR = dir[0] + r;
                    int nextC = dir[1] + c;
                    int nextK = curK;
                    if(nextR>=0 && nextR<n && nextC>=0 && nextC<m){
                        if(grid[nextR][nextC]==1){
                            nextK++;
                        }
                        if(nextK<=k && !visited[nextR][nextC][nextK]){
                            visited[nextR][nextC][nextK] = true;
                            q.offer(new int[]{nextR, nextC, nextK});
                        }
                    }
                }                
            }
            res++;
        }
        return -1;
    }
}
*/
class Solution {
    public int shortestPath(int[][] grid, int k) {
        int[] dx = new int[] {0,0,1,-1};
        int[] dy = new int[] {1,-1,0,0};
        int m = grid.length;
        int n = grid[0].length;
        boolean[][][] visited = new boolean[m][n][k + 1];
        Queue<int[]> q = new LinkedList<int[]>();
        // Initially have k chances to eliminate obstacles
        q.offer(new int[] {0, 0, k});
        visited[0][0][k] = true;
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
                    if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n) {
                        if(grid[new_x][new_y] == 1 && currPathRemain > 0 && !visited[new_x][new_y][currPathRemain - 1]) {
                            visited[new_x][new_y][currPathRemain - 1] = true;
                            q.offer(new int[] {new_x, new_y, currPathRemain - 1});
                        }
                        if(grid[new_x][new_y] == 0 && !visited[new_x][new_y][currPathRemain]) {
                            visited[new_x][new_y][currPathRemain] = true;
                            q.offer(new int[] {new_x, new_y, currPathRemain});
                        }
                    }
                }
            }
            distance++;
        }
        return -1;
    }
}
