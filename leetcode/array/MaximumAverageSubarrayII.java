/**
 * Refer to
 * https://leetcode.com/articles/maximum-average-subarray-ii/
 * Given an array consisting of n integers, find the contiguous subarray whose length is 
 * greater than or equal to k that has the maximum average value. And you need to output 
 * the maximum average value.

	Example 1:
	Input: [1,12,-5,-6,50,3], k = 4
	Output: 12.75
	Explanation:
	when length is 5, maximum average value is 10.8,
	when length is 6, maximum average value is 9.16667.
	Thus return 12.75.
	Note:
	1 <= k <= n <= 10,000.
	Elements of the given array will be in range [-10,000, 10,000].
	The answer with the calculation error less than 10-5 will be accepted.
 * 
 * 
 * Solution
 * https://leetcode.com/articles/maximum-average-subarray-ii/
 */
public class MaximumAverageSubarrayII {
	// Solution 1:
	/**
	 * Approach #1 Iterative method [Time Limit Exceeded]
	 * One of the simplest solutions is to consider the sum of every possible subarray 
	 * with length greater than or equal to k and to determine the maximum average 
	 * from out of those. But, instead of finding out this sum in a naive manner for 
	 * every subarray with length greater than or equal to kk separately, we can do as follows.
	 * For every starting point, s, considered, we can iterate over the elements of 
	 * nums starting from nums, and keep a track of the sum found till the current 
	 * index(i). Whenever the index reached is such that the number of elements lying 
	 * between s and i is greater than or equal to k, we can check if the average 
	 * of the elements between s and i is greater than the average found till now or not
	 */	
	public double findMaxAverage(int[] nums, int k) {
		double result = Integer.MIN_VALUE;
		for(int start = 0; start < nums.length - k + 1; start++) {
		    long sum = 0;
		    for(int i = start; i < nums.length; i++) {
		    	sum += nums[i];
		    	if(i - start + 1 >= k) {
		    		result = Math.max(result, sum * 1.0 / (i - start + 1));
		    	}
		    }
		}
		return result;
	}
	
	// Solution 2: 
	/**
	 * Refer to
	 * https://leetcode.com/articles/maximum-average-subarray-ii/
	 * Complexity Analysis
	 * Time complexity : O(nlog(max_val-min_val)) check takes O(n) time and 
	 *                   it is executed O(log(max_val−min_val)) times.
	 * Space complexity : O(1). Constant Space is used.
	 */
	public double findMaxAverage2(int[] nums, int k) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        double max_val = Integer.MIN_VALUE;
        double min_val = Integer.MAX_VALUE;
        for(int i = 0; i < nums.length; i++) {
            max_val = Math.max(max_val, nums[i]);
            min_val = Math.min(min_val, nums[i]);
        }
        double eps = 1e-5;
        double prev_mid = max_val;
        // In order to keep our precision in control, we limit this 
        // process to 10^-5 precision, by making use of error and 
        // continuing the process till error becomes lesser than 0.00001
        double error = Integer.MAX_VALUE;
        while(error > eps) {
            double mid = (max_val + min_val) / 2.0;
            if(check(nums, mid, k)) {
                min_val = mid;
            } else {
                max_val = mid;
            }
            // Update error
            error = Math.abs(prev_mid - mid);
            prev_mid = mid;
        }
        return min_val;
    }
    
    private boolean check(int[] nums, double mid, int k) {
        double sum = 0;
        for(int i = 0; i < k; i++) {
            sum += nums[i] - mid;
        }
        if(sum >= 0) {
            return true;
        }
        // Now, if sum < 0, we keep on check
        double pre_sum = 0;
        double min_sum = 0;
        for(int i = k; i < nums.length; i++) {
            sum += nums[i] - mid;
            pre_sum += nums[i - k] - mid;
            // Try to find the minimum one among all pre_sum,
            // the default minimum value set as 0 which try
            // to align with next if statement as default
            // as (sum >= 0)
            min_sum = Math.min(pre_sum, min_sum);
            // If we find one possiblity
            if(sum >= min_sum) {
                return true;
            }
        }
        return false;
    }
	
	public static void main(String[] args) {
		MaximumAverageSubarrayII m = new MaximumAverageSubarrayII();
		int[] nums = {1,12,-5,-6,50,3};
		int k = 4;
		double result = m.findMaxAverage(nums, k);
		System.out.print(result);
		
		
	}
}
