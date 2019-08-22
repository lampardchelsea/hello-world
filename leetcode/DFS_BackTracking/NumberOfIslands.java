/**
 Refer to
 https://leetcode.com/problems/number-of-islands/submissions/
 Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. 
 An island is surrounded by water and is formed by connecting adjacent lands horizontally 
 or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:
Input:
11110
11010
11000
00000
Output: 1

Example 2:
Input:
11000
11000
00100
00011
Output: 3
*/

// Solution 1: DFS (simiar to 1020: Number Of Enclaves)
class Solution {
    public int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == '1') {
                    count++;
                    helper(i, j, grid);
                }
            }
        }
        return count;
    }
    
    int[] dx = new int[]{0,0,1,-1};
    int[] dy = new int[]{1,-1,0,0};
    private void helper(int i, int j, char[][] grid) {
        if(i >= 0 && i <= grid.length - 1 && j >= 0 && j <= grid[0].length - 1 
           && grid[i][j] == '1') {
            grid[i][j] = '0';
            for(int k = 0; k < 4; k++) {
                helper(i + dx[k], j + dy[k], grid);
            }
        }
    }
}

// Solution 2: Union-Find
class Solution {
    public int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        UnionFind uf = new UnionFind(rows * cols);
        int count = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == '1') {
                    count++;
                }
            }
        }
        uf.set_count(count);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == '1') {
                    if(i > 0 && grid[i - 1][j] == '1') {
                        uf.union(i * cols + j, (i - 1) * cols + j);
                    }
                    if(i < rows - 1 && grid[i + 1][j] == '1') {
                        uf.union(i * cols + j, (i + 1) * cols + j);
                    }
                    if(j > 0 && grid[i][j - 1] == '1') {
                        uf.union(i * cols + j, i * cols + (j - 1));
                    }
                    if(j < cols - 1 && grid[i][j + 1] == '1') {
                        uf.union(i * cols + j, i * cols + (j + 1));
                    }
                }
            }
        }
        return uf.get_count();
    }
    
    class UnionFind {
        int[] parent;
        int count = 0;
        public UnionFind(int n) {
            parent = new int[n];
            for(int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int x) {
            if(parent[x] == x) {
                return x;
            }
            return parent[x] = find(parent[x]);
        }
        
        // public int find(int x) {
        //     while(x != parent[x]) {
        //         parent[x] = find(parent[x]);
        //         x = parent[x];
        //     }
        //     return x;
        // }
        
        public void union(int p, int q) {
            int src = find(p);
            int dst = find(q);
            if(src != dst) {
                parent[src] = dst;
                count--;
            }
        }
        
        public void set_count(int x) {
            count = x;
        }
        
        public int get_count() {
            return count;
        }
    }
}
