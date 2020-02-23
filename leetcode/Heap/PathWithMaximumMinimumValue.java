/**
 Refer to
 https://www.cnblogs.com/Dylan-Java-NYC/p/11297106.html
 Given a matrix of integers A with R rows and C columns, find the maximum score of a path starting 
 at [0,0] and ending at [R-1,C-1].
 
 The score of a path is the minimum value in that path.  For example, the value of the path 8 →  4 →  5 →  9 is 4.
 A path moves some number of times from one visited cell to any neighbouring unvisited cell in one of 
 the 4 cardinal directions (north, east, west, south).
 Example 1:
 Input: [[5,4,5],[1,2,6],[7,4,6]]
 Output: 4
 Explanation: 
 The path with the maximum score is highlighted in yellow. 
 
 Example 2:
 Input: [[2,2,1,2,2,2],[1,2,2,2,1,2]]
 Output: 2
 
 Example 3:
 Input: [[3,4,6,3,4],[0,2,1,1,7],[8,8,3,2,7],[3,2,4,9,8],[4,1,2,0,0],[4,6,5,4,3]]
 Output: 3
 Note:
 1 <= R, C <= 100
 0 <= A[i][j] <= 10^9
*/

// Solution 1: MaxPQ + BFS
// Refer to
// https://www.cnblogs.com/Dylan-Java-NYC/p/11297106.html
/**
 From A[0][0], put element with index into maxHeap, sorted by element. Mark it as visited.
 When polling out the currrent, check its surroundings. If not visited before, put it into maxHeap.
 Until we hit the A[m-1][n-1].
 Time Complexity: O(m*n*logmn). m = A.length. n = A[0].length. maxHeap add and poll takes O(logmn).
 Space: O(m*n).
*/
class Solution {
	public int maximumMinimumPath(int[][] A) {
	    int[] dx = new int[] {0,0,1,-1};
	    int[] dy = new int[] {1,-1,0,0};
	    int m = A.length;
	    int n = A[0].length;
	    // Max heap
	    PriorityQueue < int[] > maxPQ = new PriorityQueue < int[] > ((a, b) - > b[2] - a[2]);
	    maxPQ.offer(new int[] {0, 0, A[0][0]});
	    boolean[][] visited = new boolean[m][n];
	    int result = A[0][0];
	    while (!maxPQ.isEmpty()) {
	        int[] cur = maxPQ.poll();
	        result = Math.min(result, cur[2]);
	        if (cur[0] == m - 1 && cur[1] == n - 1) {
	            return result;
	        }
	        for (int k = 0; k < 4; k++) {
	            int new_x = cur[0] + dx[k];
	            int new_y = cur[1] + dy[k];
	            if (new_x >= 0 && new_x < m && new_y >= 0 && new_y < n && !visited[new_x][new_y]) {
	                maxPQ.offer(new int[] {
	                    new_x,
	                    new_y,
	                    A[new_x][new_y]
	                });
	                visited[new_x][new_y] = true;
	            }
	        }
	    }
	    return result;
	}
}
