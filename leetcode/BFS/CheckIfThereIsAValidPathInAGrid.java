/**
 Refer to
 https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/
 Given a m x n grid. Each cell of the grid represents a street. The street of grid[i][j] can be:
1 which means a street connecting the left cell and the right cell.
2 which means a street connecting the upper cell and the lower cell.
3 which means a street connecting the left cell and the lower cell.
4 which means a street connecting the right cell and the lower cell.
5 which means a street connecting the left cell and the upper cell.
6 which means a street connecting the right cell and the upper cell.


You will initially start at the street of the upper-left cell (0,0). A valid path in the grid is a path which starts 
from the upper left cell (0,0) and ends at the bottom-right cell (m - 1, n - 1). The path should only follow the streets.

Notice that you are not allowed to change any street.

Return true if there is a valid path in the grid or false otherwise.

Example 1:
Input: grid = [[2,4,3],[6,5,2]]
Output: true
Explanation: As shown you can start at cell (0, 0) and visit all the cells of the grid to reach (m - 1, n - 1).

Example 2:
Input: grid = [[1,2,1],[1,2,1]]
Output: false
Explanation: As shown you the street at cell (0, 0) is not connected with any street of any other cell and you will get stuck at cell (0, 0)

Example 3:
Input: grid = [[1,1,2]]
Output: false
Explanation: You will get stuck at cell (0, 1) and you cannot reach cell (0, 2).

Example 4:
Input: grid = [[1,1,1,1,1,1,3]]
Output: true

Example 5:
Input: grid = [[2],[2],[2],[2],[2],[2],[6]]
Output: true

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 300
1 <= grid[i][j] <= 6
*/

// Solution 1: BFS + Check if able to move one step back to check really connectioned or not + Direction order matters
// Refer to
// https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/discuss/547371/Java-clean-BFS
/**
 class Solution {
    int[][][] dirs = {
                {{0, -1}, {0, 1}},
                {{-1, 0}, {1, 0}},
                {{0, -1}, {1, 0}},
                {{0, 1}, {1, 0}},
                {{0, -1}, {-1, 0}},
                {{0, 1}, {-1, 0}}
    };
    //the idea is you need to check port direction match, you can go to next cell and check whether you can come back.
    public boolean hasValidPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0});
        visited[0][0] = true;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];
            int num = grid[x][y] - 1;
            for (int[] dir : dirs[num]) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx < 0 || nx >= m || ny < 0 || ny >= n || visited[nx][ny]) continue;
                //go to the next cell and come back to orign to see if port directions are same
                for (int[] backDir : dirs[grid[nx][ny] - 1])
                    if (nx + backDir[0] == x && ny + backDir[1] == y) {
                        visited[nx][ny] = true;
                        q.add(new int[]{nx, ny});
                    }
            }
        }
        return visited[m - 1][n - 1];
    }
}
 Complexity
 Time & Space: O(m*n), where m is the number of rows, n is the number of columns of grid
*/

// https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/discuss/547263/Python-easy-DFS
/**
class Solution:
    def hasValidPath(self, grid: List[List[int]]) -> bool:
        if not grid:
            return true
        directions = {1: [(0,-1),(0,1)],
                      2: [(-1,0),(1,0)],
                      3: [(0,-1),(1,0)],
                      4: [(0,1),(1,0)],
                      5: [(0,-1),(-1,0)],
                      6: [(0,1),(-1,0)]}
        visited = set()
        goal = (len(grid)-1, len(grid[0]) - 1)
        def dfs(i, j):
            visited.add((i,j))
            if (i,j) == goal:
                return True
            for d in directions[grid[i][j]]:
                ni, nj = i+d[0], j+d[1]
                if 0 <= ni < len(grid) and 0 <= nj < len(grid[0]) and (ni, nj) not in visited and (-d[0], -d[1]) in directions[grid[ni][nj]]:
                    if dfs(ni, nj):
                        return True
            return False
        return dfs(0,0)
Why (-d[0], -d[1]) in directions[grid[ni][nj]]:?

When traversing from one cell to the next. the next cell must have a direction that is the opposite of the direction we are moving in for the 
cells to be connected. For example, if we are moving one unit to the right, then from the next cell it must be possible to go one unit to the 
left, otherwise it's not actually connected.
*/
class Solution {
    // This is wrong !!!
    //int[][][] dirs = new int[][][] {{{-1,0},{1,0}}, 
    //                                {{0,1},{0,-1}},
    //                                {{1,0},{0,-1}},
    //                                {{-1,0},{0,-1}},
    //                                {{1,0},{0,1}},
    //                                {{-1,0},{1,0}}};
    
    /**
        Now the direction order matters:
        So {i, j} --> the 1st parameter i relate to row, which means the change
        of row number, and in normal DFS/BFS we will use x to handle this, so 
        (x + dx[i]) means the next x value which means the next row number, 
        the 2nd parameter j relate to column, which means the change of column 
        number, and in normal DFS/BFS we will use y to handle this, so (y + dy[j])
        means the next y value which means the next column number
        i -> row (grid.length)
        j -> col (grid[0].length)
        (0, 1) -> (row, col + 1)
        (1, 0) -> (row + 1, col)
        (-1, 0) -> (row - 1, col)
        (0, -1) -> (row, col - 1)

        So similarly in a daily dose of dx, dy definition, it actually means
        below 4 movement on directions:
        int[] dx = new int[] {0,0,1,-1};
        int[] dy = new int[] (1,-1,0,0);
        Move Right Move Left    Move Down    Move Up
        {0,1}      {0,-1}       {1,0}         {-1,0}
        row col+1  row col-1    row+1 col    row-1 col
    */
    int[][][] dirs = new int[][][] {{{0,-1},{0,1}}, // col-1(move left),col+1(move right)
                                    {{-1,0},{1,0}}, // row-1(move up),row+1(move down)
                                    {{0,-1},{1,0}}, // col-1(move left),row+1(move down)
                                    {{0,1},{1,0}},  // col+1(move right),row+1(move down)
                                    {{0,-1},{-1,0}},// col-1(move left),row-1(move up)
                                    {{0,1},{-1,0}}};// col+1(move right),row-1(move up)
    
    // The idea is you need to check port direction match, you can go to next cell and check whether you can come back.
    public boolean hasValidPath(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<int[]> q = new LinkedList<int[]>();
        q.offer(new int[] {0, 0});
        visited[0][0] = true;
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int dir_index = grid[x][y] - 1;
            for(int[] dir : dirs[dir_index]) {
                int new_x = x + dir[0];
                int new_y = y + dir[1];
                if(new_x >= 0 && new_x < grid.length && new_y >= 0 && new_y < grid[0].length && !visited[new_x][new_y]) {
                    // Check if next cell can go back to current cell
                    int back_dir_index = grid[new_x][new_y] - 1;
                    for(int[] back_dir : dirs[back_dir_index]) {
                        int back_x = new_x + back_dir[0];
                        int back_y = new_y + back_dir[1];
                        // Able to go back, two grids connected mutually
                        if(back_x == x && back_y == y) {
                            if(new_x == grid.length - 1 && new_y == grid[0].length - 1) {
                                return true;
                            }
                            q.offer(new int[] {new_x, new_y});
                            visited[new_x][new_y] = true;
                        }
                    }
                }
            }
        }
        //return false;
        // We cannot directly return false, test out by [[0]] as input
        return visited[grid.length - 1][grid[0].length - 1];
    }
}
