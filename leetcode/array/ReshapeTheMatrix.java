/**
 Refer to
 https://leetcode.com/problems/reshape-the-matrix/
 In MATLAB, there is a very useful function called 'reshape', which can reshape a matrix into a 
 new one with different size but keep its original data.

You're given a matrix represented by a two-dimensional array, and two positive integers r and c 
representing the row number and column number of the wanted reshaped matrix, respectively.

The reshaped matrix need to be filled with all the elements of the original matrix in the same 
row-traversing order as they were.

If the 'reshape' operation with given parameters is possible and legal, output the new reshaped 
matrix; Otherwise, output the original matrix.

Example 1:
Input: 
nums = 
[[1,2],
 [3,4]]
r = 1, c = 4
Output: 
[[1,2,3,4]]
Explanation:
The row-traversing of nums is [1,2,3,4]. The new reshaped matrix is a 1 * 4 matrix, fill it row by 
row by using the previous list.

Example 2:
Input: 
nums = 
[[1,2],
 [3,4]]
r = 2, c = 4
Output: 
[[1,2],
 [3,4]]
Explanation:
There is no way to reshape a 2 * 2 matrix to a 2 * 4 matrix. So output the original matrix.
Note:
The height and width of the given matrix is in range [1, 100].
The given r and c are all positive.
*/
// Solution 1:
/**
Approach #1 Using queue [Accepted]
Algorithm
The simplest method is to extract all the elements of the given matrix by reading the elements in 
a row-wise fashion. In this implementation, we use a queue to put the extracted elements. Then, 
we can take out the elements of the queue formed in a serial order and arrange the elements in the 
resultant required matrix in a row-by-row order again.

The formation of the resultant matrix won't be possible if the number of elements in the original 
matrix isn't equal to the number of elements in the resultant matrix.

Complexity Analysis
Time complexity : O(m*n). We traverse over m*nm∗n elements twice. Here, m and n refer to the number of rows 
                  and columns of the given matrix respectively.
Space complexity : O(m*n). The queue formed will be of size m*n.
*/
class Solution {
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int[][] result = new int[r][c];
        if (nums.length == 0 || r * c != nums.length * nums[0].length) {
            return nums;
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i = 0; i < nums.length; i++) {
            for(int j = 0; j < nums[0].length; j++) {
                queue.add(nums[i][j]);
            }
        }
        for(int i = 0; i < r; i++) {
            for(int j = 0; j < c; j++) {
                result[i][j] = queue.remove();
            }
        }
        return result;
    }
}

// Solution 2:
/**
  Approach #2 Without using extra Space [Accepted]
  Algorithm
  Instead of unnecessarily using the queue as in the brute force approach, we can keep putting 
  the numbers in the resultant matrix directly while iterating over the given matrix in a row-by-row 
  order. While putting the numbers in the resultant array, we fix a particular row and keep on 
  incrementing the column numbers only till we reach the end of the required columns indicated by c. 
  At this moment, we update the row index by incrementing it and reset the column index to start from 
  0 again. Thus, we can save the space consumed by the queue for storing the data that just needs to 
  be copied into a new array.
*/
