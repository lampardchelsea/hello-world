// Refer to
// https://leetcode.com/problems/search-a-2d-matrix/#/description
/**
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 * For example,
 * Consider the following matrix:
  [
    [1,   3,  5,  7],
    [10, 11, 16, 20],
    [23, 30, 34, 50]
  ]
 * Given target = 3, return true.
*/

// Solution
// Refer to
// https://discuss.leetcode.com/topic/4846/binary-search-on-an-ordered-matrix
// https://discuss.leetcode.com/topic/3227/don-t-treat-it-as-a-2d-matrix-just-treat-it-as-a-sorted-list
/**
 * Use binary search.
 * n * m matrix convert to an array => matrix[x][y] => a[x * m + y]
 * an array convert to n * m matrix => a[x] =>matrix[x / m][x % m];
*/
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        if(rows == 0) {
            return false;
        }
        int columns = matrix[0].length;
        int lo = 0;
        int hi = rows * columns - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo)/2;
            int midValue = matrix[mid / columns][mid % columns];
            if(midValue == target) {
                return true;
            } else if(midValue > target) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return false;
    }
}


Attempt 1: 2022-09-18 

Solution 1: Binary Search solution (10min, important on 1D array transfer to matrix 2D array with 'mid_row = mid / cols' and 'mid_col = mid % cols')

```
class Solution { 
    public boolean searchMatrix(int[][] matrix, int target) { 
        int rows = matrix.length; 
        int cols = matrix[0].length; 
        int lo = 0; 
        int hi = rows * cols - 1; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            int mid_row = mid / cols; 
            int mid_col = mid % cols; 
            if(matrix[mid_row][mid_col] == target) { 
                return true; 
            } else if(matrix[mid_row][mid_col] > target) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
        return false; 
    } 
}

Space Complexity: O(1)      
Time Complexity: O(logmn)
```

Solution 2: Divide and Conquer solution (30min, tricky solution)

```
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int row = matrix.length - 1;
        int col = 0;
        while(col < cols && row >= 0) {
            if(matrix[row][col] == target) {
                return true;
            } else if(matrix[row][col] > target) {
                row--;
            } else {
                col++;
            }
        }
        return false;
    }
}

Space Complexity: O(1)      
Time Complexity: O(m + n)
```

Refer to
https://leetcode.com/problems/search-a-2d-matrix/discuss/1500429/Search-a-2D-Matrix-C%2B%2B-or-O(m%2Bn)-Time-Complexity
这题 divide and conqure的法非常有意思，我是看了九章的小视频学会的，但是真是非常机智，我觉得临场写的话除非很擅长这种智力题不然是很难想到的。。 方法的实现就是利用了这个 matrix 两个特性，即每行每列都是递增的。假设我们从最左下角的点开始看，这个点就是当前列的最大值，还有当前行的最小值，那么如果这个点比target要大，那么我们是不是就可以把整行都给排除了？如果这个点比 target 要小，我们可以把整个列都排除，所以我们就可以用一个 while 循环，来从左下角开始比较，直到我们目标找到或者整个 matrix 都排除完为止
```
class Solution {
public:
    bool searchMatrix(vector<vector<int>>& matrix, int target) 
    {
	// m = total rows 
	// n = total columns 
        int m = matrix.size();
        int n = matrix[0].size();
        
	// Start from top-right corner  
        int row = 0, col = n-1;
        
	// Traverse the matrix 
        while(row < m && col >= 0)
        {
	    // If target found 
            if(matrix[row][col] == target)
                return true;
            
	    // If target is smaller than current element - decrement col  
            if(matrix[row][col] > target)
                col--;
				
	    // If target is greater than current element - increment row 
            else
                row++;
        }
        
	// Target not found 
        return false;
    }
};

O(m+n) Time Complexity
```
