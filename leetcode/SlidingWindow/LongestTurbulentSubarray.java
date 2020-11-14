/**
Refer to
https://leetcode.com/problems/longest-turbulent-subarray/
A subarray A[i], A[i+1], ..., A[j] of A is said to be turbulent if and only if:

For i <= k < j, A[k] > A[k+1] when k is odd, and A[k] < A[k+1] when k is even;
OR, for i <= k < j, A[k] > A[k+1] when k is even, and A[k] < A[k+1] when k is odd.
That is, the subarray is turbulent if the comparison sign flips between each adjacent pair of elements in the subarray.

Return the length of a maximum size turbulent subarray of A.

Example 1:
Input: [9,4,2,10,7,8,8,1,9]
Output: 5
Explanation: (A[1] > A[2] < A[3] > A[4] < A[5])

Example 2:
Input: [4,8,12,16]
Output: 2

Example 3:
Input: [100]
Output: 1

Note:
1 <= A.length <= 40000
0 <= A[i] <= 10^9
*/

// Solution 1: Two flag to record
// Refer to
// https://leetcode.com/problems/longest-turbulent-subarray/discuss/221935/Java-O(N)-time-O(1)-space
class Solution {
    public int maxTurbulenceSize(int[] A) {
        // For each A[i]
        // inc: The length of current valid sequence which ends with two increasing numbers
        // dec: The length of current valid sequence which ends with two decreasing numbers
        int inc = 1;
        int dec = 1;
        int result = 1;
        for(int i = 1; i < A.length; i++) {
            // Current valid sequence ending with two decreasing numbers
            if(A[i - 1] > A[i]) {
                // Previous valid sequence length record in 'inc'
                dec = inc + 1;
                // Reset previous valid sequence length to 1
                inc = 1;
            } else if(A[i - 1] < A[i]) {
                inc = dec + 1;
                dec = 1;
            } else {
                // When encounter 2 adjacent equal numbers reset both to 1
                dec = 1;
                inc = 1;
            }
            // Since for each A[i] we get one pair of dec and inc, so we need to find out max one among all pairs
            result = Math.max(result, Math.max(dec, inc));
        }
        return result;
    }
}
