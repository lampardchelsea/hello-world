import java.util.Queue;

/**
 * Refer to
 * https://leetcode.com/problems/number-of-islands/description/
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. 
 * An island is surrounded by water and is formed by connecting adjacent lands 
 * horizontally or vertically. You may assume all four edges of the grid are all 
 * surrounded by water.
	Example 1:
	11110
	11010
	11000
	00000
	Answer: 1
	
	Example 2:
	11000
	11000
	00100
	00011
	Answer: 3
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/number-of-islands/
 * https://aaronice.gitbooks.io/lintcode/content/graph_search/number_of_islands.html
 此题可以考虑用Union Find，不过更简单的是用BFS或者DFS。
其中DFS结合mark的方法最巧妙简单，n^2循环，扫描grid[i][j], 如果是island的，即grid[i][j] == true，
则计数加一（ans++），并对四个方向进行DFS查找，并将所有属于那坐岛屿的点mark为非岛屿。这样扫描过的
地方全部会变成非岛屿，而岛屿的数量已被记录
 */
public class NumberOfIslands {
	// Solution 1: BFS
    private class Coordinate {
        int x;
        int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int islands = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == '1') {
                    markByBfs(grid, i, j);
                    islands++;
                }
            }
        }
        return islands;
    }
    
    private void markByBfs(char[][] grid, int x, int y) {
        // magic numbers!
        int[] directionX = {0, 1, -1, 0};
        int[] directionY = {1, 0, 0, -1};
        Queue<Coordinate> queue = new LinkedList<Coordinate>();
        queue.offer(new Coordinate(x, y));
        grid[x][y] = '0';
        while(!queue.isEmpty()) {
            Coordinate coor = queue.poll();
            // Mark connected neighbors of current node on four 
            // directions all to '0' if they are currently '1'
            // (思路：DFS、BFS。只要遍历一遍，碰到一个1，
            // 就把它周围所有相连的1都标记为非1，这样整个遍历过程中
            // 碰到的1的个数就是所求解)
            for(int i = 0; i < 4; i++) {
                Coordinate adj = new Coordinate(coor.x + directionX[i], coor.y + directionY[i]);
                // If after adding majic numbers out of current grid, ignore
                if(!inBound(adj, grid)) {
                    continue;
                }
                if(grid[adj.x][adj.y] == '1') {
                    grid[adj.x][adj.y] = '0';
                    queue.offer(adj);
                }
            }
        }        
    }
    
    private boolean inBound(Coordinate coor, char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        return coor.x >= 0 && coor.x < m && coor.y >= 0 && coor.y < n; 
    }
    
    // Solution 2: Union Find
    /**
     * 思路二： Union Find 利用Union Find的find和union操作，对岛屿进行合并计数。因为UnionFind结构一般来说是一维的
		| 1 | 2 | 3 | 4 |
		-----------------
		| 2 | 2 | 2 | 4 |
		表达了如下的连通结构
		1 - 2    4
		| /
		3
		因此可以转换二维矩阵坐标为一维数字，M N 的矩阵中`(i,j) -> i N + j`。
		建立UnionFind的parent[] (或者 parent hashmap)，初始化各个parent[i * N + j] = i * N + j，
		如果grid[i][j] == true，则岛屿计数count++
		之后再对矩阵遍历一次，当遇到岛屿时，则要检查上下左右四个方向的邻近点，如果也是岛屿则合并，并且count--；
		Union Find结构可以用Path Compression和Weighted Union进行优化。
     *
     */
    class UnionFind { 
        private int[] parent;
        private int count;

        private int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            return parent[x] = find(parent[x]);
        }

        public UnionFind(int n) {
            // initialize your data structure here.
            parent = new int[n];
            for (int i = 0; i < n; ++i) {
                parent[i] = i;
            }
        }

        public void connect(int a, int b) {
            // Write your code here
            int src = find(a);
            int dst = find(b);
            if (src != dst) {
                parent[src] = dst;
                count--;
            }
        }
            
        public int query() {
            // Write your code here
            return count;
        }
        
        public void set_count(int total) {
            count = total;
        }
    }

    public class Solution {
        /**
         * @param grid a boolean 2D matrix
         * @return an integer
         */
        public int numIslands(char[][] grid) {
            int count = 0;
            int n = grid.length;
            if (n == 0)
                return 0;
            int m = grid[0].length;
            if (m == 0)
                return 0;
            UnionFind uf = new UnionFind(n * m);
            
            int total = 0;
            for(int i = 0;i < grid.length; i++) {
                for(int j = 0;j < grid[0].length; j++) {
                    if (grid[i][j] == '1') {
                        total++;
                    }
                }
            }
            uf.set_count(total);
            for(int i = 0; i < grid.length; ++i)
                for(int j = 0;j < grid[0].length; ++j)
                if (grid[i][j] == '1') {
                    if (i > 0 && grid[i - 1][j] == '1') {
                        uf.connect(i * m + j, (i - 1) * m + j);
                    }
                    if (i <  n - 1 && grid[i + 1][j] == '1') {
                        uf.connect(i * m + j, (i + 1) * m + j);
                    }
                    if (j > 0 && grid[i][j - 1] == '1') {
                        uf.connect(i * m + j, i * m + j - 1);
                    }
                    if (j < m - 1 && grid[i][j + 1] == '1') {
                        uf.connect(i * m + j, i * m + j + 1);
                    }
                }
            return uf.query();
        }
    }
    
    
}

