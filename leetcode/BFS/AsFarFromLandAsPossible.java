/**
 Refer to
 https://leetcode.com/problems/as-far-from-land-as-possible/
 Given an N x N grid containing only values 0 and 1, where 0 represents water and 1 represents land, 
 find a water cell such that its distance to the nearest land cell is maximized and return the distance.

The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) 
and (x1, y1) is |x0 - x1| + |y0 - y1|.

If no land or water exists in the grid, return -1.

Example 1:
1  0  1
0  0  0
1  0  1
Input: [[1,0,1],[0,0,0],[1,0,1]]
Output: 2
Explanation: 
The cell (1, 1) is as far as possible from all the land with distance 2.

Example 2:
1  0  0
0  0  0
0  0  0
Input: [[1,0,0],[0,0,0],[0,0,0]]
Output: 4
Explanation: 
The cell (2, 2) is as far as possible from all the land with distance 4.
 
Note:
1 <= grid.length == grid[0].length <= 100
grid[i][j] is 0 or 1
*/

// Solution 1: BFS
// Theree important tips:
// (1) Find all water(0) with N^2
// (2) Based on all water(0) find all land(1) by BFS, since its shortest path problem.
// (3) (i, j) as pair no need to use pair class, just use 100 * i + j, 
//      since 1 <= grid.length == grid[0].length <= 100, 100 * i + j will not generate
//      duplicate, if use String as i + "_" + j to generate key will cause TLE.    
class Solution {
    public int maxDistance(int[][] grid) {
        int allMax = 0;
        int len = grid.length;
        boolean allone = true;
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(grid[i][j] == 0) {
                    allone = false;
                }
            }
        }
        if(allone) {
            return -1;
        }
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(grid[i][j] == 0) {
                    int curDistance = helper(i, j, len, grid);
                    if(curDistance == -1) {
                        return -1;
                    }
                    if(curDistance > allMax) {
                        allMax = curDistance;
                    }
                }
            }
        }
        return allMax;
    }
    
    int[] dx = new int[]{0,0,-1,1};
    int[] dy = new int[]{1,-1,0,0};
    private int helper(int i, int j, int len, int[][] grid) {
        int result = 0;
        Queue<Integer> q = new LinkedList<Integer>();
        boolean[][] visited = new boolean[len][len];
        visited[i][j] = true;
        int root = 100 * i + j;
        q.offer(root);
        while(!q.isEmpty()) {
            int cur = q.poll();
            int a = cur / 100;
            int b = cur % 100;
            for(int k = 0; k < 4; k++) {
                int new_a = a + dx[k];
                int new_b = b + dy[k];
                if(new_a >= 0 && new_a < len && new_b >= 0 && new_b < len && !visited[new_a][new_b]) {
                    if(grid[new_a][new_b] == 1) {
                        return Math.abs(new_a - i) + Math.abs(new_b - j);
                    }
                    visited[new_a][new_b] = true;
                    q.offer(100 * new_a + new_b);    
                }
            }
        }
        return -1;
    }
}

// TLE with String
class Solution {
    public int maxDistance(int[][] grid) {
        int allMax = 0;
        int len = grid.length;
        // boolean allzero = true;
        boolean allone = true;
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(grid[i][j] == 0) {
                    allone = false;
                }
            }
        }
        if(allone) {
            return -1;
        }
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(grid[i][j] == 0) {
                    int curDistance = helper(i, j, len, grid);
                    if(curDistance == -1) {
                        return -1;
                    }
                    if(curDistance > allMax) {
                        allMax = curDistance;
                    }
                }
            }
        }
        return allMax;
    }
    
    int[] dx = new int[]{0,0,-1,1};
    int[] dy = new int[]{1,-1,0,0};
    private int helper(int i, int j, int len, int[][] grid) {
        int result = 0;
        Queue<String> q = new LinkedList<String>();
        boolean[][] visited = new boolean[len][len];
        visited[i][j] = true;
        String str = i + "_" + j;
        q.offer(str);
        while(!q.isEmpty()) {
            String cur = q.poll();
            String[] temp = cur.split("_");
            int a = Integer.valueOf(temp[0]);
            int b = Integer.valueOf(temp[1]);
            for(int k = 0; k < 4; k++) {
                int new_a = a + dx[k];
                int new_b = b + dy[k];
                if(new_a >= 0 && new_a < len && new_b >= 0 && new_b < len 
                   && !visited[new_a][new_b]) {
                    if(grid[new_a][new_b] == 1) {
                        return Math.abs(new_a - i) + Math.abs(new_b - j);
                    }
                    visited[new_a][new_b] = true;
                    q.offer(new String(new_a + "_" + new_b));    
                }
            }
        }
        return -1;
    }
}
