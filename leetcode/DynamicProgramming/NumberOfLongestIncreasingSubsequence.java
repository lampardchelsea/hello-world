/** 
 * Refer to
 * https://leetcode.com/problems/number-of-longest-increasing-subsequence/description/
 * Given an unsorted array of integers, find the number of longest increasing subsequence.

  Example 1:
  Input: [1,3,5,4,7]
  Output: 2
  Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].
  Example 2:
  Input: [2,2,2,2,2]
  Output: 5
  Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 
  subsequences' length is 1, so output 5.
  Note: Length of the given array will be not exceed 2000 and the answer is guaranteed to be 
  fit in 32-bit signed int.
  
 *
 * Solution
 * https://discuss.leetcode.com/topic/102976/java-with-explanation-easy-to-understand
 * https://discuss.leetcode.com/topic/103020/java-c-simple-dp-solution-with-explanation
*/
class Solution {
    public int findNumberOfLIS(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        // state:
        // The idea is to use two arrays f[n] and cnt[n] to record the maximum length of 
        // Increasing Subsequence and the coresponding number of these sequence which 
        // ends with nums[i], respectively. That is:
        // f[i]: the length of the Longest Increasing Subsequence which ends with nums[i].
        // cnt[i]: the number of the Longest Increasing Subsequence which ends with nums[i].
        // Then, the result is the sum of each cnt[i] while its corresponding f[i] is the maximum length.
        int n = nums.length;
        // intialize:
        int result = 0;
        int max = 0;
        int[] f = new int[n];
        int[] cnt = new int[n];
        for(int i = 0; i < n; i++) {
            f[i] = 1;
            cnt[i] = 1;
        }
        // function
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    // Refer to
                    // https://discuss.leetcode.com/topic/103020/java-c-simple-dp-solution-with-explanation/9
                    // f[i] == f[j] + 1 means that you find another subsequence with the 
                    // same length of LIS which ends with nums[i]. 
                    // While f[i] > f[j] + 1 means that you find a subsequence, but its 
                    // length is smaller compared to LIS which ends with nums[i]. --> so
                    // for this case we will ignore
                    if(f[i] == f[j] + 1) {
                        // Important: not ++
                        cnt[i] += cnt[j];
                    }
                    if(f[i] < f[j] + 1) {
                        f[i] = f[j] + 1;
                        cnt[i] = cnt[j];
                    }
                }
            }
            // if(max_len == f[i]) {
            //     result += cnt[i];
            // }
            // if(max_len < f[i]) {
            //     max_len = f[i];
            //     result = cnt[i];
            // }
            // Refer to
            // https://discuss.leetcode.com/topic/102976/java-with-explanation-easy-to-understand
            // we can change the above section in same style as previous two question
            // https://leetcode.com/problems/longest-continuous-increasing-subsequence/description/
            // https://leetcode.com/problems/longest-increasing-subsequence/description/
            max = Math.max(max, f[i]);
        }
        for(int i = 0; i < n; i++) {
            if(max == f[i]) {
                result += cnt[i];
            }
        }  
        // answer
        return result;
    }
}
