/**
 * Refer to
 * https://leetcode.com/problems/spiral-matrix-ii/#/description
 * Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
	For example,
	Given n = 3,
	You should return the following matrix:
	
	[
	 [ 1, 2, 3 ],
	 [ 8, 9, 4 ],
	 [ 7, 6, 5 ]
	]
 *
 * Solution
 * https://segmentfault.com/a/1190000003817711
 * 顺序添加法
 * 复杂度
 * 时间 O(NM) 空间 O(1)
 * 思路
 * 本题就是按照螺旋的顺序把数字依次塞进去，我们可以维护上下左右边界的四个变量，一圈一圈往里面添加。
 * 最后要注意的是，如果n是奇数，要把中心那个点算上。
 */
public class SpiralMatrixII {
	public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        int left = 0, right = n - 1, bottom = n - 1, top = 0;
        int num = 1;
        while(left < right && top < bottom) {
        	// Add first row of current round
        	for(int i = left; i < right; i++) {
        		result[top][i] = num++;
        	}
        	// Add last column of current round
        	for(int i = top; i < bottom; i++) {
        		result[i][right] = num++;
         	}
        	// Add last row of current round
        	for(int i = right; i > left; i--) {
        		result[bottom][i] = num++;
        	}
        	// Add first column of current round
        	for(int i = bottom; i > top; i--) {
        		result[i][left] = num++;
        	}
        	// Prepare for possible second round
        	top++;
        	bottom--;
        	left++;
        	right--;
        }
        // Important: If given odd number, then add center point
        if(n % 2 == 1) {
        	result[n / 2][n / 2] = num;
        }
        return result;
    }
	 
	public static void main(String[] args) {
		int n = 3;
		SpiralMatrixII s = new SpiralMatrixII();
		int[][] result = s.generateMatrix(n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.println(result[i][j]);
			}
		}
	}
}






























https://leetcode.com/problems/spiral-matrix-ii/description/
Given a positive integer n, generate an n x n matrix filled with elements from 1 to n2 in spiral order.
 
Example 1:

Input: n = 3
Output: [[1,2,3],[8,9,4],[7,6,5]]

Example 2:
Input: n = 1
Output: [[1]]
 
Constraints:
- 1 <= n <= 20
--------------------------------------------------------------------------------
Attempt 1: 2025-05-26
Solution 1: Array (10 min)
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        int top = 0;
        int right = n - 1;
        int bottom = n - 1;
        int left = 0;
        int num = 1;
        while(left <= right && top <= bottom) {
            for(int i = left; i <= right; i++) {
                result[top][i] = num;
                num++;
            }
            top++;
            for(int j = top; j <= bottom; j++) {
                result[j][right] = num;
                num++;
            }
            right--;
            if(top <= bottom) {
                for(int i = right; i >= left; i--) {
                    result[bottom][i] = num;
                    num++;
                }
                bottom--;
            }
            if(left <= right) {
                for(int j = bottom; j >= top; j--) {
                    result[j][left] = num;
                    num++;
                }
                left++;
            }
        }
        return result;
    }
}

Refer to QWEN
Here's an efficient and well-commented Java solution for LeetCode 59: Spiral Matrix II:
class Solution {
    public int[][] generateMatrix(int n) {
        // Create n x n matrix initialized with zeros
        int[][] matrix = new int[n][n];
        
        // Boundary pointers
        int top = 0, bottom = n - 1;
        int left = 0, right = n - 1;
        int num = 1; // Start filling from 1
        
        // Fill matrix in spiral order until all cells are populated
        while (num <= n * n) {
            // 1. Left to right along top row
            for (int col = left; col <= right; col++) {
                matrix[top][col] = num++;
            }
            top++; // Move top boundary down
            
            // 2. Top to bottom along right column
            for (int row = top; row <= bottom; row++) {
                matrix[row][right] = num++;
            }
            right--; // Move right boundary left
            
            // 3. Right to left along bottom row (if still valid)
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    matrix[bottom][col] = num++;
                }
                bottom--; // Move bottom boundary up
            }
            
            // 4. Bottom to top along left column (if still valid)
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    matrix[row][left] = num++;
                }
                left++; // Move left boundary right
            }
        }
        
        return matrix;
    }
}
Key Insights:
1.Boundary Management: Four pointers (top, bottom, left, right) dynamically shrink as we complete each spiral layer.
2.Directional Traversal:
- → Left to right (top row)
- ↓ Top to bottom (right column)
- ← Right to left (bottom row)
- ↑ Bottom to top (left column)
3.Boundary Checks: Critical after first two directions to avoid overwriting when spiraling inward (especially for odd n).
Complexity Analysis:
- Time Complexity: O(n²) — Each cell is filled exactly once
- Space Complexity: O(n²) — For the output matrix (excluding output, O(1) extra space)
Example Walkthrough (n = 3):
Initial boundaries: top=0, bottom=2, left=0, right=2

Step 1 (→): Fill top row [0][0..2] → [1, 2, 3]
            top becomes 1
Step 2 (↓): Fill right col [1..2][2] → [4, 5]
            right becomes 1
Step 3 (←): Fill bottom row [2][1..0] → [6, 7]
            bottom becomes 1
Step 4 (↑): Fill left col [1][0] → [8]
            left becomes 1
Step 5 (→): Fill remaining [1][1] → [9]

Result:
[1, 2, 3]
[8, 9, 4]
[7, 6, 5]
Alternative Direction-Based Approach (Using Direction Vectors):
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int[] dr = {0, 1, 0, -1}; // row direction: right, down, left, up
        int[] dc = {1, 0, -1, 0}; // col direction
        int r = 0, c = 0, dir = 0; // start at (0,0), facing right
        
        for (int num = 1; num <= n * n; num++) {
            matrix[r][c] = num;
            
            // Calculate next position
            int nr = r + dr[dir];
            int nc = c + dc[dir];
            
            // Change direction if next position is out of bounds or already filled
            if (nr < 0 || nr >= n || nc < 0 || nc >= n || matrix[nr][nc] != 0) {
                dir = (dir + 1) % 4;
                nr = r + dr[dir];
                nc = c + dc[dir];
            }
            r = nr;
            c = nc;
        }
        return matrix;
    }
}
This approach uses direction vectors and checks for boundaries/filled cells to change direction. Slightly more elegant but requires checking cell values.
Test Cases:
// n = 1
[[1]]

// n = 2
[[1, 2],
 [4, 3]]

// n = 4
[[ 1,  2,  3,  4],
 [12, 13, 14,  5],
 [11, 16, 15,  6],
 [10,  9,  8,  7]]
Both implementations correctly generate the spiral matrix. The boundary-pointer approach is generally preferred for its clarity and explicit control over traversal boundaries.

Refer to Deepseek
Here's a Java solution for LeetCode 59 - Spiral Matrix II using boundary simulation:
Problem Summary
Given a positive integer n, generate an n x n matrix filled with elements from 1 to n² in spiral order.
Solution
Approach 1: Layer-by-Layer Simulation
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        
        int top = 0, bottom = n - 1;
        int left = 0, right = n - 1;
        int num = 1;
        
        while (top <= bottom && left <= right) {
            // Fill top row from left to right
            for (int i = left; i <= right; i++) {
                matrix[top][i] = num++;
            }
            top++;
            
            // Fill right column from top to bottom
            for (int i = top; i <= bottom; i++) {
                matrix[i][right] = num++;
            }
            right--;
            
            // Fill bottom row from right to left
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    matrix[bottom][i] = num++;
                }
                bottom--;
            }
            
            // Fill left column from bottom to top
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    matrix[i][left] = num++;
                }
                left++;
            }
        }
        
        return matrix;
    }
}
Approach 2: Direction Simulation (Cleaner)
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        
        // Define directions: right, down, left, up
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        int row = 0, col = 0;
        int dirIndex = 0; // Start moving right
        int num = 1;
        
        while (num <= n * n) {
            matrix[row][col] = num++;
            
            // Calculate next position
            int nextRow = row + dirs[dirIndex][0];
            int nextCol = col + dirs[dirIndex][1];
            
            // If next position is out of bounds or already filled, change direction
            if (nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= n || 
                matrix[nextRow][nextCol] != 0) {
                dirIndex = (dirIndex + 1) % 4; // Change direction
                nextRow = row + dirs[dirIndex][0];
                nextCol = col + dirs[dirIndex][1];
            }
            
            row = nextRow;
            col = nextCol;
        }
        
        return matrix;
    }
}
Approach 3: Optimized Boundary Traversal
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        
        int rowStart = 0, rowEnd = n - 1;
        int colStart = 0, colEnd = n - 1;
        int num = 1;
        
        while (rowStart <= rowEnd && colStart <= colEnd) {
            // Top row
            for (int col = colStart; col <= colEnd; col++) {
                matrix[rowStart][col] = num++;
            }
            rowStart++;
            
            // Right column
            for (int row = rowStart; row <= rowEnd; row++) {
                matrix[row][colEnd] = num++;
            }
            colEnd--;
            
            // Bottom row
            if (rowStart <= rowEnd) {
                for (int col = colEnd; col >= colStart; col--) {
                    matrix[rowEnd][col] = num++;
                }
            }
            rowEnd--;
            
            // Left column
            if (colStart <= colEnd) {
                for (int row = rowEnd; row >= rowStart; row--) {
                    matrix[row][colStart] = num++;
                }
            }
            colStart++;
        }
        
        return matrix;
    }
}
Explanation
Time Complexity: O(n²)
- We visit each cell exactly once
- Each cell operation is O(1)
Space Complexity: O(1)
- We only use constant extra space (excluding the output matrix)
Key Points:
1.Spiral Pattern:
- Move right → down → left → up → repeat
- After completing each row/column, adjust boundaries
2.Boundary Management:
- Keep track of top, bottom, left, right
- After filling each side, shrink the boundaries
- Check if boundaries are still valid before filling inner layers
3.Termination Condition:
- The loop continues while top <= bottom && left <= right
- This ensures we fill all cells exactly once
4.Direction Changes:
- When we hit a boundary or a filled cell, change direction
- This is handled automatically by our boundary checks
Visual Example (n = 3)
Step-by-step filling:
Initial:      After 1st row:  After 1st col:  After 2nd row:  Final:
[0, 0, 0]     [1, 2, 3]      [1, 2, 3]      [1, 2, 3]      [1, 2, 3]
[0, 0, 0]     [0, 0, 0]      [0, 0, 4]      [8, 9, 4]      [8, 9, 4]
[0, 0, 0]     [0, 0, 0]      [0, 0, 0]      [7, 6, 5]      [7, 6, 5]

Sequence: 1→2→3→4→5→6→7→8→9
Edge Cases
// Test Cases
n = 1 → [[1]]
n = 2 → [[1, 2], [4, 3]]
n = 4 → [[1, 2, 3, 4], [12, 13, 14, 5], [11, 16, 15, 6], [10, 9, 8, 7]]
The first approach (boundary simulation) is generally the most intuitive and easiest to understand. It clearly shows the spiral pattern by tracking boundaries and filling one layer at a time.


Refer to
L54.Spiral Matrix (Ref.L59,L885,L2326)
L885.Spiral Matrix III (Ref.L54,L59,L2326)
L2326.Spiral Matrix IV (Ref.L54,L59,L2326)
