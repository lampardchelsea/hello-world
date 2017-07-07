import java.util.HashMap;
import java.util.Map;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5336668.html
 * Given an array nums and a target value k, find the maximum length of a subarray that sums to k. 
 * If there isn't one, return 0 instead.
 * Example 1:
 * Given nums = [1, -1, 5, -2, 3], k = 3,
 * return 4. (because the subarray [1, -1, 5, -2] sums to 3 and is the longest)
 * 
 * Example 2:
 * Given nums = [-2, -1, 2, 1], k = 1,
 * return 2. (because the subarray [-1, 2] sums to 1 and is the longest)
 *
 * Follow Up:
 * Can you do it in O(n) time? 
 *
 * Solution
 * https://discuss.leetcode.com/topic/33259/o-n-super-clean-9-line-java-solution-with-hashmap
 * The HashMap stores the sum of all elements before index i as key, and i as value. For each i, 
 * check not only the current sum but also (currentSum - previousSum) to see if there is any that 
 * equals k, and update max length.
*/
public class MaximumSizeSubarraySumEqualsK {
	public int maxSubArrayLen(int[] nums, int k) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int sum = 0;
		int max = 0;
		for(int i = 0; i < nums.length; i++) {
			sum += nums[i];
			if(sum == k) {
				max = i + 1; 
			// Refer to
			// https://discuss.leetcode.com/topic/33259/o-n-super-clean-9-line-java-solution-with-hashmap/2
			// Only one question, if sum == k, we do not need to check the second one cause i+1 
			// must be larger,
			} else if(map.containsKey(sum - k)) {
				max = Math.max(max, i - map.get(sum - k));
			}
			if(!map.containsKey(sum)) {
				map.put(sum, i);
			}
		}
		return max;
	}
}
