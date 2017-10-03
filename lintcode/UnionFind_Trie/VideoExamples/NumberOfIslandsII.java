import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-434-number-of-islands-ii.html
 * Description
	Given a n,m which means the row and column of the 2D matrix and an array of pair 
	A( size k). Originally, the 2D matrix is all 0 which means there is only sea in 
	the matrix. The list pair has k operator and each operator has two integer A[i].x, 
	A[i].y means that you can change the grid matrix[A[i].x][A[i].y] from sea to island. 
	Return how many island are there in the matrix after each operator.
	
	Notice
	0 is represented as the sea, 1 is represented as the island. If two 1 is adjacent, 
	we consider them in the same island. We only consider up/down/left/right adjacent.
	
	Example
	Given n = 3, m = 3, array of pair A = [(0,0),(0,1),(2,2),(2,1)].
	
	return [1,1,2,2].
 * 
 * 
 * Solution
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-434-number-of-islands-ii.html
 * 用union-find做。
 * 遍历operators。每进来一个点，先判断一下这个点有没有visit过。如果已经找过了，那么总的岛屿数量不变。
 * 如果没有找过，先把岛屿数量加一。然后看一下这个点的上下左右有没有已经visit过的点。如果有，那就和当前这个点union起来。
 * 并且把岛屿的数量减一。
 * 
 * https://discuss.leetcode.com/topic/29613/easiest-java-solution-with-explanations
 * This is a basic union-find problem. Given a graph with points being added, we can at least solve:

	How many islands in total?
	Which island is pointA belonging to?
	Are pointA and pointB connected?
	The idea is simple. To represent a list of islands, we use trees. i.e., a list of roots. 
	This helps us find the identifier of an island faster. If roots[c] = p means the parent 
	of node c is p, we can climb up the parent chain to find out the identifier of an island, 
	i.e., which island this point belongs to:
	
	Do root[root[roots[c]]]... until root[c] == c;
	To transform the two dimension problem into the classic UF, perform a linear mapping:
	
	int id = n * x + y;
	Initially assume every cell are in non-island set {-1}. When point A is added, we create 
	a new root, i.e., a new island. Then, check if any of its 4 neighbors belong to the same 
	island. If not, union the neighbor by setting the root to be the same. Remember to skip 
	non-island cells.
	
	UNION operation is only changing the root parent so the running time is O(1).
	
	FIND operation is proportional to the depth of the tree. If N is the number of points added, 
	the average running time is O(logN), and a sequence of 4N operations take O(NlogN). If there 
	is no balancing, the worse case could be O(N^2).
	
	Remember that one island could have different roots[node] value for each node. Because roots[node] 
	is the parent of the node, not the highest root of the island. To find the actually root, 
	we have to climb up the tree by calling findIsland function.
	
	Here I've attached my solution. There can be at least two improvements: union by rank & path 
	compression. However I suggest first finish the basis, then discuss the improvements.
	
 * https://github.com/awangdev/LintCode/blob/master/Java/Number%20of%20Islands%20II.java
 * http://www.cnblogs.com/yrbbest/p/5050749.html
 * 又是一道Union Find的经典题。这道题代码主要参考了yavinci大神。风格还是princeton Sedgewick的那一套。
 * 这里我们可以把二维的Union-Find映射为一维的Union Find。使用Quick-Union就可以完成。但这样的话
 * Time Complexity是O(kmn)。 想要达到O(klogmn)的话可能还需要使用Weighted-Quick Union配合path compression。二刷一定要实现。
 * Time Complexity - O(mn * k)， Space Complexity - O(mn)
 * 二刷:
 * 加入了Path compression以及Weight， 速度快了不少。
 * Time Complexity - (k * logmn)  Space Complexity - O(mn),  这里k是positions的长度
 */
public class NumberOfIslandsII {
	private class UnionFind {
		private int[] father;
		
		public UnionFind(int n) {
			father = new int[n + 1];
			for(int i = 1; i < n + 1; i++) {
				father[i] = i;
			}
		}
		
		//TODO: Change to while style
		/**
		 * Refer to
		 * https://discuss.leetcode.com/topic/29613/easiest-java-solution-with-explanations/2
		 * PATH COMPRESSION (BONUS)
			If you have time, add one line to shorten the tree. 
			The new runtime becomes: 19ms (95.94%).			
			public int find(int[] roots, int id) {
			    while(id != roots[id]) {
			        roots[id] = roots[roots[id]];   // only one line added
			        id = roots[id];
			    }
			    return id;
			}
		 */
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
			}
		}
	}
	
	// O(mn * k) k is length of positions
	public List<Integer> numIslands2(int m, int n, int[][] positions) {
		List<Integer> result = new ArrayList<Integer>();
		if(positions == null || positions.length == 0) {
			return result;
		}
		if(positions[0].length == 0) {
			return result;
		}
		int[] isLand = new int[m * n];
	    UnionFind u = new UnionFind(m * n);
	    int count = 0;
		int[] dx = {-1, 0, 0, 1};
	    int[] dy = {0, 1, -1, 0};
	    for(int i = 0; i < positions.length; i++) {
	    	int x = positions[i][0];
	    	int y = positions[i][1];
	    	count++;
	    	int pos = x * m + y;
	    	if(isLand[pos] != 1) {
	    		isLand[pos] = 1;
	    		// O(4 * k) --> O(k)
	    		for(int j = 0; j < 4; j++) {
	    			int next_x = x + dx[j];
	    			int next_y = y + dy[j];
	    			int newPos = next_x * m + next_y;
	    			if(next_x >= 0 && next_x < n && next_y >= 0 && next_y < m && isLand[newPos] == 1) {
	    				// UNION operation is only changing the root parent so the running time is O(1).
	    				u.connect(pos, newPos);
	    				count--;
	    			}
	    		}
	    	}
	    	result.add(count);
	    }
	    return result;
	}
	
	public static void main(String[] args) {
		NumberOfIslandsII numberOfIslandsII = new NumberOfIslandsII();
		int[][] positions = {{0,0},{0,1},{2,2},{2,1}};
		int m = 3, n = 3;
	    List<Integer> result = numberOfIslandsII.numIslands2(m, n, positions);
	    for(Integer i : result) {
	    	System.out.print(i + " ");
	    }
	}
	
}
