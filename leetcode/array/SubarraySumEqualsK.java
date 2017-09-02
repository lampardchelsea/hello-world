/**
 * Refer to
 * https://leetcode.com/problems/subarray-sum-equals-k/description/
 * Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.
    Example 1:
    Input:nums = [1,1,1], k = 2
    Output: 2
    Note:
    The length of the array is in range [1, 20,000].
    The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
 * 
 * Solution
 * https://leetcode.com/problems/subarray-sum-equals-k/solution/#approach-1-brute-force-time-limit-exceeded
 *
 * Complexity Analysis
 * Time complexity : O(n^2)
   Considering every possible subarray takes O(n^2)time. 
   Finding out the sum of any subarray takes O(1) time after the initial processing of O(n) 
   for creating the cumulative sum array.
   Space complexity : O(n). Cumulative sum array sumsum of size n+1 is used.
*/

// Solution 1: preSum way
/**
 Approach #2 Using Cummulative sum [Accepted]
 Algorithm
 Instead of determining the sum of elements everytime for every new subarray considered, we can make use of a 
 cumulative sum array , sumsum. Then, in order to calculate the sum of elements lying between two indices, we 
 can subtract the cumulative sum corresponding to the two indices to obtain the sum directly, instead of 
 iterating over the subarray to obtain the sum.
 In this implementation, we make use of a cumulative sum array, sumsum, such that sum[i]sum[i] is used to 
 store the cumulative sum of numsnums array upto the element corresponding to the (i-1)th index. Thus, to 
 determine the sum of elements for the subarray nums[i:j]nums[i:j], we can directly use sum[j+1] - sum[i].
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        // Create preSum array
        int[] sum = new int[nums.length + 1];
        for(int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }
        int count = 0;
        for(int start = 0; start < nums.length; start++) {
            for(int end = start + 1; end <= nums.length; end++) {
                if(sum[end] - sum[start] == k) {
                    count++;
                }
            }
        }
        return count;
    }
}


// Solution 2: 
/**
 Approach #3 Without space [Accepted]
 Algorithm
 Instead of considering all the startstart and endend points and then finding the sum for each subarray 
 corresponding to those points, we can directly find the sum on the go while considering different end
 points. i.e. We can choose a particular start point and while iterating over the end points, 
 we can add the element corresponding to the end point to the sum formed till now. Whenver the sum
 equals the required kk value, we can update the count value. We do so while iterating over all 
 the end indices possible for every start index. Whenver, we update the start index, we need to 
 reset the sum value to 0.
 Complexity Analysis

Time complexity : O(n^2). We need to consider every subarray possible
Space complexity : O(1) Constant space is used.
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        for(int start = 0; start < nums.length; start++) {
            int sum = 0;
            for(int end = start; end < nums.length; end++) {
                sum += nums[end];
                if(sum == k) {
                    count++;
                }                
            }
        }
        return count;
    }
}
