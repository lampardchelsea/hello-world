/**
 * Refer to
 * http://www.lintcode.com/en/problem/triangle/
 * Given a triangle, find the minimum path sum from top to bottom. Each step you may move 
   to adjacent numbers on the row below.
   Notice
  Bonus point if you are able to do this using only O(n) extra space, where n is the total 
  number of rows in the triangle.
  Have you met this question in a real interview? Yes
  Example
  Given the following triangle:
  [
       [2],
      [3,4],
     [6,5,7],
    [4,1,8,3]
  ]
  The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 *
 * Solution
 * http://www.jiuzhang.com/solutions/triangle/
 * https://discuss.leetcode.com/topic/1669/dp-solution-for-triangle/2?page=1
 *
 */
public class Triangle {
	// Solution 1: bottom-up
	public int minimumTotal(int[][] triangle) {
	    if(triangle == null || triangle.length == 0) {
	    	return -1;
	    }
	    if(triangle[0].length == 0) {
	    	return -1;
	    }
	    // state: f[x][y] = minimum path value from x,y to bottom
	    int n = triangle.length;
	    int[][] f = new int[n][n];
	    // initialize (bottom row)
	    for(int i = 0; i < n; i++) {
	    	f[n - 1][i] = triangle[n - 1][i];
	    }
	    // bottom up function
	    for(int i = n - 2; i >= 0; i--) {
	    	// j <= i because other cell on current row no value assigned (= 0)
	    	for(int j = 0; j <= i; j++) {
	    		f[i][j] = Math.min(f[i + 1][j], f[i + 1][j + 1]) + triangle[i][j];
	    	}
	    }
	    // answer
	    return f[0][0];
	}
	
	// Solution 2: top-down
	public int minimumTotal_2(int[][] triangle) {
		if(triangle == null || triangle.length == 0) {
			return -1;
		}
		if(triangle[0].length == 0) {
			return -1;
		}
		// state: f[x][y] = minimum path value from 0,0 to x,y
		int n = triangle.length;
		int[][] f = new int[n][n];
		// initialize
		f[0][0] = triangle[0][0];
		for(int i = 1; i < n; i++) {
			// calculate two edges of triangle, why we need two edges value,
			// check video
			f[i][0] = f[i - 1][0] + triangle[i][0];
			f[i][i] = f[i - 1][i - 1] + triangle[i][i];
		}
		// top-down function
		for(int i = 1; i < n; i++) {
			// Caution: j < i as the condition
			for(int j = 1; j < i; j++) {
				f[i][j] = Math.min(f[i - 1][j], f[i - 1][j - 1]) + triangle[i][j];
			}
		}
		// answer
		int min = f[n - 1][0];
		for(int i = 1; i < n; i++) {
			min = Math.min(min, f[n - 1][i]);
		}
		return min;
	}
	
	
	// Solution 3: Memorize Search
	// Decrease Time Complexity from 2^N to N^2
	// Method 'search()' will only called twice in whole process for
	// each node (x, y) => from node (x + 1, y) and (x + 1, y + 1),
	// and the nodes number is 1 + 2 + 3 + 4.... as N^2, now the
	// total time complexity is 2 * (N^2) = O(N^2)
	private int[][] minSum;
	public int minimumTotal_3(int[][] triangle) {
		int n = triangle.length;
        minSum = new int[n][n];
        for(int i = 0; i < n; i++) {
        	for(int j = 0; j < n; j++) {
        		// Initialize all cell
        		minSum[i][j] = Integer.MAX_VALUE;
        	}
        }
        return search(triangle, 0, 0);
	}
	
	private int search(int[][] triangle, int x, int y) {
		if(x == triangle.length) {
			return 0;
		}
		// In traditional DFS traverse, there is no condition
		// here to stop go into next recursion, but here
		// besides base condition we also add a stop condition:
		// If we already got the minimum path from (x,y) to
		// bottom just return it
		if(minSum[x][y] != Integer.MAX_VALUE) {
			return minSum[x][y];
		}
	    // Set before return
		minSum[x][y] = Math.min(search(triangle, x + 1, y), search(triangle, x + 1, y + 1)) + triangle[x][y];
		return minSum[x][y];
	}
	
	// Solution 4: DFS (traverse solution) without optimization
	// TLE
    public int minPath;
    public int minimumTotal_4(int[][] triangle) {
        minPath = Integer.MAX_VALUE;
        traverse(triangle, 0, 0, 0);
        return minPath;
    }
    
    private void traverse(int[][] tri, int x, int y, int sum) {
        if(x == tri.length) {
            minPath = Math.min(minPath, sum);
            return;
        }
        // No condition here to stop go into next recursion,
        // which means (x,y) must go into next recursion 
        // (x + 1, y) and (x + 1, y + 1)
        traverse(tri, x + 1, y, sum + tri[x][y]);
        traverse(tri, x + 1, y + 1, sum + tri[x][y]);
    }
	
	public static void main(String[] args) {
		Triangle t = new Triangle();
		int[][] triangle = {{2},{3,4},{6,5,7},{4,1,8,3}};
		int result = t.minimumTotal_2(triangle);
		System.out.println(result);
	}
}
