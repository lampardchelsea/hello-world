/**
 Refer to
 https://leetcode.com/problems/longest-continuous-increasing-subsequence/
 Given an unsorted array of integers, find the length of longest continuous increasing subsequence (subarray).

Example 1:
Input: [1,3,5,4,7]
Output: 3
Explanation: The longest continuous increasing subsequence is [1,3,5], its length is 3. 
Even though [1,3,5,7] is also an increasing subsequence, it's not a continuous one where 5 and 7 are separated by 4. 
Example 2:
Input: [2,2,2,2,2]
Output: 1
Explanation: The longest continuous increasing subsequence is [2], its length is 1. 
Note: Length of the array will not exceed 10,000.
*/

// Solutoin 1: Native Solution
// Refer to
// https://leetcode.com/problems/longest-continuous-increasing-subsequence/discuss/107365/JavaC%2B%2BClean-solution
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        int res = 0, cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || nums[i - 1] < nums[i]) res = Math.max(res, ++cnt);
            else cnt = 1;
        }
        return res;
    }
}

// Solution 2: DP
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        // State
        int n = nums.length;
        int[] f = new int[n];
        // initialize
        for(int i = 0; i < n; i++) {
            f[i] = 1;
        }
        int max = 0;
        // function
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                // Refer to
                // https://leetcode.com/problems/longest-increasing-subsequence/description/
                // DP solution:
                // Just add one more condition to make sure it is continuous sequence,
                // as j + 1 = i
                if(nums[j] < nums[i] && j + 1 == i) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
            max = Math.max(max, f[i]);
        }
        // answer
        return max;
    }
}
