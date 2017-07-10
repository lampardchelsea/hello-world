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
 * Solution 1:
 * https://discuss.leetcode.com/topic/33259/o-n-super-clean-9-line-java-solution-with-hashmap
 * The HashMap stores the sum of all elements before index i as key, and i as value. For each i, 
 * check not only the current sum but also (currentSum - previousSum) to see if there is any that 
 * equals k, and update max length.
 * 
 * Solution 2:
 * https://discuss.leetcode.com/topic/33537/java-o-n-explain-how-i-come-up-with-this-idea
 * The subarray sum reminds me the range sum problem. Preprocess the input array such that you get
 * the range sum in constant time.
 * sum[i] means the sum from 0 to i inclusively, the sum from i to j is sum[j] - sum[i - 1] 
 * -----------> except that from 0 to j is sum[j] <--------------- (This is a special case).
 * j-i is equal to the length of subarray of original array. we want to find the max(j - i)
 * for any sum[j] we need to find if there is a previous sum[i] such that sum[j] - sum[i] = k
 * Instead of scanning from 0 to j -1 to find such i, we use hashmap to do the job in constant time.
 * However, there might be duplicate value of of sum[i] we should avoid overriding its index as we 
 * want the max j - i, so we want to keep i as left as possible.
*/
public class MaximumSizeSubarraySumEqualsK {
	// Solution 1:
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
	
	// Solution 2:
	public int maxSubArrayLen2(int[] nums, int k) {
		if(nums == null || nums.length == 0) {
			return 0;
		}
		int n = nums.length;
		for(int i = 1; i < n; i++) {
			nums[i] += nums[i - 1];
		}
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int max = 0;
		// Refer to
		// https://discuss.leetcode.com/topic/33537/java-o-n-explain-how-i-come-up-with-this-idea/8
		// look at this part max = Math.max(max, i - map.get(nums[i] - k))
		// so make map.put(0,-1) is just to say if the nums[i]-k==0 which means 
		// index from 0 to index i will make to sum k.
		// in this case, the length of the subarray will be 0,1,2,...i, which is i+1, so you need 
		// the value of map.get(nums[i]-k) to be "-1"
		map.put(0, -1);
		for(int i = 0; i < n; i++) {
			if(map.containsKey(nums[i] - k)) {
				max = Math.max(max, i - map.get(nums[i] - k));
			}
			// Keep only 1st duplicate as we want first index as left as possible
			if(!map.containsKey(nums[i])) {
				map.put(nums[i], i);
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		int[] nums = {1, -1, 5, -2, 3};
		int k = 3;
		MaximumSizeSubarraySumEqualsK m = new MaximumSizeSubarraySumEqualsK();
		int result = m.maxSubArrayLen2(nums, k);
		System.out.println(result);
	}
}
