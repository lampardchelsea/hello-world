/**
Refer to
https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/
Given an array A of non-negative integers, return the maximum sum of elements in two non-overlapping (contiguous) subarrays, 
which have lengths L and M.  (For clarification, the L-length subarray could occur before or after the M-length subarray.)

Formally, return the largest V for which V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... + A[j+M-1]) and either:
0 <= i < i + L - 1 < j < j + M - 1 < A.length, or
0 <= j < j + M - 1 < i < i + L - 1 < A.length.

Example 1:
Input: A = [0,6,5,2,2,5,1,9,4], L = 1, M = 2
Output: 20
Explanation: One choice of subarrays is [9] with length 1, and [6,5] with length 2.

Example 2:
Input: A = [3,8,1,3,2,1,8,9,0], L = 3, M = 2
Output: 29
Explanation: One choice of subarrays is [3,8,1] with length 3, and [8,9] with length 2.

Example 3:
Input: A = [2,1,5,6,0,9,5,0,3,8], L = 4, M = 3
Output: 31
Explanation: One choice of subarrays is [5,6,0,9] with length 4, and [3,8] with length 3.

Note:
L >= 1
M >= 1
L + M <= A.length <= 1000
0 <= A[i] <= 1000
*/

// Solution 1: preSum + sliding window
// Refer to
// https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/discuss/279221/JavaPython-3-two-easy-DP-codes-w-comment-time-O(n)-NO-change-of-input
// Style 1: preSum size [n + 1]
class Solution {
    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        int n = A.length;
        // Construct preSum array
        // preSum[0] --> 0, preSum[1] --> A[0], preSum[2] --> A[1]...etc.
        int[] preSum = new int[n + 1];
        for(int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + A[i];
        }
        // Find maximum between two conditions
        /**        
        (1) L before M and Non-overlapping
             i-M-L            i-M              i
        |------|<------L------>|<------M------>|------|
                L=(i-M)-(i-M-L)    M=i-(i-M)
        
        (2) M before L and Non-overlapping
             i-L-M            i-L              i
        |------|<------M------>|<------L------>|------|
                M=(i-L)-(i-L-M)    L=i-(i-L)
        */
        return Math.max(maxSum(preSum, L, M), maxSum(preSum, M, L));
    }
    
    // Naming for show case of maxSum(preSum, L, M), L before M
    /**
    1. Scan the prefix sum array from index L + M, which is the first possible position;
    2. update the max value of the L-length subarray; then update max value of the sum of the both;
    3. we need to swap L and M to scan twice, since either subarray can occur before the other.
    4.In private method, prefix sum difference p[i - M] - p[i - M - L] is L-length subarray from index i - M - L to i - M - 1, and p[i] - p[i - M] is M-length subarray from index i - M to i - 1.
    */
    private int maxSum(int[] p, int L, int M) {
        int result = 0;
        // Lmax, max sum of contiguous L elements before the last M elements.
        int Lmax = 0;
        // Scan the prefix sum array from index L + M, which is the first possible position
        for(int i = L + M; i < p.length; i++) {
            // Update max of L-length subarray
            Lmax = Math.max(Lmax, p[i - M] - p[i - M - L]);
            // Update max of the sum of L-length & M-length subarrays by 
            // finding maximum sum for L length before index i and add it 
            // with every M length sum right to it
            result = Math.max(result, Lmax + p[i] - p[i - M]);
        }
        return result;
    }
}

// Style 2: preSum size [n]
// Refer to
// https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/discuss/278251/JavaC%2B%2BPython-O(N)Time-O(1)-Space
class Solution {
    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        int n = A.length;
        // Construct preSum array
        // preSum[0] --> A[0], preSum[1] --> A[1], preSum[2] --> A[2]...etc.
        int[] preSum = new int[n];
        preSum[0] = A[0];
        for(int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + A[i];
        }
        return Math.max(maxSum(preSum, L, M), maxSum(preSum, M, L));
    }

    private int maxSum(int[] p, int L, int M) {
        int result = p[L + M - 1];
        int Lmax = p[L - 1];
        for(int i = L + M; i < p.length; i++) {
            Lmax = Math.max(Lmax, p[i - M] - p[i - M - L]);
            result = Math.max(result, Lmax + p[i] - p[i - M]);
        }
        return result;
    }
}
