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

// Solution 1: BFS level order traversal start from 0 cell, similar to 417.Pacific Atlantic Water Flow which start from Ocean
// Refer to
// https://leetcode.com/problems/01-matrix/discuss/248525/Java-BFS-solution-with-comments
/**
 Use BFS starting from each 0 cell and mark new length for each 1 cell
 Note: Need a way to differentiate original 1 and distance 1
 BFS starting level order traversal from each 0 cell 
*/
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        Queue<int[]> q = new LinkedList<int[]>();
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(matrix[i][j] == 1) {
                    matrix[i][j] = -1;
                } else {
                    q.offer(new int[]{i, j});
                }
            }
        }
        int[] dx = new int[] {0,0,1,-1};
        int[] dy = new int[] {1,-1,0,0};
        int distance = 0;
        while(!q.isEmpty()) {
            distance++;
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int[] cur = q.poll();
                for(int j = 0; j < 4; j++) {
                    int new_x = cur[0] + dx[j];
                    int new_y = cur[1] + dy[j];
                    if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n) {
                        if(matrix[new_x][new_y] == -1) {
                            matrix[new_x][new_y] = distance;
                            q.offer(new int[] {new_x, new_y});
                        }
                    }
                }
            }
        }
        return matrix;
    }
}
