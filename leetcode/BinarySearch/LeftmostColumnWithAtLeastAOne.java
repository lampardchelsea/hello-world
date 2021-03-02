/**
Refer to
https://medium.com/@sumukhballal/leftmost-column-with-at-least-a-one-problem-leetcode-57361bb66bf6
(This problem is an interactive problem.)

A binary matrix means that all elements are 0 or 1. For each individual row of the matrix, this row is sorted in non-decreasing order.

Given a row-sorted binary matrix binaryMatrix, return leftmost column index(0-indexed) with at least a 1 in it. 
If such index doesn't exist, return -1.

You can't access the Binary Matrix directly.  You may only access the matrix using a BinaryMatrix interface:

BinaryMatrix.get(x, y) returns the element of the matrix at index (x, y) (0-indexed).
BinaryMatrix.dimensions() returns a list of 2 elements [m, n], which means the matrix is m * n.
Submissions making more than 1000 calls to BinaryMatrix.get will be judged Wrong Answer.  Also, any solutions that attempt 
to circumvent the judge will result in disqualification.

For custom testing purposes you're given the binary matrix mat as input in the following four examples. 
You will not have access the binary matrix directly.
*/

// Solution 1: Binary Search
// Refer to
// https://medium.com/@sumukhballal/leftmost-column-with-at-least-a-one-problem-leetcode-57361bb66bf6

// https://snowan.gitbook.io/study-notes/leetcode/30daychallenge/leftmost-column-with-at-least-a-one
/**
Solution #1 - Binary Search
BinaryMatrix row is sorted, and find left most 1, binary search in O(logN), record each row left most column index with 1, 
each row do binary search, and compare the left most column, keep track of min column number. after iterate through all rows, return min.
Complexity Analysis
Time Complexity: O(MlogN)
Space Complexity: O(1)
M - the number of rows in binaryMatrix
N - the number of columns in binaryMatrix
*/
/**
 * // This is the BinaryMatrix's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface BinaryMatrix {
 *     public int get(int x, int y) {}
 *     public List<Integer> dimensions {}
 * };
 */
class Solution {
    int max = 101;

    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        List<Integer> dimension = binaryMatrix.dimensions();
        int m = dimension.get(0);
        int n = dimension.get(1);
        int min = max;
        for (int i = 0; i < m; i++) {
            min = Math.min(min, binarySearch(binaryMatrix, i, n));
        }
        return min == max ? -1 : min;
    }
    private int binarySearch(BinaryMatrix bm, int row, int n) {
        if (bm.get(row, n - 1) == 0) return max;
        if (bm.get(row, 0) == 1) return 0;
        int lo = 0;
        int hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (bm.get(row, mid) == 1) {
              hi = mid - 1;
            } else {
              lo = mid + 1;
            }
        }
        return lo;
    }
}

// Solution 2: Linear Time
// Refer to
// https://snowan.gitbook.io/study-notes/leetcode/30daychallenge/leftmost-column-with-at-least-a-one
/**
Try the problem before looking at the solution. The solution is rather easy if you think about it enough.
Okay, so it is important to note that in the problem we need to
) Reduce the number of calls to binaryMatrix.get() function, hence we need to think of minimizing the number of calls.
) It would be easy to traverse all the blocks from the left side, but this would increase the number of calls to the get() function.
) Every row is sorted. Hence every row starts from 0 and if it has a one, ends in 1. For example, if a row has 3 0’s and 2 1’s , 
  it would be [0 0 0 1 1] . This is important as it let’s us understand that for every row, if we go from the last index to the first 
  and keep a variable for the leftMostColumn we found a 1 in, we can solve this.
) We can also understand that say we are at the right-most cell, and the cell is 0, we can go down, since we know that 
there is no 1 on our left.
The 3rd and 4th point are important to understand as that is how our solution is optimal. Now that we understand this, 
let us go through a example.

Here we can see that our start is on the right top most element, every time we see a 0 , we go down, since we know that 
no 1 occurs on our left. When we see a 1, we add it to a variable ( so we remember it) and go left since there could be 
a another 1 to its left, then we a see a 0 and go down and so on. If we are on the last row and see a 0, we can stop 
since we know we have reach the left most element. Now that makes it O(ROWS +COLUMN) or O(M+N) for short in worst case.
*/

// https://medium.com/@sumukhballal/leftmost-column-with-at-least-a-one-problem-leetcode-57361bb66bf6
/**
Solutions #2 -- Linear Time
Start from up-right corner, (m, n)
init start position: row = 0, col = n - 1, res = -1
if current position value, binaryMatrix.get(row, col) = 1, update res = col, move left, row = row - 1
if current position, binaryMatrix.get(row, col) = 0, move down, col = col - 1
continue until one dimension out of boundry. return res.

Complexity Analysis
Time Complexity: O(M+N)
Space Complexity: O(1)
M - the number of rows in binaryMatrix
N - the number of columns in binaryMatrix
*/

/**
 * // This is the BinaryMatrix's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface BinaryMatrix {
 *     public int get(int x, int y) {}
 *     public List<Integer> dimensions {}
 * };
 */
class Solution {
    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        List<Integer> dimension = binaryMatrix.dimensions();
        int m = dimension.get(0);
        int n = dimension.get(1);
        int res = -1;
        int row = 0; int col = n - 1;
        while (row < m && col >= 0) {
            if (binaryMatrix.get(row, col) == 1) {
                res = col;
                col--;
            } else {
                row++;
            }
        }
        return res;
    }
}
