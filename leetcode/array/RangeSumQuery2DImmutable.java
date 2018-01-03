/**
 * Refer to
 * https://leetcode.com/problems/range-sum-query-2d-immutable/description/
 * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper 
 * left corner (row1, col1) and lower right corner (row2, col2).

	Range Sum Query 2D
	The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and 
	(row2, col2) = (4, 3), which contains sum = 8.
	
	Example:
	Given matrix = [
	  [3, 0, 1, 4, 2],
	  [5, 6, 3, 2, 1],
	  [1, 2, 0, 1, 5],
	  [4, 1, 0, 1, 7],
	  [1, 0, 3, 0, 5]
	]
	
	sumRegion(2, 1, 4, 3) -> 8
	sumRegion(1, 1, 2, 2) -> 11
	sumRegion(1, 2, 2, 4) -> 12
	Note:
	You may assume that the matrix does not change.
	There are many calls to sumRegion function.
    You may assume that row1 ≤ row2 and col1 ≤ col2.
 * 
 * 
 * Solution
 * https://leetcode.com/articles/range-sum-query-2d-immutable/
 *
 */
public class RangeSumQuery2DImmutable {
    // Solution 1: Brute Force (Time Limit Exceed)
	/**
	 Complexity analysis
	 Time complexity : O(mn) time per query. Assume that mm and nn represents the number 
	                   of rows and columns respectively, each sumRegion query can go 
	                   through at most m \times nm×n elements.
	 Space complexity : O(1). Note that data is a reference to matrix and is 
	                    not a copy of it.
	*/
	int[][] data;
	public RangeSumQuery2DImmutable(int[][] matrix) {
        data = matrix;
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
    	int sum = 0;
        for(int i = row1; i <= row2; i++) {
        	for(int j = col1; j <= col2; j++) {
        		sum += data[i][j];
        	}
        }
        return sum;
    }
    
    
    // Solution 2: Cache row value
	// Refer to
	// https://leetcode.com/articles/range-sum-query-2d-immutable/
    /**
     * Intuition
     * Remember from the 1D version where we used a cumulative sum array? 
     * Could we apply that directly to solve this 2D version?
     * 
     * Algorithm
     * Try to see the 2D matrix as mm rows of 1D arrays. To find the region sum, 
     * we just accumulate the sum in the region row by row
     * 
     * Complexity analysis
     * Time complexity : O(m) time per query, O(mn) time pre-computation. 
     *                   The pre-computation in the constructor takes O(mn) time. 
     *                   The sumRegion query takes O(m) time.
     * Space complexity : O(mn). The algorithm uses O(mn) space to store the 
     *                    cumulative sum of all rows.                   
     */
    int[][] data2;
    public RangeSumQuery2DImmutable(int[][] matrix) {
    	int rows = matrix.length;
    	int cols = matrix[0].length;
    	data2 = new int[rows][cols + 1];
    	// Refer to Approach 3
    	// https://leetcode.com/articles/range-sum-query-immutable/
    	for(int i = 0; i < rows; i++) {
    		for(int j = 0; j < cols; j++) {
    			data2[i][j + 1] += data2[i][j] + matrix[i][j];
    		}
    	}
    }
    
    public int sumRegion2(int row1, int col1, int row2, int col2) {
    	int sum = 0;
    	for(int k = row1; k <= row2; k++) {
    		sum += (data2[k][col2] - data2[k][col1]);
    	}
    	return sum;
    }
    
    
    // Solution 3: Cache Smarter
    // Refer to
    // https://leetcode.com/articles/range-sum-query-2d-immutable/
    int[][] data3;
    public RangeSumQuery2DImmutable(int[][] matrix) {
    	if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
    		return;
    	}
    	int rows = matrix.length;
    	int cols = matrix[0].length;
        data3 = new int[rows + 1][cols + 1];
        for(int i = 0; i < rows; i++) {
        	for(int j = 0; j < cols; j++) {
        		data3[i + 1][j + 1] = data3[i + 1][j] + data3[i][j + 1] + matrix[i][j] - data3[i][j];
        	}
        }
    	printDPMatrix(data3);
    }
    
    private void printDPMatrix(int[][] dp) {
    	for(int i = 0; i < dp.length; i++) {
    		System.out.println("");
    		for(int j = 0; j < dp[0].length; j++) {
    			System.out.print(dp[i][j] + " ");
    		}
    	}
    }
    
    public int sumRegion3(int row1, int col1, int row2, int col2) {
    	return data3[row2 + 1][col2 + 1] - data3[row1][col2 + 1] - data3[row2 + 1][col1] + data3[row1][col1]; 
    }
    
    public static void main(String[] args) {    	
    	int[][] matrix = new int[][]{{3, 0, 1, 4, 2},
                                     {5, 6, 3, 2, 1},
                                     {1, 2, 0, 1, 5},
                                     {4, 1, 0, 1, 7},
                                     {1, 0, 3, 0, 5}};
    	for(int i = 0; i < matrix.length; i++) {
    		System.out.println("");
    		for(int j = 0; j < matrix[0].length; j++) {
    			System.out.print(matrix[i][j] + " ");
    		}
    	}
    	System.out.println("");
    	System.out.println("------------");
    	RangeSumQuery2DImmutable r = new RangeSumQuery2DImmutable(matrix);
    	int row1 = 2;
    	int col1 = 1;
    	int row2 = 4;
    	int col2 = 3;
//    	int row1 = 1;
//    	int col1 = 2;
//    	int row2 = 2;
//    	int col2 = 4;
    	
    	int result = r.sumRegion3(row1, col1, row2, col2);
    	System.out.println("");
    	System.out.println("----Result----");
    	System.out.println(result);
    }
}
