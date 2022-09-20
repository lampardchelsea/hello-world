/**
 * Refer to
 * http://www.lintcode.com/en/problem/search-a-2d-matrix/
 * Write an efficient algorithm that searches for a value in an m x n matrix.
 * This matrix has the following properties:
    Integers in each row are sorted from left to right.
    The first integer of each row is greater than the last integer of the previous row.
 * Have you met this question in a real interview?
    Example
    Consider the following matrix:
    [
        [1, 3, 5, 7],
        [10, 11, 16, 20],
        [23, 30, 34, 50]
    ]
 * Given target = 3, return true.
 *
 * Solution
 * Refer to
 * http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-240-Search-a-2D-Matrix-II
 * 思路
 * 这题的最初想法是用 binary search，这种方法肯定也是可行的，但是没有完全利用到这个 matrix 的两个特性。
 * 先说说 binary search
 * 这题说每行从右到左都是排好序的，那么我们就可以排除掉那些超出目标范围的行，然后对剩下的每行进行 binary search 就可以了。
 * Divide and Conqure
 * 这题 divide and conqure 的做法非常有意思，我是看了九章的小视频学会的，但是真是非常机智，我觉得临场写的话除非很擅长这种智力题不然是很难想到的。。
 * 方法的实现就是利用了这个 matrix 两个特性，即每行每列都是递增的。
 * 假设我们从最左下角的点开始看，这个点就是当前列的最大值，还有当前行的最小值，那么如果这个点比 target 要大，那么我们是不是就可以把整行都给排除了？
 * 如果这个点比 target 要小，我们可以把整个列都排除，所以我们就可以用一个 while 循环，来从左下角开始比较，直到我们目标找到或者
 * 整个 matrix 都排除完为止。
 * 复杂度分析
 * 时间 n: height, m: width
 * binary search: O(nlogm), O(log(nm))
    多次 binary search每次 search 是logm, 总共可能做 n 次 search
    单次 binary search search 总共n*m个 element
 * Divide & Conqure O(m+n)
 * 如果整个 matrix 都要排除，那么我们就是走完所有的 width 还有走完所有的 height，每次做 O(1)
 * 空间 O(1)
 * 没有使用额外空间
 * 总结
 * 这题一开始没想到 divide and conquere 做法，但是真是非常有意思。 还是需要多练习这种智力题，增加经验。
 * 
 * 
*/
public class Solution {
    /**
     * @param matrix, a list of lists of integers
     * @param target, an integer
     * @return a boolean, indicate whether matrix contains target
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        // Check null and empty case
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int height = matrix.length;
        int width = matrix[0].length;
        int start = 0;
        int end = height * width - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            int rowNum = mid / width;
            int colNum = mid % width;
            if(matrix[rowNum][colNum] == target) {
                return true;
            } else if(matrix[rowNum][colNum] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if(matrix[start / width][start % width] == target || 
           matrix[end / width][end % width] == target) {
           return true; 
        }
        return false;
    }

    public boolean searchMatrix_Divide_Conqure(int[][] matrix, int target) {
        // Check null and empty case
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int height = matrix.length;
        int width = matrix[0].length;
        int col = 0;
        int row = matrix.length - 1;
        // Divide and Conqure
        // 这题 divide and conqure的法非常有意思，我是看了九章的小视频学会的，但是真是非常机智，我觉得临场写的话除非
        // 很擅长这种智力题不然是很难想到的。。 方法的实现就是利用了这个 matrix 两个特性，即每行每列都是递增的。
        // 假设我们从最左下角的点开始看，这个点就是当前列的最大值，还有当前行的最小值，那么如果这个点比 
        // target要大，那么我们是不是就可以把整行都给排除了？
        // 如果这个点比 target 要小，我们可以把整个列都排除，所以我们就可以用一个 while 
        // 循环，来从左下角开始比较，直到我们目标找到或者整个 matrix 都排除完为止。
        while(col < width && row >= 0) {
            if(matrix[row][col] == target) {
                return true;
            } else if(matrix[row][col] < target) {
                col++;
            } else {
                row--;
            }
        }
        return false;
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
