/**
 Refer to
 https://leetcode.com/problems/spiral-matrix/submissions/
 Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

Example 1:

Input:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
Output: [1,2,3,6,9,8,7,4,5]
Example 2:

Input:
[
  [1, 2, 3, 4],
  [5, 6, 7, 8],
  [9,10,11,12]
]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
*/
// Solution 1:
// Refer to
// https://leetcode.com/problems/spiral-matrix/discuss/20599/Super-Simple-and-Easy-to-Understand-Solution
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<Integer>();
        if(matrix == null || matrix.length == 0) {
            return result;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int rowBegin = 0;
        int rowEnd = rows - 1;
        int colBegin = 0;
        int colEnd = cols - 1;
        while(rowBegin <= rowEnd && colBegin <= colEnd) {
            // Traverse right
            for(int j = colBegin; j <= colEnd; j++) {
                result.add(matrix[rowBegin][j]);
            }
            rowBegin++;
            // Traverse down
            for(int j = rowBegin; j <= rowEnd; j++) {
                result.add(matrix[j][colEnd]);
            }
            colEnd--;
            // The only tricky part is that when traverse left or up check 
            // whether the row or col still exists to prevent duplicates.
            // e.g
            // input: [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
            // output: [1,2,3,4,8,12,11,10,9,5,6,7,6]
            // expected: [1,2,3,4,8,12,11,10,9,5,6,7]
            if(rowBegin <= rowEnd) {
                // Traverse Left
                for(int j = colEnd; j >= colBegin; j--) {
                    result.add(matrix[rowEnd][j]);
                }
            }
            rowEnd--;            
            if(colBegin <= colEnd) {
                // Traver Up
                for(int j = rowEnd; j >= rowBegin; j--) {
                    result.add(matrix[j][colBegin]);
                }
            }
            colBegin++;
        }
        return result;
    }
}

// Solution 2:
// Refer to
// https://leetcode.com/problems/spiral-matrix/discuss/20599/Super-Simple-and-Easy-to-Understand-Solution/185257
// AN INTERVIEW FRIENDLY SOLUTION
// the conditions to check borders are all the same.
// res.size() < n * m
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new LinkedList<>(); 
        if (matrix == null || matrix.length == 0) return res;
        int n = matrix.length, m = matrix[0].length;
        int up = 0,  down = n - 1;
        int left = 0, right = m - 1;
        while (res.size() < n * m) {
            for (int j = left; j <= right && res.size() < n * m; j++)
                res.add(matrix[up][j]);
            
            for (int i = up + 1; i <= down - 1 && res.size() < n * m; i++)
                res.add(matrix[i][right]);
                     
            for (int j = right; j >= left && res.size() < n * m; j--)
                res.add(matrix[down][j]);
                        
            for (int i = down - 1; i >= up + 1 && res.size() < n * m; i--) 
                res.add(matrix[i][left]);
                
            left++; right--; up++; down--; 
        }
        return res;
    }
}




import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/spiral-matrix/#/description
 * Given a matrix of m x n elements (m rows, n columns), return all elements of 
 * the matrix in spiral order.
 * For example,
	Given the following matrix:
	
	[
	 [ 1, 2, 3 ],
	 [ 4, 5, 6 ],
	 [ 7, 8, 9 ]
	]
 * You should return [1,2,3,6,9,8,7,4,5]. 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003817711
 * 顺序添加法
 * 复杂度
 * 时间 O(NM) 空间 O(1)
 * 思路
 * 首先考虑最简单的情况，如图我们先找最外面这圈X，这种情况下我们是第一行找前4个，最后一列找前4个，
 * 最后一行找后4个，第一列找后4个，这里我们可以发现，第一行和最后一行，第一列和最后一列都是有对应
 * 关系的。即对i行，其对应行是m - i - 1，对于第j列，其对应的最后一列是n - j - 1。
 * 
	XXXXX
	XOOOX
	XOOOX
	XOOOX
	XXXXX

 * 然后进入到里面那一圈，同样的顺序没什么问题，然而关键在于下图这么两种情况，一圈已经没有四条边了，
 * 所以我们要单独处理，只加那唯一的一行或一列。另外，根据下图我们可以发现，圈数是宽和高中较小的那个，
 * 加1再除以2。

	OOOOO  OOO
	OXXXO  OXO
	OOOOO  OXO
	       OXO
	       OOO
 */
public class SpiralMatrix {
	public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<Integer>();
        int m = matrix.length;
        if(m == 0) {
        	return result;
        }
        int n = matrix[0].length;
        // Calculate how many rounds
        int lvl = (Math.min(m, n) + 1) / 2;
        for(int i = 0; i < lvl; i++) {
        	// Calculate last row of current round
        	int lastRow = m - i - 1;
        	// Calculate last column of current round
        	int lastCol = n - i - 1;
        	// If first row of current round already
        	// the last row, means only left one row
        	if(i == lastRow) {
        		// Be careful, j <= lastCol contains '='
        		for(int j = i; j <= lastCol; j++) {
        			result.add(matrix[i][j]);
        		}
        	// If first column of current round already
        	// the last column, means only left one column
        	} else if(i == lastCol) {
        		// Be careful, j <= lastRow contains '='
        		for(int j = i; j <= lastRow; j++) {
        			result.add(matrix[j][i]);
        		}
        	} else {
        	    // Important: Spin order
        	    // first row(left to right) -> last column(up to bottom)
        	    // -> last row(right to left) -> first column(bottom to up)
        		// The first row of current round (i represented)
        		for(int j = i; j < lastCol; j++) {
        			result.add(matrix[i][j]);
        		}
        		// The last column of current round (lastCol = n - i - 1 represented)
        		for(int j = i; j < lastRow; j++) {
        			result.add(matrix[j][lastCol]);
        		}
        		// The last row of current round
        		// Important: Must change the adding
        		// direction -> right to left (lastRow = m - i - 1 represented)
        		for(int j = lastCol; j > i; j--) {
        			result.add(matrix[lastRow][j]);
        		}
        		// The first column of current round
        		// Important: Must change the adding
        		// direction -> bottom to up (i represented)
        		for(int j = lastRow; j > i; j--) {
        			result.add(matrix[j][i]);
        		}
        	}
        }
        return result;
    }
	
	public static void main(String[] args) {
		int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8 ,9}};
		SpiralMatrix s = new SpiralMatrix();
		List<Integer> result = s.spiralOrder(matrix);
		for(Integer i : result) {
			System.out.println(i);
		}
	}
}
