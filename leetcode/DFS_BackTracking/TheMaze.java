/**
 Refer to
 https://segmentfault.com/a/1190000017163338
 There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces 
 by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the 
 ball stops, it could choose the next direction.

Given the ball's start position, the destination and the maze, determine whether the ball 
could stop at the destination.

The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. 
You may assume that the borders of the maze are all walls. The start and destination coordinates 
are represented by row and column indexes.

Example 1:
Input 1: a maze represented by a 2D array

0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0

Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (4, 4)

Output: true

Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.

Example 2:
Input 1: a maze represented by a 2D array

0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0

Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (3, 2)

Output: false

Explanation: There is no way for the ball to stop at the destination.
*/
// Solution 1: DFS
// Refer to
// https://segmentfault.com/a/1190000017163338
// https://www.cnblogs.com/grandyang/p/6381458.html
/**
 这道题让我们遍历迷宫，但是与以往不同的是，这次迷宫是有一个滚动的小球，这样就不是每次只走一步了，
 而是朝某一个方向一直滚，直到遇到墙或者边缘才停下来，我记得貌似之前在手机上玩过类似的游戏。那么
 其实还是要用DFS或者BFS来解，只不过需要做一些修改。先来看DFS的解法，我们用DFS的同时最好能用上优化，
 即记录中间的结果，这样可以避免重复运算，提高效率。我们用二维数组dp来保存中间结果，然后用maze数组
 本身通过将0改为-1来记录某个点是否被访问过，这道题的难点是在于处理一直滚的情况，其实也不难，
 只要我们有了方向，只要一直在那个方向上往前走，每次判读是否越界了或者是否遇到墙了即可，然后对于
 新位置继续调用递归函数
*/
// Wrong Solution
class Solution {
    int[] dx = new int[]{0,0,1,-1};
	int[] dy = new int[]{1,-1,0,0};
	public boolean hasPath(int[][] maze, int[] start, int[] destination) {
		if(maze == null || maze.length == 0) {
			return false;
		}
		boolean[][] visited = new boolean[maze.length][maze[0].length];
		return helper(maze, visited, start[0], start[1], destination[0], destination[1]);
	}
	
	private boolean helper(int[][] maze, boolean[][] visited, int start_i, int start_j, int dest_i, int dest_j) {
		if(start_i == dest_i && start_j == dest_j) {
			return true;
		}
		if(start_i < 0 || start_i >= maze.length || start_j < 0 || start_j >= maze[0].length || visited[start_i][start_j]) {
			return false;
		}
		visited[start_i][start_j] = true;
		for(int k = 0; k < 4; k++) {
			// rolling until out or hit the wall
			while(start_i >= 0 && start_i < maze.length && start_j >= 0 && start_j < maze[0].length && maze[start_i][start_j] != 1) {
				start_i += dx[k];
				start_j += dy[k];
			}
			// one step back to the stop position
			start_i -= dx[k];
			start_j -= dy[k];
			// start a new dfs from the stop position
			if(helper(maze, visited, start_i, start_j, dest_i, dest_j)) {
				return true;
			}
		}
		return false;
	}
}

// Correct Solution with reassign start_i, start_j to x, y
// Caution: The difference here is very imporatnt, after each time rolling until hit the wall, we must return back
// to original start position to prepare another direction DFS, same as normal DFS one direction one step, but since
// we have to rolling until hit the wall in this problem, recording the original position by int x = start_i, 
// int y = start_j is very critical
class Solution {
    int[] dx = new int[]{0,0,1,-1};
	int[] dy = new int[]{1,-1,0,0};
	public boolean hasPath(int[][] maze, int[] start, int[] destination) {
		if(maze == null || maze.length == 0) {
			return false;
		}
		boolean[][] visited = new boolean[maze.length][maze[0].length];
		return helper(maze, visited, start[0], start[1], destination[0], destination[1]);
	}
	
	private boolean helper(int[][] maze, boolean[][] visited, int start_i, int start_j, int dest_i, int dest_j) {
		if(start_i == dest_i && start_j == dest_j) {
			return true;
		}
		if(start_i < 0 || start_i >= maze.length || start_j < 0 || start_j >= maze[0].length || visited[start_i][start_j]) {
			return false;
		}
		visited[start_i][start_j] = true;
		for(int k = 0; k < 4; k++) {
		        // Don't understand why assign start_i, start_j to new variable x, y?
			// Recording start position before start rolling in the maze, for next time start another
			// direction's DFS, since we are rolling until hit the wall, so start position if not
			// record will change to the stop position, which change the start condition of DFS in this
			// time recursion, actually we do this in an implicit way in normal DFS as directly start
			// four directions DFS in for loop, which not modify the start position
			// Refer to
			// https://leetcode.com/problems/flood-fill/
			// for(int k = 0; k < 4; k++) {
			//     dfs(i + dx[k], j + dy[k], image, visited, iniColor, newColor);
			// }
			int x = start_i;
			int y = start_j;
			// rolling until out or hit the wall
			while(x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] != 1) {
				x += dx[k];
				y += dy[k];
			}
			// one step back to the stop position
			x -= dx[k];
			y -= dy[k];
			// start a new dfs from the stop position
			if(helper(maze, visited, x, y, dest_i, dest_j)) {
				return true;
			}
		}
		return false;
	}
}
