/** 
 * Refer to
 * https://leetcode.com/problems/longest-continuous-increasing-subsequence/description/
 * Given an unsorted array of integers, find the length of longest continuous increasing subsequence.

  Example 1:
  Input: [1,3,5,4,7]
  Output: 3
  Explanation: The longest continuous increasing subsequence is [1,3,5], its length is 3. 
  Even though [1,3,5,7] is also an increasing subsequence, it's not a continuous one where 5 and 7 are separated by 4. 
  Example 2:
  Input: [2,2,2,2,2]
  Output: 1
  Explanation: The longest continuous increasing subsequence is [2], its length is 1. 
 *
 *
 * Solution
 * https://discuss.leetcode.com/topic/102999/java-c-clean-solution
*/
class Solution {
    public int findLengthOfLCIS(int[] A) {
        if(A == null || A.length == 0) {
            return 0;
        }
        int result = 1;
        int max = 1;
        int n = A.length;
        // from left to right
        for(int i = 1; i < n; i++) {
            if(A[i] > A[i - 1]) {
                max++;
            } else {
                max = 1;
            }
            result = Math.max(result, max);
        }
        return result;
    }
}
