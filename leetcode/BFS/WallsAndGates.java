import java.util.LinkedList;
import java.util.Queue;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5285868.html
 * You are given a m x n 2D grid initialized with these three possible values.

	-1  - A wall or an obstacle.
	0   - A gate.
	INF - Infinity means an empty room. We use the value 231 - 1 = 2147483647 to 
	      represent INF as you may assume that the distance to a gate is less than 2147483647.
	Fill each empty room with the distance to its nearest gate. If it is impossible 
	to reach a gate, it should be filled with INF.
	
	For example, given the 2D grid:
	INF  -1  0  INF
	INF INF INF  -1
	INF  -1 INF  -1
	  0  -1 INF INF
	
	After running your function, the 2D grid should be:
	  3  -1   0   1
	  2   2   1  -1
	  1  -1   2  -1
	  0  -1   3   4
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/35242/benchmarks-of-dfs-and-bfs
 * http://www.cnblogs.com/grandyang/p/5285868.html
 * https://discuss.leetcode.com/topic/33459/my-short-java-solution-very-easy-to-understand
 */
public class WallsAndGates {
// Solution 1: Multi-End BFS
	// Refer to
	// https://discuss.leetcode.com/topic/35242/benchmarks-of-dfs-and-bfs
	// http://www.cnblogs.com/grandyang/p/5285868.html
	/**
	 * 那么下面我们再来看BFS的解法，却要借助queue，我们首先把门的位置都排入queue中，然后开始循环，
	 * 对于门位置的四个相邻点，我们判断其是否在矩阵范围内，并且位置值是否大于上一位置的值加1，
	 * 如果满足这些条件，我们将当前位置赋为上一位置加1，并将次位置排入queue中，这样等queue中的
	 * 元素遍历完了，所有位置的值就被正确地更新了
	 */
	public void wallsAndGates(int[][] rooms) {
		int m = rooms.length;
		int n = rooms[0].length;
		if(rooms == null || m == 0 || rooms[0] == null || n == 0) {
			return;
		}
		Queue<int[]> queue = new LinkedList<int[]>();
		// Tricky part: Start calculate distance from gate (rooms[i][j] == 0)
	    for(int i = 0; i < m; i++) {
	    	for(int j = 0; j < n; j++) {
	    		if(rooms[i][j] == 0) {
	    			queue.offer(new int[] {i, j});
	    		}
	    	}
	    }
	    int[] dx = new int[]{0,1,-1,0};
	    int[] dy = new int[]{1,0,0,-1};
	    while(!queue.isEmpty()) {
	        int[] gate = queue.poll();
	        for(int p = 0; p < 4; p++) {
	        	int new_x = gate[0] + dx[p];
	        	int new_y = gate[1] + dy[p];
	        	if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n && rooms[new_x][new_y] == Integer.MAX_VALUE) {
	        		rooms[new_x][new_y] = rooms[gate[0]][gate[1]] + 1;
	        		queue.offer(new int[]{new_x, new_y});
	        	}
	        }
	    }
	}
	
	// Solution 2: DFS
	// Refer to
	// http://www.cnblogs.com/grandyang/p/5285868.html
	// https://discuss.leetcode.com/topic/33459/my-short-java-solution-very-easy-to-understand
    int[] d_x = new int[]{0,1,-1,0};
    int[] d_y = new int[]{1,0,0,-1};
	public void wallsAndGates2(int[][] rooms) {
		int m = rooms.length;
		int n = rooms[0].length;
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				if(rooms[i][j] == 0) {
					dfs(rooms, i, j, 0);
				}
			}
		}
	}
	
	private void dfs(int[][] rooms, int i, int j, int distance) {
	    if(i < 0 || i >= rooms.length || j < 0 || j >= rooms[0].length || rooms[i][j] < distance) {
	    	return;
	    }
	    rooms[i][j] = distance;
		for(int k = 0; k < 4; k++) {
		    int p = i + d_x[k];
		    int q = j + d_y[k];
		    dfs(rooms, p, q, distance + 1);
		}
	}
	
	
	public static void main(String[] args) {
		WallsAndGates w = new WallsAndGates();
		int[][] rooms = new int[][]{{Integer.MAX_VALUE, -1, 0, Integer.MAX_VALUE}, 
				                    {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, -1}, 
				                    {Integer.MAX_VALUE, -1, Integer.MAX_VALUE, -1}, 
				                    {0, -1, Integer.MAX_VALUE, Integer.MAX_VALUE}};
		w.wallsAndGates2(rooms);
		for(int i = 0; i < rooms.length; i++) {
			System.out.println("");
			for(int j = 0; j < rooms[0].length; j++) {
				System.out.print(rooms[i][j] + " ");
			}
		}
	}
}
