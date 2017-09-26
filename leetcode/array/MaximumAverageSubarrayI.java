/**
 * Refer to
 * http://www.lintcode.com/en/problem/maximum-average-subarray/
 * https://leetcode.com/problems/maximum-average-subarray-i/description/
 * Given an array with positive and negative numbers, find the maximum average 
 * subarray which length should be greater or equal to given length k.

	 Notice
	
	It's guaranteed that the size of the array is greater or equal to k.
	
	Have you met this question in a real interview? Yes
	Example
	Given nums = [1, 12, -5, -6, 50, 3], k = 3
	
	Return 15.667 // (-6 + 50 + 3) / 3 = 15.667
 * 
 * 
 * Solution
 * http://www.cnblogs.com/grandyang/p/7294585.html
 * https://leetcode.com/problems/maximum-average-subarray-i/solution/
 */
public class MaximumAverageSubarrayI {
	// Solution 1: 
	// Approach #1 Cumulative Sum [Accepted]
	/**
	 * Refer to
	 * https://leetcode.com/problems/maximum-average-subarray-i/solution/
	 * We know that in order to obtain the averages of subarrays with length k, 
	 * we need to obtain the sum of these k length subarrays. One of the methods 
	 * of obtaining this sum is to make use of a cumulative sum array, sum, 
	 * which is populated only once. Here, sum[i] is used to store the sum of 
	 * the elements of the given nums array from the first element up to the 
	 * element at the i​th index.
	 * Once the sum array has been filled up, in order to find the sum of elements 
	 * from the index ii to i+k, all we need to do is to use: sum[i]−sum[i−k]. 
	 * Thus, now, by doing one more iteration over the sum array, we can determine 
	 * the maximum average possible from the subarrays of length k.
	 * Complexity Analysis
	 * Time complexity : O(n). We iterate over the nums array of length n 
	 *                   once to fill the sum array. Then, we iterate over n−k 
	 *                   elements of sum to determine the required result.
	 * Space complexity : O(n). We make use of a sum array of length n to store 
	 *                    the cumulative sum.
	 * 
	 * Refer to
	 * http://www.cnblogs.com/grandyang/p/7294585.html
	 * 这道题给了我们一个数组nums，还有一个数字k，让我们找长度为k且平均值最大的子数组。由于子数组必须是连续的，
	 * 所以我们不能给数组排序。那么怎么办呢，在博主印象中，计算子数组之和的常用方法应该是建立累加数组，然后我们可以快速计算
	 * 出任意一个长度为k的子数组，用来更新结果res，从而得到最大的那个
	 */
    public double findMaxAverage(int[] nums, int k) {
        int n = nums.length;
        int[] preSum = new int[n];
        preSum[0] = nums[0];
        for(int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        // double max = 0;
        // Caution: When initialize max must point to preSum[k - 1], not 0
        // E.g input [5] and 1, expected 5.00000 output 0.00000
        double max = preSum[k - 1];
        // Style 1:
        int m = n - k;
        for(int i = 0; i < m; i++) {
            double sum = preSum[i + k] - preSum[i];
            max = Math.max(max, sum);
        }
        // Style 2:
        // for(int i = k; i < n; i++) {
        // 	double sum = preSum[i] - preSum[i - k];
        // 	max = Math.max(max, sum);
        // }
        return max / k;
    }
    
    // Solution 2:
    // Approach #2 Sliding Window [Accepted]
    /**
     * Algorithm
     * Instead of creating a cumulative sum array first, and then traversing over it to 
     * determine the required sum, we can simply traverse over nums just once, and 
     * on the go keep on determining the sums possible for the subarrays of length k. 
     * To understand the idea, assume that we already know the sum of elements from 
     * index ii to index i+k, say it is xx.
     * Now, to determine the sum of elements from the index i+1i+1 to the index i+k+1
     * , all we need to do is to subtract the element nums[i] from xx and to add 
     * the element nums[i+k+1] to xx. We can carry out our process based on 
     * this idea and determine the maximum possible average.
     */
    public double findMaxAverage2(int[] nums, int k) {
        int n = nums.length;
        double sum = 0;
        int i;
        for(i = 0; i < k; i++) {
            sum += nums[i];
        }
        double max = sum;
        for(i = k; i < n; i++) {
            sum += nums[i] - nums[i - k];
            max = Math.max(max, sum);
        }
        return max / k;
    }
    
    public static void main(String[] args) {
    	MaximumAverageSubarrayI m = new MaximumAverageSubarrayI();
    	int k = 3;
    	int[] nums = {1, 12, -5, -6, 50, 3};
    	double result = m.findMaxAverage(nums, k);
    	System.out.print(result);
    }
}

