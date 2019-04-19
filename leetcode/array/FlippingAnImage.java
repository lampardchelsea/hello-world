/**
 Refer to
 https://leetcode.com/problems/flipping-an-image/
 Given a binary matrix A, we want to flip the image horizontally, then invert it, and return the resulting image.

To flip an image horizontally means that each row of the image is reversed. 
For example, flipping [1, 1, 0] horizontally results in [0, 1, 1].

To invert an image means that each 0 is replaced by 1, and each 1 is replaced by 0. 
For example, inverting [0, 1, 1] results in [1, 0, 0].

Example 1:

Input: [[1,1,0],[1,0,1],[0,0,0]]
Output: [[1,0,0],[0,1,0],[1,1,1]]
Explanation: First reverse each row: [[0,1,1],[1,0,1],[0,0,0]].
Then, invert the image: [[1,0,0],[0,1,0],[1,1,1]]
Example 2:

Input: [[1,1,0,0],[1,0,0,1],[0,1,1,1],[1,0,1,0]]
Output: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
Explanation: First reverse each row: [[0,0,1,1],[1,0,0,1],[1,1,1,0],[0,1,0,1]].
Then invert the image: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
Notes:

1 <= A.length = A[0].length <= 20
0 <= A[i][j] <= 1
*/
// Solution 1: Native solution
class Solution {
    // [1,1,0]                      [0,1,1]            [1,0,0]
    // [1,0,1]-> reverse each row ->[1,0,1]-> invert ->[0,1,0]
    // [0,0,0]                      [0,0,0]            [1,1,1]
    public int[][] flipAndInvertImage(int[][] A) {
        for(int i = 0; i < A.length; i++) {
            int j = 0;
            int k = A[0].length - 1;
            while(j < k) {
                int temp = A[i][j];
                A[i][j] = A[i][k];
                A[i][k] = temp;
                j++;
                k--;
            }
            for(int m = 0; m < A[0].length; m++) {
                if(A[i][m] == 0) {
                    A[i][m] = 1;
                } else {
                    A[i][m] = 0;
                }
            }
        }
        return A;
    }
}
