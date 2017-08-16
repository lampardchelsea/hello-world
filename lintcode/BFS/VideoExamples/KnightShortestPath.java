import java.util.Queue;

/**
 * Refer to
 * http://www.cnblogs.com/EdwardLiu/p/6546118.html
 * Given a knight in a chess board (a binary matrix with 0 as empty and 1 as barrier) with 
 * a source position, find the shortest path to a destination position, return the length 
 * of the route. 
 * Return -1 if knight can not reached.	
 * Notice
 * source and destination must be empty.
 * Knight can not enter the barrier.
	 
	Clarification
	If the knight is at (x, y), he can get to the following positions in one step:	
	(x + 1, y + 2)
	(x + 1, y - 2)
	(x - 1, y + 2)
	(x - 1, y - 2)
	(x + 2, y + 1)
	(x + 2, y - 1)
	(x - 2, y + 1)
	(x - 2, y - 1)
	
	Example
	[[0,0,0],
	 [0,0,0],
	 [0,0,0]]
	source = [2, 0] destination = [2, 2] return 2
	
	[[0,1,0],
	 [0,0,0],
	 [0,0,0]]
	source = [2, 0] destination = [2, 2] return 6
	
	[[0,1,0],
	 [0,0,1],
	 [0,0,0]]
	source = [2, 0] destination = [2, 2] return -1
 *
 * Solution
 * http://www.cnblogs.com/aprilyang/p/6505037.html
 * http://www.cnblogs.com/EdwardLiu/p/6546118.html
 */
public class KnightShortestPath {
	private class Coordinate {
		int x;
		int y;
		boolean visited;
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
			this.visited = false;
		}
	}
	
	private boolean inBound(Coordinate coor, int[][] board) {
		int m = board.length;
		int n = board[0].length;
		return coor.x >= 0 && coor.x < m && coor.y >= 0 && coor.y < n;
	}
	
	public int shortestPath(int[][] board, int[] src, int[] dst) {
		if(board == null || board.length == 0 || board[0].length == 0) {
			return -1;
		}
		// Magic numbers
		int[] directionX = {1, 1, -1, -1, 2, 2, -2, -2};
		int[] directionY = {2, -2, 2, -2, 1, -1, 1, -1};
		int count = 0;
		Queue<Coordinate> queue = new LinkedList<Coordinate>();
		Coordinate srcCoor = new Coordinate(src[0], src[1]);
		queue.offer(srcCoor);
		while(!queue.isEmpty()) {
			// Recording dfs depth need level traverse
			int size = queue.size();
			for(int i = 0; i < size; i++) {
				Coordinate coor = queue.poll();
				// Mark as visited
				coor.visited = true;
				// If match given destination, return depth count
				if(coor.x == dst[0] && coor.y == dst[1]) {
					return count;
				}
				// Try on 8 directions with magic number
			    for(int j = 0; j < 8; j++) {
			    	Coordinate adj = new Coordinate(coor.x + directionX[j], coor.y + directionY[j]);
			    	// If not in boundary OR already visited OR the cell value not 0, skip
			    	if(!inBound(adj, board) || adj.visited || board[adj.x][adj.y] != 0) {
			    		continue;
			    	}
			    	queue.offer(adj);
			    }
			}
			count++;
		}
		// If not find a path from src to dst, return -1
		return -1;
	}

	public static void main(String[] args) {
		KnightShortestPath k = new KnightShortestPath();
//		int[][] board = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
//		int[][] board = {{0, 1, 0}, {0, 0, 0}, {0, 0, 0}};
		int[][] board = {{0, 1, 0}, {0, 0, 1}, {0, 0, 0}};
		int[] src = {2, 0};
		int[] dst = {2, 2};
		int result = k.shortestPath(board, src, dst);
		System.out.println(result);
	}
	
}

