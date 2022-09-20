/**
 * Refer to
 * https://leetcode.com/problems/search-a-2d-matrix-ii/description/
 * Write an efficient algorithm that searches for a value in an m x n matrix. 
   This matrix has the following properties:

    Integers in each row are sorted in ascending from left to right.
    Integers in each column are sorted in ascending from top to bottom.
    For example,

    Consider the following matrix:
    [
      [1,   4,  7, 11, 15],
      [2,   5,  8, 12, 19],
      [3,   6,  9, 16, 22],
      [10, 13, 14, 17, 24],
      [18, 21, 23, 26, 30]
    ]
    Given target = 5, return true.
    Given target = 20, return false.
 *
 * 
 * Solution
 * https://discuss.leetcode.com/topic/33240/java-an-easy-to-understand-divide-and-conquer-method?page=1
 * The coding seems to be much more complex than those smart methods such as this one, but the idea behind 
   is actually quite straightforward. Unfortunately, it is not as fast as the smart ones.

    First, we divide the matrix into four quarters as shown below:

      zone 1      zone 2
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    -----------------------
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
    *  *  *  * | *  *  *  *
      zone 3      zone 4
    We then compare the element in the center of the matrix with the target. There are three possibilities:
    center < target. In this case, we discard zone 1 because all elements in zone 1 are less than target.
    center > target. In this case, we discard zone 4.
    center == target. return true.

    For time complexity, if the matrix is a square matrix of size nxn, then for the worst case,
    T(nxn) = 3T(n/2 x n/2)
    which makes
    T(nxn) = O(n^log3)
 * 
 * https://discuss.leetcode.com/topic/33240/java-an-easy-to-understand-divide-and-conquer-method/3?page=1
*/
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        if(matrix.length == 1 && matrix[0].length == 1) {
            return matrix[0][0] == target;
        }
        return helper(matrix, target, 0, matrix.length - 1, 0, matrix[0].length - 1);
    }
    
    private boolean helper(int[][] matrix, int target, int rowStart, int rowEnd, int colStart, int colEnd) {
        // If not including these 2 conditions will cause StackOverFlow issue
        // E.g
        // Runtime Error Message:
        // Line 26: java.lang.StackOverflowError
        // Last executed input:
        // [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]] and target = 20
        if(rowStart < 0 || rowStart >= matrix.length || colStart < 0 || colStart >= matrix[0].length 
           || rowStart > rowEnd || colStart > colEnd) {
            return false;
        }
        int rowMid = rowStart + (rowEnd - rowStart) / 2;
        int colMid = colStart + (colEnd - colStart) / 2;
        if(matrix[rowMid][colMid] == target) {
            return true;
        // If center value > target, get rid of the lower-right corner of matrix, and continue
        // compute other 3 parts
        } else if(matrix[rowMid][colMid] > target) {
            return helper(matrix, target, rowStart, rowMid - 1, colStart, colMid - 1) ||
                   helper(matrix, target, rowStart, rowMid - 1, colMid, colEnd) ||
                   helper(matrix, target, rowMid, rowEnd, colStart, colMid - 1);
        // If center value < target, get rid of the upper-left corner of matrix, and continue
        // compute other 3 parts
        } else {
            return helper(matrix, target, rowMid + 1, rowEnd, colMid + 1, colEnd) ||
                   helper(matrix, target, rowMid + 1, rowEnd, colStart, colMid) ||
                   helper(matrix, target, rowStart, rowMid, colMid + 1, colEnd);
        }
    }
}






Wrong Binary Search solution (CANNOT directly use same code from L74 because in L240 we don't have "The first integer of each row is greater than the last integer of the previous row", and if flat 2D array into 1D array in L240, the 1D array is not strictly sorted in ascending, e.g [[1,4], [2,5]]  ==> [1,4,2,5])

```
// L74 standard Find Target Occurrence not applicable to L240
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

Solution 1: Similar to L74, use sorted matrix row + column strictly ascending property, start from bottom left corner to scan whole matrix  solution (10min, tricky Binary Search solution)
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

Solution 2: Divide and Conquer (360min, too long to identify corner case and constrains)

```
class Solution { 
    public boolean searchMatrix(int[][] matrix, int target) { 
        int rows = matrix.length; 
        int cols = matrix[0].length; 
        return helper(matrix, target, 0, rows - 1, 0, cols - 1); 
    } 
     
    private boolean helper(int[][] matrix, int target, int rowStart, int rowEnd, int colStart, int colEnd) { 
        // Base case 
        if(rowStart < 0 || rowStart >= matrix.length || colStart < 0 || colEnd >= matrix[0].length) { 
            return false; 
        } 
        // Q1: Why we need condition this condition ? 
        if(rowStart > rowEnd || colStart > colEnd) { 
            return false; 
        } 
        // Divide 
        // 1 | 2 
        // ----- 
        // 3 | 4 
        int rowMid = rowStart + (rowEnd - rowStart) / 2; 
        int colMid = colStart + (colEnd - colStart) / 2; 
        if(matrix[rowMid][colMid] == target) { 
            return true; 
        } else if(matrix[rowMid][colMid] > target) { 
            // Discard zone4 
            // Q2: Why zone2 boundary setup is different ? 
            boolean zone1 = helper(matrix, target, rowStart, rowMid - 1, colStart, colMid - 1); 
            boolean zone2 = helper(matrix, target, rowStart, rowMid - 1, colMid, colEnd); 
            boolean zone3 = helper(matrix, target, rowMid, rowEnd, colStart, colMid - 1); 
            // Conquer 
            return zone1 || zone2 || zone3; 
        } else { 
            // Discard zone1 
            boolean zone4 = helper(matrix, target, rowMid + 1, rowEnd, colMid + 1, colEnd); 
            boolean zone3 = helper(matrix, target, rowMid + 1, rowEnd, colStart, colMid); 
            boolean zone2 = helper(matrix, target, rowStart, rowMid, colMid + 1, colEnd); 
            // Conquer 
            return zone2 || zone3 || zone4; 
        } 
    } 
}

Space Complexity: O(1)        
Time Complexity: O((mnlog4(3))
The time complexity should be O((MN)log4(3)) just as what @StefanPochmann points out. The recursive equation of this solution is T(n) = 3T(n/4) + O(1) 
Then according to master method, any equation in form of T(n) = aT(n/b) + f(n) could fall into 3 case if it is solvable by master method. And T(n) = 3T(n/4) + O(1) matches exactly the 1st case, which is T(n)=Θ(n^logb(a)).
```

Refer to
https://leetcode.com/problems/search-a-2d-matrix-ii/discuss/66147/*Java*-an-easy-to-understand-divide-and-conquer-method
The coding seems to be much more complex than those smart methods such as this one, but the idea behind is actually quite straightforward. Unfortunately, it is not as fast as the smart ones.

First, we divide the matrix into four quarters as shown below:
```
  zone 1      zone 2
*  *  *  * | *  *  *  *
*  *  *  * | *  *  *  *
*  *  *  * | *  *  *  *
*  *  *  * | *  *  *  *
-----------------------
*  *  *  * | *  *  *  *
*  *  *  * | *  *  *  *
*  *  *  * | *  *  *  *
*  *  *  * | *  *  *  *
  zone 3      zone 4
```
We then compare the element in the center of the matrix with the target. There are three possibilities:
center < target. In this case, we discard zone 1 because all elements in zone 1 are less than target.
center > target. In this case, we discard zone 4.
center == target. return true.

For time complexity, if the matrix is a square matrix of size nxn, then for the worst case,
```
T(nxn) = 3T(n/2 x n/2)
```
which makes
```
T(nxn) = O(n^log3)
```

---
Q1: Why we need condition "if(rowStart > rowEnd || colStart > colEnd) { return  false;}" ?
```
-------------------------- 
matrix = [[1,4,7,11,15], 
          [2,5,8,12,19], 
          [3,6,9,16,22], 
          [10,13,14,17,24], 
          [18,21,23,26,30]] 
target = 5 
-------------------------- 
rowStart=0 
rowEnd=4 
colStart=0 
colEnd=4 
1,4  | 7,11,15 
2,5  | 8,12,19        1 | 2 
--------------  =>  --------- 
3,6  | 9,16,22        3 | 4 
10,13| 14,17,24 
18,21| 23,26,30 
-------------------------- 
Round 1: 
rowMid=2 
colMid=2 
m[2][2]=9 > target=5 -> discard zone4 of original matrix 
zone1=helper(m,5,0,1,0,1) 
zone2=helper(m,5,0,1,2,4) 
zone3=helper(m,5,2,4,0,1) 
We can see zone1 + zone2 + zone3 strictly fit together, no missing row or column 
1,4  | 7,11,15 
2,5  | 8,12,19 
-------------- 
3,6  | 
10,13| discard 
18,21| 
-------------------------- 
Round 2: 
let's focus on only zone1 after discard zone4, ignore branch of zone2 & zone3   
for now 
zone1=helper(m,5,0,1,0,1) 
rowMid=0 
colMid=0 
m[0][0]=1 < target=5 -> discard zone1 inside after Round 1's zone1 
zone4=helper(m,5,1,1,1,1) 
zone3=helper(m,5,1,1,0,0) 
zone2=helper(m,5,0,0,1,1) 
We can see zone2 + zone3 + zone4 strictly fit together, no missing row or column 
discard | 4 
------------ 
     2  | 5 
------------------------- 
Round 3: 
let's focus on only zone3 after discard zone1, ignore branch of zone3 & zone4   
for now (even the answer 5 is in zone4) 
zone3=helper(zone1,5,1,1,0,0) 
rowMid=1 
colMid=0 
m[1][0]=3 < target=5 -> discard zone4 inside after Round 2's zone3, but there is   
only 1 cell in Round 3 as '2' only, now if still go into discard zone4 branch: 
zone4=helper(m,5,2,1,1,0) -> when rowStart=2 > rowEnd=1 or colStart=1 >   
colEnd=0, both mean there is no such case, we should return false, otherwise   
(rowEnd - rowStart) or (colEnd - colStart) will become negative and no physical   
meaning, so we have to return false directly when rowStart > rowEnd or colStart   
> colEnd
```

Q2: Why zone2 boundary setup is different in > target OR < target branch ? 
In > target: boolean zone2 = helper(matrix, target, rowStart, rowMid - 1, colMid, colEnd);
In < target: boolean zone2 = helper(matrix, target, rowStart, rowMid, colMid + 1, colEnd);
```
Let's reuse Round 1 detail from Question 1: 
rowStart=0 
rowEnd=4 
colStart=0 
colEnd=4 
1,4  | 7,11,15 
2,5  | 8,12,19        1 | 2 
--------------  =>  --------- 
3,6  | 9,16,22        3 | 4 
10,13| 14,17,24 
18,21| 23,26,30 
-------------------------- 
Round 1: 
rowMid=2 
colMid=2 
m[2][2]=9 > target=5 -> discard zone4 of original matrix 
zone1=helper(matrix, target, rowStart, rowMid - 1, colStart, colMid - 1); 
zone2=helper(matrix, target, rowStart, rowMid - 1, colMid, colEnd); 
zone3=helper(matrix, target, rowMid, rowEnd, colStart, colMid - 1); 
zone1=helper(m,5,0,1,0,1) 
zone2=helper(m,5,0,1,2,4) 
zone3=helper(m,5,2,4,0,1) 
We can see zone1 + zone2 + zone3 strictly fit together, no missing row or column 
1,4  | 7,11,15 
2,5  | 8,12,19 
-------------- 
3,6  |[9] 
10,13| discard 
18,21| 
--------------------------- 
If we have a target=17 and want to discard zone1 based on equation: 
Round 1: 
rowMid=2 
colMid=2 
m[2][2]=9 < target=17 -> discard zone1 of original matrix 
zone4=helper(matrix, target, rowMid + 1, rowEnd, colMid + 1, colEnd); 
zone3=helper(matrix, target, rowMid + 1, rowEnd, colStart, colMid); 
zone2=helper(matrix, target, rowStart, rowMid, colMid + 1, colEnd); 
zone4=helper(m,17,3,4,3,4) 
zone3=helper(m,17,3,4,0,2) 
zone2=helper(m,17,0,2,3,4) 
We can see zone2 + zone3 + zone4 strictly fit together, no missing row or column 
         | 11,15  
 discard | 12,19 
      [9]| 16,22 
---------------- 
 10,13,14| 17,24 
 18,21,23| 26,30 
--------------------------- 
So the discipline is when we discard a zone, that zone must include m[rowMid]  
[colMid] element, because only remove it the remain part will include target,   
which means the discard zone will include colMid'th column and rowMid'th row
```
