
public class Solution {
    
    private class UnionFind {
        private int[] father;
        private int count;
        public UnionFind (int n) {
            this.father = new int[n + 1];
            for(int i = 1; i < n + 1; i++) {
                father[i] = i;
            } 
            count = 0;
        }
        
        public void setCount(int count) {
            this.count = count;
        }
        
        public int find(int x) {
            if(father[x] == x) {
                return x;
            }
            return father[x] = find(father[x]);
        }
        
        public void connect(int a, int b) {
            int root_a = find(a);
            int root_b = find(b);
            if(root_a != root_b) {
                father[root_a] = root_b;
                count--;
            }
        }
        
        public int query() {
            return count;
        }
    }
    
    /*
     * @param grid: a boolean 2D matrix
     * @return: an integer
     */
    public int numIslands(boolean[][] grid) {
        if(grid == null) {
            return 0;
        }
        int m = grid.length;
        if(m == 0) {
            return 0;
        }
        int n = grid[0].length;
        if(n == 0) {
            return 0;
        }
        int size = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j]) {
                    size++;
                }
            }
        }
        UnionFind u = new UnionFind(m * n);
        u.setCount(size);
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j]) {
                    if(i > 0 && grid[i - 1][j]) {
                        u.connect(i * n + j, (i - 1) * n + j);
                    }
                    if(i < m - 1 && grid[i + 1][j]) {
                        u.connect(i * n + j, (i + 1) * n + j);
                    }
                    if(j > 0 && grid[i][j - 1]) {
                        u.connect(i * n + j, i * n + j - 1);
                    }
                    if(j < n - 1 && grid[i][j + 1]) {
                        u.connect(i * n + j, i * n + j + 1);
                    }
                }
            }
        }
        return u.query();
    }
}
