/**
 Refer to
 https://leetcode.com/problems/pacific-atlantic-water-flow/
 Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific ocean" 
 touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.
 
 Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.
 Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.
 
 Note:
 The order of returned grid coordinates does not matter.
 Both m and n are less than 150.

Example:
Given the following 5x5 matrix:

  Pacific ~   ~   ~   ~   ~ 
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic

Return:
[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
*/

// Solution 1: BFS
// Refer to
// https://leetcode.com/problems/pacific-atlantic-water-flow/discuss/90733/Java-BFS-and-DFS-from-Ocean
/**
 Two Queue and add all the Pacific border to one queue; Atlantic border to another queue.
 Keep a visited matrix for each queue. In the end, add the cell visited by two queue to the result.
 BFS: Water flood from ocean to the cell. Since water can only flow from high/equal cell to low cell, 
 add the neighboor cell with height larger or equal to current cell to the queue and mark as visited.
*/
class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int[] dx = new int[] {0,0,1,-1};
        int[] dy = new int[] {1,-1,0,0};
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return result;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        Queue<int[]> pq = new LinkedList<int[]>();
        Queue<int[]> aq = new LinkedList<int[]>();
        boolean[][] pVisited = new boolean[m][n];
        boolean[][] aVisited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            pq.offer(new int[] {i, 0});     // first column
            aq.offer(new int[] {i, n - 1}); // last column
            pVisited[i][0] = true;
            aVisited[i][n - 1] = true;
        }
        for(int i = 0; i < n; i++) {
            pq.offer(new int[] {0, i});     // first row
            aq.offer(new int[] {m - 1, i}); // last row
            pVisited[0][i] = true;
            aVisited[m - 1][i] = true;
        }
        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            int x = cur[0];
            int y = cur[1];
            for(int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n && !pVisited[new_x][new_y]) {
                    if(matrix[new_x][new_y] >= matrix[x][y]) {
                        pVisited[new_x][new_y] = true;
                        pq.offer(new int[] {new_x, new_y});
                    }
                }
            }
        }
        while(!aq.isEmpty()) {
            int[] cur = aq.poll();
            int x = cur[0];
            int y = cur[1];
            for(int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n && !aVisited[new_x][new_y]) {
                    if(matrix[new_x][new_y] >= matrix[x][y]) {
                        aVisited[new_x][new_y] = true;
                        aq.offer(new int[] {new_x, new_y});
                    }
                }
            }
        }
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(pVisited[i][j] && aVisited[i][j]) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    list.add(j);
                    result.add(list);
                }
            }
        }
        return result;
    }
}

// Solution 2: DFS
// Refer to
// https://leetcode.com/problems/pacific-atlantic-water-flow/discuss/90733/Java-BFS-and-DFS-from-Ocean
class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return result;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] pVisited = new boolean[m][n];
        boolean[][] aVisited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            helper(i, 0, -1, pVisited, matrix);      // first column
            helper(i, n - 1, -1, aVisited, matrix);  // last column 
        }
        for(int i = 0; i < n; i++) {
            helper(0, i, -1, pVisited, matrix);      // first row
            helper(m - 1, i, -1, aVisited, matrix);  // last row
        }
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(pVisited[i][j] && aVisited[i][j]) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    list.add(j);
                    result.add(list);
                }
            }
        }
        return result;
    }
    
    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    private void helper(int x, int y, int height, boolean[][] visited, int[][] matrix) {
        if(x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length && !visited[x][y] && matrix[x][y] >= height) {
            visited[x][y] = true;
            for(int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                helper(new_x, new_y, matrix[x][y], visited, matrix);
            }
        }
    }
}
