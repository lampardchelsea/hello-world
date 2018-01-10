import java.util.LinkedList;
import java.util.Queue;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5297683.html
 * You want to build a house on an empty land which reaches all buildings in the shortest 
 * amount of distance. You can only move up, down, left and right. You are given a 2D grid 
 * of values 0, 1 or 2, where:

	Each 0 marks an empty land which you can pass by freely.
	Each 1 marks a building which you cannot pass through.
	Each 2 marks an obstacle which you cannot pass through.
	For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):
	
	1 - 0 - 2 - 0 - 1
	|   |   |   |   |
	0 - 0 - 0 - 0 - 0
	|   |   |   |   |
	0 - 0 - 1 - 0 - 0
	The point (1,2) is an ideal empty land to build a house, as the total travel 
	distance of 3+3+1=7 is minimal. So return 7.
	
	Note:
	There will be at least one building. If it is not possible to build such house 
	according to the above rules, return -1.
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/31925/java-solution-with-explanation-and-time-complexity-analysis
 * The main idea is the following:

	Traverse the matrix. For each building, use BFS to compute the shortest distance from each '0' to
	this building. After we do this for all the buildings, we can get the sum of shortest distance
	from every '0' to all reachable buildings. This value is stored
	in 'distance[][]'. For example, if grid[2][2] == 0, distance[2][2] is the sum of shortest distance 
	from this block to all reachable buildings.
	Time complexity: O(number of 1)O(number of 0) ~ O(m^2n^2)
	
	We also count how many building each '0' can be reached. It is stored in reach[][]. This can be done during 
	the BFS. We also need to count how many total buildings are there in the matrix, which is stored in 'buildingNum'.
	
	Finally, we can traverse the distance[][] matrix to get the point having shortest distance to all buildings. O(m*n)
	
	The total time complexity will be O(m^2*n^2), which is quite high!. Please let me know if I did the analysis 
	wrong or you have better solution.
 */
public class ShortestDistanceFromAllBuildings {
int[] dx = {0,1,-1,0};
	int[] dy = {1,0,0,-1};
	public int shortestDistance(int[][] grid) {
		if(grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
			return 0;
		}
		int rows = grid.length;
		int cols = grid[0].length;
		// 'distance' recording each cell's total distance to all reachable buildings
		int[][] distance = new int[rows][cols];
		// 'reach' recording each cell's total reachable building numbers
		int[][] reach = new int[rows][cols];		

		int numOfBuildings = 0;
		
		// Recursive checking every building cell as initial start point
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(grid[i][j] == 1) {
					numOfBuildings++;
		            // Calling BFS for each cell
					bfs(i, j, grid, rows, cols, distance, reach);
				}
			}
		}
		
		int minDistance = Integer.MAX_VALUE;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(distance[i][j] < minDistance && reach[i][j] == numOfBuildings) {
					minDistance = distance[i][j];
				}
			}
		}
		return minDistance;
	}
	
	private void bfs(int i, int j, int[][] grid, int rows, int cols, int[][] distance, int[][] reach) {
		boolean[][] visited = new boolean[rows][cols];
		int level = 1;
		Queue<int[]> queue = new LinkedList<int[]>();
		queue.offer(new int[]{i, j});
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int k = 0; k < size; k++) {
				int[] curr = queue.poll();
				for(int l = 0; l < 4; l++) {
				    int new_x = curr[0] + dx[l];
				    int new_y = curr[1] + dy[l];
				    if(new_x >= 0 && new_x < rows && new_y >= 0 && new_y < cols 
				    		&& !visited[new_x][new_y] && grid[new_x][new_y] == 0) {
				    	//The shortest distance from [nextRow][nextCol] to this building
                        // is 'level'.
				    	distance[new_x][new_y] += level;
				    	reach[new_x][new_y]++;
				    	visited[new_x][new_y] = true;
				    	queue.offer(new int[]{new_x, new_y});
				    }
				}
			}
			level++;
		}
	}
	
	
	public static void main(String[] args) {
		ShortestDistanceFromAllBuildings s = new ShortestDistanceFromAllBuildings();
		int[][] grid = {{1,0,2,0,1}, 
				        {0,0,0,0,0},
				        {0,0,1,0,0}};
		int result = s.shortestDistance(grid);
		System.out.println(result);
	}
}
