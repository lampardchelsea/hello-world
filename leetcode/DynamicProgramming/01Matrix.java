/**
 Refer to
 https://leetcode.com/problems/01-matrix/
 Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.
The distance between two adjacent cells is 1.
Example 1:
Input:
[[0,0,0],
 [0,1,0],
 [0,0,0]]
Output:
[[0,0,0],
 [0,1,0],
 [0,0,0]]
 
Example 2:
Input:
[[0,0,0],
 [0,1,0],
 [1,1,1]]
Output:
[[0,0,0],
 [0,1,0],
 [1,2,1]]
 
Note:
The number of elements of the given matrix will not exceed 10,000.
There are at least one 0 in the given matrix.
The cells are adjacent in only four directions: up, down, left and right.
*/
// Solution 2: DP
// Refer to
// https://leetcode.com/problems/01-matrix/discuss/101051/Simple-Java-solution-beat-99-(use-DP)
// https://leetcode.com/problems/01-matrix/discuss/101051/Simple-Java-solution-beat-99-(use-DP)/113437
/**
 The first iteration is from upper left corner to lower right. It's trying to update dis[i][j] to be the distance to the nearest 0 
 that is in the top left region relative to (i,j). If there's a nearer 0 to the right or to the bottom of (i,j), it won't catch that. 
 And because of the direction of the double loop, it's already in correct iterative order, meaning, you must have dealt with 
 dis[i-1][j] and dis[i][j-1] before you deal with dis[i][j]
 Then in the second loop, it goes the opposite direction from the lower right corner. So it'll find the distance to the nearest 0 
 in the bottom right region. Now combine that with the result from the first loop, it'll cover nearest 0 in all directions. 
 That is where dis[i][j] takes the min of its previous value from the first loop, and the new value (distance to the nearest 0 
 in the lower right region)
*/
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return matrix;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] result = new int[m][n];
        // Update result[i][j] to be the distance to the nearest 0 
        // that is in the top left region relative to (i,j)
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(matrix[i][j] == 0) {
                    result[i][j] = 0;
                } else {
                    int up_cell = 0;
                    if(i > 0) {
                        up_cell = result[i - 1][j];
                    } else {
                        up_cell = m + n; // maximum range for distance
                    }
                    int left_cell = 0;
                    if(j > 0) {
                        left_cell = result[i][j - 1];
                    } else {
                        left_cell = m + n;
                    }
                    result[i][j] = Math.min(up_cell, left_cell) + 1;
                }
            }
        }
        // Update result[i][j] to be the distance to the nearest 0 
        // that is in the bottom right region relative to (i,j)     
        for(int i = m - 1; i >= 0; i--) {
            for(int j = n - 1; j >= 0; j--) {
                if(matrix[i][j] == 0) {
                    result[i][j] = 0;
                } else {
                    int down_cell = 0;
                    if(i < m - 1) {
                        down_cell = result[i + 1][j];
                    } else {
                        down_cell = m + n;
                    }
                    int right_cell = 0;
                    if(j < n - 1) {
                        right_cell = result[i][j + 1];
                    } else {
                        right_cell = m + n;
                    }
                    result[i][j] = Math.min(Math.min(down_cell, right_cell) + 1, result[i][j]);
                }
            }
        }
        return result;
    }
}


































































































https://leetcode.com/problems/01-matrix/

Given an m x n binary matrix mat, return the distance of the nearest 0 for each cell.

The distance between two adjacent cells is 1.

Example 1:


```
Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
Output: [[0,0,0],[0,1,0],[0,0,0]]
```

Example 2:


```
Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
Output: [[0,0,0],[0,1,0],[1,2,1]]
```
 
Constraints:
- m == mat.length
- n == mat[i].length
- 1 <= m, n <= 104
- 1 <= m * n <= 104
- mat[i][j] is either 0 or 1.
- There is at least one 0 in mat.
---
Attempt 1: 2023-08-18

Solution 1: BFS + Topological pattern (30 min)
```
class Solution {
    public int[][] updateMatrix(int[][] mat) {
        Queue<int[]> q = new LinkedList<int[]>();
        int m = mat.length;
        int n = mat[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(mat[i][j] == 0) {
                    q.offer(new int[]{i, j});
                } else {
                    // Change original 1 to -1 in case of distance for a cell might be set to 1
                    mat[i][j] = -1;
                }
            }
        }
        // Similar as Topological Sort pattern, start from all mat[i][j] = 0 
        // cells as "center"(s) and level by level gradually expand footprint 
        // on four directions of these "center"(s), if find a mat[i][j] = -1
        // means we find a target cell originally not 0, update distance on 
        // this cell {i,j}, then consider the new cell {i,j} as new "center" 
        // which going to expand footprint on four directions again to find 
        // futher mat[i][j] = -1, and to implement this logic, we add new 
        // "center" into queue, after adding all new "center"(s) into queue, 
        // it consider as a new level, and correspondingly when start new level 
        // scanning around new "center"(s) we have to increase distance by 1 
        // to mark the potential mat[i][j] = -1 cell around new "center"(s)
        int[] dx = new int[]{0,0,1,-1};
        int[] dy = new int[]{1,-1,0,0};
        int distance = 0;
        while(!q.isEmpty()) {
            distance++;
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int[] cur = q.poll();
                for(int k = 0; k < 4; k++) {
                    int new_x = cur[0] + dx[k];
                    int new_y = cur[1] + dy[k];
                    if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n 
                    && mat[new_x][new_y] == -1) {
                        mat[new_x][new_y] = distance;
                        q.offer(new int[]{new_x, new_y});
                    }
                }
            }
        }
        return mat;
    }
}

Time Complexity: O(M * N), where M is number of rows, N is number of columns in the matrix. 
Space Complexity: O(M * N), space for the queue.
```

Refer to
https://leetcode.com/problems/01-matrix/solutions/2583027/dfs-vs-bfs-which-is-better-when-to-use-them/
Solution 1: BFS on zero cells first
- For convenience, let's call the cell with value 0 as zero-cell, the cell has with value 1 as one-cell, the distance of the nearest 0 of a cell as distance.
- Firstly, we can see that the distance of all zero-cells are 0.
- Same idea with Topology Sort, we process zero-cells first, then we use queue data structure to keep the order of processing cells, so that cells which have the smaller distance will be processed first. Then we expand the unprocessed neighbors of the current processing cell and push into our queue.
- Afterall, we can achieve the minimum distance of all cells in our matrix.


```
class Solution {
    int[] DIR = new int[]{0, 1, 0, -1, 0};
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length, n = mat[0].length; // The distance of cells is up to (M+N)
        Queue<int[]> q = new ArrayDeque<>();
        for (int r = 0; r < m; ++r)
            for (int c = 0; c < n; ++c)
                if (mat[r][c] == 0) q.offer(new int[]{r, c});
                else mat[r][c] = -1; // Marked as not processed yet!
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0], c = curr[1];
            for (int i = 0; i < 4; ++i) {
                int nr = r + DIR[i], nc = c + DIR[i+1];
                if (nr < 0 || nr == m || nc < 0 || nc == n || mat[nr][nc] != -1) continue;
                mat[nr][nc] = mat[r][c] + 1;
                q.offer(new int[]{nr, nc});
            }
        }
        return mat;
    }
}
```
Complexity
- Time: O(M * N), where M is number of rows, N is number of columns in the matrix.
- Space: O(M * N), space for the queue.
---
Solution 2: Traverse on two ways (30 min)
```
class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int max = m + n;
        // Inplace update distance from top to bottom + from left to right on matrix
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(mat[i][j] == 0) {
                    continue;
                } else {
                    // Handling first row and first column with default value 'max'
                    int top_distance = max;
                    int left_distance = max;
                    // Otherwise the top_distance always coming from previous row, the
                    // left_distance always coming from previous column
                    if(i > 0) {
                        top_distance = mat[i - 1][j];
                    }
                    if(j > 0) {
                        left_distance = mat[i][j - 1];
                    }
                    // Compare top_distance and left_distance to get the minimum value 
                    // and also locally update matrix itself without create new 2D array
                    mat[i][j] = Math.min(top_distance, left_distance) + 1;
                }
            }
        }
        // Inplace update distance from bottom to top + from right to left on matrix
        for(int i = m - 1; i >= 0; i--) {
            for(int j = n - 1; j >= 0; j--) {
                if(mat[i][j] == 0) {
                    continue;
                } else {
                    int bottom_distance = max;
                    int right_distance = max;
                    if(i < m - 1) {
                        bottom_distance = mat[i + 1][j];
                    }
                    if(j < n - 1) {
                        right_distance = mat[i][j + 1];
                    }
                    mat[i][j] = Math.min(mat[i][j], Math.min(bottom_distance, right_distance) + 1);
                }
            }
        }
        return mat;
    }
}

Time Complexity: O(M * N), where M is number of rows, N is number of columns in the matrix. 
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/01-matrix/solutions/1369741/c-java-python-bfs-dp-solutions-with-picture-clean-concise-o-1-space/
✔️ Solution 2: Dynamic Programming
- For convenience, let's call the cell with value 0 as zero-cell, the cell has with value 1 as one-cell, the distance of the nearest 0 of a cell as distance.
- Firstly, we can see that the distance of all zero-cells are 0, so we skip zero-cells, we process one-cells only.
- In DP, we can only use previous values if they're already computed.
- In this problem, a cell has at most 4 neighbors that are left, top, right, bottom. If we use dynamic programming to compute the distance of the current cell based on 4 neighbors simultaneously, it's impossible because we are not sure if distance of neighboring cells is already computed or not.
- That's why, we need to compute the distance one by one:
	- Firstly, for a cell, we restrict it to only 2 directions which are left and top. Then we iterate cells from top to bottom, and from left to right, we calculate the distance of a cell based on its left and top neighbors.
	- Secondly, for a cell, we restrict it only have 2 directions which are right and bottom. Then we iterate cells from bottom to top, and from right to left, we update the distance of a cell based on its right and bottom neighbors.


```
class Solution { // 5 ms, faster than 99.66%
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length, n = mat[0].length, INF = m + n; // The distance of cells is up to (M+N)
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (mat[r][c] == 0) continue;
                int top = INF, left = INF;
                if (r - 1 >= 0) top = mat[r - 1][c];
                if (c - 1 >= 0) left = mat[r][c - 1];
                mat[r][c] = Math.min(top, left) + 1;
            }
        }
        for (int r = m - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 0; c--) {
                if (mat[r][c] == 0) continue;
                int bottom = INF, right = INF;
                if (r + 1 < m) bottom = mat[r + 1][c];
                if (c + 1 < n) right = mat[r][c + 1];
                mat[r][c] = Math.min(mat[r][c], Math.min(bottom, right) + 1);
            }
        }
        return mat;
    }
}
```
Complexity
- Time: O(M * N), where M is number of rows, N is number of columns in the matrix.
- Space: O(1)
---
DFS vs BFS | Which is better | when to use them
Refer to
https://leetcode.com/problems/01-matrix/solutions/2583027/dfs-vs-bfs-which-is-better-when-to-use-them/
Initially this problem seems to be very similar to all the backtracking problems that you would've solved earlier like(rat in a maze, N Queen problem) . Which made me think the same and i ended up getting the TLE for the DFS solution and then i tried applying DP but that is a mess too. Here are some of the points that i learnt from this question:
1. The previous DFS + Backtracking problems we've always had the definite start and endpoint so it doesn't really matter to end the recursive all . But where as here the starting points and the ending points are scattered which may consume a lot of time if we apply a DFS solution.
2. DFS will come in to action , if you have a choice and you decide to pick that up and try to build a solution using that choice and finally find the best of all the choices you made
3. BFS works differently, where it finds the shorted node and the next shortest node to these visited nodes and so on... and eventually you reach a solution which will give you the shortest path.

Verdict:
Whenever you see find shortest, minimum, nearest , quickest way Throw BFS blindly you got to break your head only to find , which will be my starting point(0 here) and which will be my desired ending point(1 here)
