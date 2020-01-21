/**
 Refer to
 https://leetcode.com/problems/longest-increasing-subsequence/
 Given an unsorted array of integers, find the length of longest increasing subsequence.

Example:

Input: [10,9,2,5,3,7,101,18]
Output: 4 
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
Note:

There may be more than one LIS combination, it is only necessary for you to return the length.
Your algorithm should run in O(n2) complexity.
Follow up: Could you improve it to O(n log n) time complexity?
*/

// Solution 1:
class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        // state: f[x] means add number at position x can build the LIS
        int n = nums.length;
        int[] f = new int[n];
        // initialize: every position can be a start point of LIS and
        // default length is 1 (itself for all positions)
        for(int i = 0; i < n; i++) {
            f[i] = 1;
        }
        int max = 0;
        // function: 
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    /**
                     * What will happen if not compare f[i], f[j] + 1 ?
                        20 / 24 test cases passed.
                        Status: Wrong Answer
                        Input:
                        [1,3,6,7,9,4,10,5,6]
                        Output:
                        5
                        Expected:
                        6
                        i = 6
                        j = 5
                        f[i] = 6 -> 1,3,6,7,9,10
                        f[j] = 3 -> 1,3,6 and since nums[5] = 4, the 1,3,6 already the max length after adding 4
                        f[i] > f[j] + 1
                        f[i] = Math.max(f[i], f[j] + 1) -> Should keep f[i] = 6
                    */
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
            // Update global result: every position can be the end ponit of LIS
            max = Math.max(max, f[i]);
        }
        return max;
    }
}

// Solution 2: DP + Binary Search
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/310b1934ce68fd4b95ac7a47982ca7a624516346/leetcode/DynamicProgramming/LongestIncreasingSubsequence.java
