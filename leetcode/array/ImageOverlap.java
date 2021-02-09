/**
Refer to
https://leetcode.com/problems/image-overlap/
You are given two images img1 and img2 both of size n x n, represented as binary, square matrices of the same size. 
(A binary matrix has only 0s and 1s as values.)

We translate one image however we choose (sliding it left, right, up, or down any number of units), and place it on 
top of the other image.  After, the overlap of this translation is the number of positions that have a 1 in both images.

(Note also that a translation does not include any kind of rotation.)

What is the largest possible overlap?

Example 1:
1 1 0      0 0 0
0 1 0      0 1 1 
0 1 0      0 0 1
 img1       img2
Input: img1 = [[1,1,0],[0,1,0],[0,1,0]], img2 = [[0,0,0],[0,1,1],[0,0,1]]
Output: 3
Explanation: We slide img1 to right by 1 unit and down by 1 unit.
1 1 0      0 1 1     0 0 0
0 1 0  --> 0 0 1 --> 0 1 1
0 1 0      0 0 1     0 0 1
           right      down          
The number of positions that have a 1 in both images is 3. (Shown in red)

Example 2:
Input: img1 = [[1]], img2 = [[1]]
Output: 1

Example 3:
Input: img1 = [[0]], img2 = [[0]]
Output: 0

Constraints:
n == img1.length
n == img1[i].length
n == img2.length
n == img2[i].length
1 <= n <= 30
img1[i][j] is 0 or 1.
img2[i][j] is 0 or 1.
*/

// Solution 1: Shift and Count
// Refer to
// https://leetcode.com/problems/image-overlap/solution/
/**
Intuition

As stated in the problem description, in order to calculate the number of ones in the overlapping zone, 
we should first shift one of the images. Once the image is shifted, it is intuitive to count the numbers.

Therefore, a simple idea is that one could come up all possible overlapping zones, by shifting the image matrix, 
and then simply count within each overlapping zone.

The image matrix could be shifted in four directions, i.e. left, right, up and down.

We could represent the shifting with a 2-axis coordinate as follows, where the X-axis indicates the shifting on 
the directions of left and right and the Y-axis indicates the shifting of up and down.

For instance, the coordinate of (1, 1) represents that we shift the matrix to the right by one and to the up side by one as well.

One important insight is that shifting one matrix to a direction is equivalent to shifting the other matrix to 
the opposite direction, in the sense that we would have the same overlapping zone at the end.

For example, by shifting the matrix A to one step on the right, is same as shifting the matrix B to the left by one step.

Algorithm
Based on the above intuition, we could implement the solution step by step. First we define the function 
shift_and_count(x_shift, y_shift, M, R) where we shift the matrix M with reference to the matrix R with the shifting 
coordinate (x_shift, y_shift) and then we count the overlapping ones in the overlapping zone.

The algorithm is organized as a loop over all possible combinations of shifting coordinates (x_shift, y_shift).

More specifically, the ranges of x_shift and y_shift are both [0, N-1] where NN is the width of the matrix.

At each iteration, we invoke the function shift_and_count() twice to shift and count the overlapping zone, first with 
the matrix B as the reference and vice versa.

class Solution {
    /**
     *  Shift the matrix M in up-left and up-right directions 
     *    and count the ones in the overlapping zone.
     */
    protected int shiftAndCount(int xShift, int yShift, int[][] M, int[][] R) {
        int leftShiftCount = 0, rightShiftCount = 0;
        int rRow = 0;
        // count the cells of ones in the overlapping zone.
        for (int mRow = yShift; mRow < M.length; ++mRow) {
            int rCol = 0;
            for (int mCol = xShift; mCol < M.length; ++mCol) {
                if (M[mRow][mCol] == 1 && M[mRow][mCol] == R[rRow][rCol])
                    leftShiftCount += 1;
                if (M[mRow][rCol] == 1 && M[mRow][rCol] == R[rRow][mCol])
                    rightShiftCount += 1;
                rCol += 1;
            }
            rRow += 1;
        }
        return Math.max(leftShiftCount, rightShiftCount);
    }

    public int largestOverlap(int[][] A, int[][] B) {
        int maxOverlaps = 0;

        for (int yShift = 0; yShift < A.length; ++yShift)
            for (int xShift = 0; xShift < A.length; ++xShift) {
                // move the matrix A to the up-right and up-left directions.
                maxOverlaps = Math.max(maxOverlaps, shiftAndCount(xShift, yShift, A, B));
                // move the matrix B to the up-right and up-left directions, which is equivalent to moving A to the down-right and down-left directions 
                maxOverlaps = Math.max(maxOverlaps, shiftAndCount(xShift, yShift, B, A));
            }

        return maxOverlaps;
    }
}
*/
class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int max = 0;
        int n = img1.length;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                max = Math.max(max, helper(i, j, n, img1, img2));
                max = Math.max(max, helper(i, j, n, img2, img1));
            }
        }
        return max;
    }
    
    private int helper(int x_shift, int y_shift, int n, int[][] a, int[][] b) {
        int count1 = 0;
        int count2 = 0;
        int bRow = 0;
        for(int aRow = y_shift; aRow < n; aRow++) {
            int bCol = 0;
            for(int aCol = x_shift; aCol < n; aCol++) {
                if(a[aRow][aCol] == 1 && a[aRow][aCol] == b[bRow][bCol]) {
                    count1++;
                }
                if(a[aRow][bCol] == 1 && a[aRow][bCol] == b[bRow][aCol]) {
                    count2++;
                }
                bCol++;
            }
            bRow++;
        }
        return Math.max(count1, count2);
    }
}

// Solution 2: Promote code
// Refer to
// https://leetcode.com/problems/image-overlap/discuss/161851/Java-most-intuitive-solution
/**
If you had the two pictures in your hand, what's the most intuitive thing to do? 
Move them around until you get the most overlap. This is what the code does:

Move the second image around starting from the bottom right corner to the top left hand corner
For each move, see whats the overlap
Return the highest value
*/
class Solution {
    public int largestOverlap(int[][] A, int[][] B) {
        int ans = 0;
        for (int row = -A.length; row < A.length; row++) {
            for (int col = -A[0].length; col < A[0].length; col++) {
                ans = Math.max(ans, overlap(A, B, row, col));
            }
        }
        return ans;
    }
    public int overlap(int[][] A, int[][] B, int rowOffset, int colOffset) {
        int ans = 0;
        for (int row = 0; row < A.length; row++) {
            for (int col = 0; col < A[0].length; col++) {
                if ((row+rowOffset < 0 || row+rowOffset >= A.length) || (col + colOffset < 0 || col + colOffset >= A.length)) {
                    continue;
                }
                if(A[row][col] == 1 && B[row + rowOffset][col+colOffset] == 1) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
