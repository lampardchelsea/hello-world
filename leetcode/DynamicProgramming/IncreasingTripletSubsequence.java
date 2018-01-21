/**
 * Refer to
 * https://leetcode.com/problems/increasing-triplet-subsequence/description/
 * Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.

    Formally the function should:
    Return true if there exists i, j, k 
    such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
    Your algorithm should run in O(n) time complexity and O(1) space complexity.

    Examples:
    Given [1, 2, 3, 4, 5],
    return true.

    Given [5, 4, 3, 2, 1],
    return false.
 * 
 * Solution
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/LongestIncreasingSubsequence.java
 * https://www.youtube.com/watch?v=DbP5OCoEwFo
 * https://discuss.leetcode.com/topic/37281/clean-and-short-with-comments-c
*/
// Solution 1: DP
// Same as Longest Increasing Subsequence
// Time Complexity: O(n^2)
class Solution {
    public boolean increasingTriplet(int[] nums) {
        int[] dp = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            dp[i] = 1;
        }
        int max = 0;
        for(int i = 0; i < nums.length; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max >= 3;
    }
}

// Solution 2: DP + Binary Search
// Same as Longest Increasing Subsequence
// Time Compelxity: O(nlogn)


// Solution 3:
// Refer to
// https://discuss.leetcode.com/topic/37281/clean-and-short-with-comments-c
/**
  c1 is min seen so far (it's a candidate for 1st element)
  here when x > c1, i.e. x might be either c2 or c3
  x is better than the current c2, store it
  here when we have/had c1 < c2 already and x > c2
  the increasing subsequence of 3 elements exists
*/
public class IncreasingTripleSubsequence {
  public boolean increasingTriplet(int[] nums) {
        int c1 = Integer.MAX_VALUE;
        int c2 = Integer.MAX_VALUE;
        for(int i = 0; i < nums.length; i++) {
            // Must contains '=' to make sure duplicate numbers
            // will set to same candidate, otherwise it will
            // go to next candidate and deduce 2 same candiates,
            // which violate the requirement as strictly asecending
            // sequence, e.g [1,1,-2,6]
            if(nums[i] <= c1) {
                c1 = nums[i];
            } else if(nums[i] <= c2) {
                c2 = nums[i];
            } else {
                return true;
            }
        }
        return false;
    }
	
	public static void main(String[] args) {
		IncreasingTripleSubsequence i = new IncreasingTripleSubsequence();
		int[] nums = {1,1,-2,6};
		boolean result = i.increasingTriplet(nums);
		System.out.println(result);
	}

}







