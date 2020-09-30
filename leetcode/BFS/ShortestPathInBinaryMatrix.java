/**
 Refer to
 https://leetcode.com/problems/shortest-path-in-binary-matrix/
 In an N by N square grid, each cell is either empty (0) or blocked (1).

A clear path from top-left to bottom-right has length k if and only if it is composed of cells C_1, C_2, ..., C_k such that:

Adjacent cells C_i and C_{i+1} are connected 8-directionally (ie., they are different and share an edge or corner)
C_1 is at location (0, 0) (ie. has value grid[0][0])
C_k is at location (N-1, N-1) (ie. has value grid[N-1][N-1])
If C_i is located at (r, c), then grid[r][c] is empty (ie. grid[r][c] == 0).
Return the length of the shortest such clear path from top-left to bottom-right.  If such a path does not exist, return -1.

Example 1:
Input: [[0,1],[1,0]]
    0 1
    1 0
Output: 2

Example 2:
Input: [[0,0,0],[1,1,0],[1,1,0]]
    0 0 0
    1 1 0
    1 1 0
Output: 4

Note:
1 <= grid.length == grid[0].length <= 100
grid[r][c] is 0 or 1
*/

// Solution 1: BFS with 8 directions
// Refer to
// https://leetcode.com/problems/shortest-path-in-binary-matrix/discuss/312706/JAVA-BFS
// Note: handle special case as initial start with 0
class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int[] dx = new int[] {1,-1,0,0,1,-1,1,-1};
        int[] dy = new int[] {0,0,1,-1,1,-1,-1,1};
        int m = grid.length;
        int n = grid[0].length;
        if(grid[0][0] == 1 || grid[m - 1][n - 1] == 1) {
            return -1;
        }
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> q = new LinkedList<int[]>();
        visited[0][0] = true;
        q.offer(new int[] {0, 0});
        int distance = 1;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int[] cur = q.poll();
                int x = cur[0];
                int y = cur[1];
                if(x == m - 1 && y == n - 1) {
                    return distance;
                }
                for(int j = 0; j < 8; j++) {
                    int new_x = x + dx[j];
                    int new_y = y + dy[j];
                    if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n && grid[new_x][new_y] == 0 && !visited[new_x][new_y]) {
                        visited[new_x][new_y] = true;
                        q.offer(new int[] {new_x, new_y});
                    }
                }
            }
            distance++;
        }
        return -1;
    }
}
