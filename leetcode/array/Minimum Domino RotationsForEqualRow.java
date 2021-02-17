/**
Refer to
https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/
In a row of dominoes, A[i] and B[i] represent the top and bottom halves of the ith domino.  
(A domino is a tile with two numbers from 1 to 6 - one on each half of the tile.)

We may rotate the ith domino, so that A[i] and B[i] swap values.

Return the minimum number of rotations so that all the values in A are the same, or all the values in B are the same.

If it cannot be done, return -1.

Example 1:
Input: A = [2,1,2,4,2,2], B = [5,2,6,2,3,2]
Output: 2
Explanation: 
The first figure represents the dominoes as given by A and B: before we do any rotations.
If we rotate the second and fourth dominoes, we can make every value in the top row equal to 2, as indicated by the second figure.

Example 2:
Input: A = [3,5,1,2,3], B = [3,6,3,3,4]
Output: -1
Explanation: 
In this case, it is not possible to rotate the dominoes to make one row of values equal.

Constraints:
2 <= A.length == B.length <= 2 * 104
1 <= A[i], B[i] <= 6
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/discuss/252802/Thinking-Process
/**
We find all possible number of rotations to make all the values in A are the same, or all the values in B are the same, and then get the minimum among them.

A[i] = A[i] if not swap OR B[i] if swap, B[i] = B[i] if not swap OR A[i] if swap.
When i = 0, A[0] can be either A[0] or B[0], B[0] can be either B[0] or A[0].
So the value must be either A[0] or B[0] if can be done.

There are 4 possible cases:
make values in A equal to A[0]
make values in B equal to A[0]
make values in A equal to B[0]
make values in B equal to B[0]

For each case we count rotations and we get the min rotations among them.

    public int minDominoRotations(int[] A, int[] B) {
        int ans = min(
                f(A[0], A, B),
                f(B[0], A, B),
                f(A[0], B, A),
                f(B[0], B, A));
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
    
    private int min(int a, int b, int c, int d) {
        return Math.min(Math.min(Math.min(a, b), c), d);
    }

    // Count number of rotations to make values in A equal to target.
    private int f(int target, int[] A, int[] B) {
        int swap = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] != target) {
                if (B[i] != target) {
                    return Integer.MAX_VALUE;
                } else {
                    swap++;
                }
            }
        }
        return swap;
    }
*/
class Solution {
    public int minDominoRotations(int[] A, int[] B) {
        int result = findMin(rotate(A[0], A, B), rotate(A[0], B, A), rotate(B[0], A, B), rotate(B[0], B, A));
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int rotate(int target, int[] A, int[] B) {
        int rotate_count = 0;
        for(int i = 0; i < A.length; i++) {
            if(A[i] != target) {
                if(B[i] == target) {
                    rotate_count++;
                } else {
                    return Integer.MAX_VALUE;
                }
            }
        }
        return rotate_count;
    }
    
    private int findMin(int a, int b, int c, int d) {
        return Math.min(Math.min(Math.min(a, b), c), d);
    }
}
