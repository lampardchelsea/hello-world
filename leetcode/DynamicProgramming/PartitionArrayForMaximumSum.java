/**
 Refer to
 https://leetcode.com/problems/partition-array-for-maximum-sum/
 Given an integer array A, you partition the array into (contiguous) subarrays of length at most K.  
 After partitioning, each subarray has their values changed to become the maximum value of that subarray.

Return the largest sum of the given array after partitioning.

Example 1:
Input: A = [1,15,7,9,2,5,10], K = 3
Output: 84
Explanation: A becomes [15,15,15,9,10,10,10]

Note:
1 <= K <= A.length <= 500
0 <= A[i] <= 10^6
*/

// Solution 1: DP
// Refer to
// https://leetcode.com/problems/partition-array-for-maximum-sum/discuss/299443/Java-O(NK).-Faster-than-99.82.-Less-memory-than-100.-With-Explanation.
/**
 The dynamic programming solution works here because the problem has an optimal substructure and overlapping 
 subproblems as in the following example:

Let A = [9, 10, 2, 5] and K = 3

Let S[n1, n2, ..., ni] be the solution to subarray [n1, n2, ..., ni].
The following are base cases to initialize the memo array:

S[9] = 9 (i.e., memo[0] = 9)
S[9, 10] = 20 (i.e., memo[1] = 20)
S[9, 10, 2] = 30 (i.e., memo[2] = 30)
Here we do the real work, where you need to "loop" through a K-sized window before the new value to be considered, 
including the new value, which in this case the new value is 5:

S[9, 10, 2, 5] = max(S[9] + S[10, 2, 5], S[9, 10] + S[2, 5], S[9, 10, 2] + S[5]) = 39
The window we "looped" through above is [10, 2, 5].

From the formula above, we see that the overlapping subproblem is in using the solutions from previous solutions 
stored in the memo, e.g., S[9], S[9, 10], and S[9, 10, 2]. The optimal substructure comes from the fact that the 
solution to S[9, 10, 2, 5] is solved by using solutions to previously calculated solutions.
*/
class Solution {
    public int maxSumAfterPartitioning(int[] A, int K) {
        int result = 0;
        int[] dp = new int[A.length];
        dp[0] = A[0];
        int temp = A[0];
        for(int i = 1; i < K; i++) {
            if(A[i] > temp) {
                temp = A[i];
            }
            dp[i] = temp * (i + 1);
        }
        for(int i = K; i < A.length; i++) {
            int cur = 0;
            int maxInRangeK = A[i];
            // Backtrack up to K-1 indices to calculate current maximum for dp[i].
            for(int j = 1; j <= K; j++) {
                // Keep track of the current maximum in the window [i - j + 1, i].
                if(A[i - j + 1] > maxInRangeK) {
                    maxInRangeK = A[i - j + 1];
                }
                // cur is the candidate for the solution to dp[i] as we backtrack the K-1 window.
                cur = dp[i - j] + j * maxInRangeK;
                if(cur > dp[i]) {
                    dp[i] = cur;
                }
            }
        }
        return dp[A.length - 1];
    }
}
