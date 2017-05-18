/**
 * Refer to
 * https://leetcode.com/problems/maximum-product-subarray/#/description
 *  Find the contiguous subarray within an array (containing at least 
 *  one number) which has the largest product.
 *  For example, given the array [2,3,-2,4],
 *  the contiguous subarray [2,3] has the largest product = 6. 
 *
 * Solution
 * https://discuss.leetcode.com/topic/3607/sharing-my-solution-o-1-space-o-n-running-time
 * https://discuss.leetcode.com/topic/3607/sharing-my-solution-o-1-space-o-n-running-time/8
 */
public class MaximumProductSubarray {
	public int maxProduct(int[] nums) {
        int len = nums.length;
        if(len == 0) {
        	return 0;
        }
        int maxProduct = nums[0];
        int minProduct = nums[0];
        int maxResult = nums[0];
        for(int i = 1; i < len; i++) {
        	// Check if nums[i] is positive before getting maxProduct and minProduct
        	if(nums[i] >= 0) {
        		maxProduct = Math.max(maxProduct * nums[i], nums[i]);
        		minProduct = Math.min(minProduct * nums[i], nums[i]);
        	} else {
        		int temp = maxProduct;
        		maxProduct = Math.max(minProduct * nums[i], nums[i]);
        		minProduct = Math.min(temp * nums[i], nums[i]);
        	}
        	maxResult = Math.max(maxResult, maxProduct);
        }
        return maxResult;
    }
	
	public static void main(String[] args) {
		
	}
}

