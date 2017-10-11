/** 
 * Refer to
 * https://leetcode.com/problems/longest-continuous-increasing-subsequence/description/
 * http://www.lintcode.com/en/problem/longest-increasing-continuous-subsequence/
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
  Note: Length of the array will not exceed 10,000.
 * 
 * Solution
 * https://www.jiuzhang.com/solution/longest-increasing-continuous-subsequence/
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/LongestIncreasingSubsequence.java
*/
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


// Print Version --> p and p[i] relation seems Path Compression
public class LongestIncreasingContinuousSubsequence {
	public int longestIncreasingContinuousSubsequence(int[] A) {
		int n = A.length;
        int[] f = new int[n];
        
        // If you remove comments in code, it can print out the longest sequence
        // 去掉所有加注释的地方可以打印方案
        
        int[] pi = new int[n];
        int p = 0;
        int max = 0;
        for (int i = 0; i < n; i++) {
            f[i] = 1;
            pi[i] = -1;
            for (int j = 0; j < i; j++) {
                if (A[j] < A[i]) {
                    f[i] = f[i] > f[j] + 1 ? f[i] : f[j] + 1;
                    if (f[i] == f[j] + 1) {
                        pi[i] = j;
                    }
                }
            }
            if (f[i] > max) {
                max = f[i];
                p = i;
            }
        }
        
        int[] seq = new int[max];
        for (int i = max - 1; i >= 0; --i) {
            seq[i] = A[p];
            p = pi[p];
        }
        
        for (int i = 0; i < max; ++i) {
            System.out.println(seq[i]);
        }
        return max;
	}
	
	public static void main(String[] args) {
		LongestIncreasingContinuousSubsequence l = new LongestIncreasingContinuousSubsequence();
		int[] nums = {1,3,5,4,7};
		int result = l.longestIncreasingContinuousSubsequence(nums);
		System.out.println(result);
	}
}








