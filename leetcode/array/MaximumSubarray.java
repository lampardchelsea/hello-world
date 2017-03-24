/**
 * Refer to
 * https://leetcode.com/problems/maximum-subarray/#/description
 * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
 * For example, given the array [-2,1,-3,4,-1,2,1,-5,4], the contiguous subarray [4,-1,2,1] has the largest sum = 6.
 * click to show more practice.
 * More practice:
 * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, 
 * which is more subtle.
 */
public class MaximumSubarray {
	/**
	 * Solution 1: DP Solution with O(n) extra space
	 * Refer to
	 * https://discuss.leetcode.com/topic/6413/dp-solution-some-thoughts
	 * Analysis of this problem:
	 * Apparently, this is a optimization problem, which can be usually solved by DP. So when it comes to DP, 
	 * the first thing for us to figure out is the format of the sub problem(or the state of each sub problem). 
	 * The format of the sub problem can be helpful when we are trying to come up with the recursive relation.
	 * At first, I think the sub problem should look like: maxSubArray(int A[], int i, int j), which means the 
	 * maxSubArray for A[i: j]. In this way, our goal is to figure out what maxSubArray(A, 0, A.length - 1) is. 
	 * However, if we define the format of the sub problem in this way, it's hard to find the connection from 
	 * the sub problem to the original problem(at least for me). In other words, I can't find a way to divided 
	 * the original problem into the sub problems and use the solutions of the sub problems to somehow create 
	 * the solution of the original one.
	 * So I change the format of the sub problem into something like: maxSubArray(int A[], int i), which means 
	 * the maxSubArray for A[0:i ] which must has A[i] as the end element. Note that now the sub problem's format 
	 * is less flexible and less powerful than the previous one because there's a limitation that A[i] should be 
	 * contained in that sequence and we have to keep track of each solution of the sub problem to update the 
	 * global optimal value. However, now the connect between the sub problem & the original one becomes clearer:
	 * maxSubArray(A, i) = maxSubArray(A, i - 1) > 0 ? maxSubArray(A, i - 1) : 0 + A[i]; 
	 */
    public int maxSubArray(int[] nums) {
        int len = nums.length;
        // dp[i] means the maximum subarray ending with A[i];
        int[] dp = new int[len];
        dp[0] = nums[0];
        int max = dp[0];
        for(int i = 1; i < len; i++) {
        	dp[i] = nums[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
        	max = Math.max(dp[i], max);
        }
        return max;
    }
    
    
    /**
     * Solution 2: DP Solution with O(1) extra space
     * Refer to
     * https://discuss.leetcode.com/topic/6413/dp-solution-some-thoughts/13
     * To calculate sum(0,i), you have 2 choices: either adding sum(0,i-1) to a[i], or not. 
     * If sum(0,i-1) is negative, adding it to a[i] will only make a smaller sum, so we add only if it's non-negative.
     * sum(0,i) = a[i] + (sum(0,i-1) < 0 ? 0 : sum(0,i-1))
     * We can then use O(1) space to keep track of the max sum(0, i) so far.
     */
    public int maxSubArray2(int[] nums) {
    	int len = nums.length;
    	int max = nums[0];
    	int sum = nums[0];
    	for(int i = 1; i < len; i++) {
    		if(sum < 0) {
    			sum = nums[i];
    		} else {
    			sum += nums[i];
    		}
    		max = Math.max(sum, max);
    	}
    	return max;
    }
    
    
    /**
     * Solution 3: Divide and conquer:
     * Refer to
     * https://discuss.leetcode.com/topic/4175/share-my-solutions-both-greedy-and-divide-and-conquer/2
     * http://www.cnblogs.com/grandyang/p/4377150.html
     * 题目还要求我们用分治法Divide and Conquer Approach来解，这个分治法的思想就类似于二分搜索法，我们需要把数组一分为二，
     * 分别找出左边和右边的最大子数组之和，然后还要从中间开始向左右分别扫描，求出的最大值分别和左右两边得出的最大值相比较取
     * 最大的那一个，代码如下：
     */
    public int maxSubArray3(int[] nums) {
    	if(nums.length == 0) {
    		return 0;
    	}
    	return helper(nums, 0, nums.length - 1);
    }
    
    public int helper(int[] nums, int left, int right) {
    	// Base case
    	if(left >= right) {
    		return nums[left];
    	}
    	int mid = left + (right - left) / 2;
    	int lmax = helper(nums, left, mid - 1);
    	int rmax = helper(nums, mid + 1, right);
    	int mmax = nums[mid];
    	int t = mmax;
    	for(int i = mid - 1; i >= left; --i) {
    		t += nums[i];
    		mmax = Math.max(mmax, t);
    	}
    	t = mmax;
    	for(int i = mid + 1; i <= right; ++i) {
    		t += nums[i];
    		mmax = Math.max(mmax, t);
    	}
    	return Math.max(mmax, Math.max(lmax, rmax));
    }
    
    public static void main(String[] args) {
    	MaximumSubarray m = new MaximumSubarray();
    	int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    	int result = m.maxSubArray(nums);
    	System.out.println(result);
    }
}
