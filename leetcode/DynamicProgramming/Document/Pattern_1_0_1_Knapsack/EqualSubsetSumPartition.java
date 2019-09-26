/**
 Refer to
 https://www.educative.io/courses/grokking-dynamic-programming-patterns-for-coding-interviews/3jEPRo5PDvx
 Given a set of positive numbers, find if we can partition it into two subsets such that the sum of 
 elements in both the subsets is equal.
 Example 1:
 Input: {1, 2, 3, 4}
 Output: True
 Explanation: The given set can be partitioned into two subsets with equal sum: {1, 4} & {2, 3}
 Example 2:
 Input: {1, 1, 3, 4, 7}
 Output: True
 Explanation: The given set can be partitioned into two subsets with equal sum: {1, 3, 4} & {1, 7}
 Example 3:
 Input: {2, 3, 4, 6}
 Output: False
 Explanation: The given set cannot be partitioned into two subsets with equal sum.
*/

// Solution 1: Native DFS (TLE)
/**
 Basic Solution
 This problem follows the 0/1 Knapsack pattern. A basic brute-force solution could be to try all combinations 
 of partitioning the given numbers into two sets to see if any pair of sets has an equal sum. 
 Assume if S represents the total sum of all the given numbers, then the two equal subsets must have a sum 
 equal to S/2. This essentially transforms our problem to: "Find a subset of the given numbers that has 
 a total sum of S/2".
*/
class Solution {
    public boolean canPartition(int[] nums) {
        if(nums.length == 0) {
            return false;
        }
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i]; 
        }         
        // if 'sum' is a an odd number, we can't have two subsets with equal sum
        if(sum % 2 != 0) {
            return false;  
        }
        return helper(nums, sum/2, 0);
    }
    
    private boolean helper(int[] nums, int target, int index) {
        if(target == 0) {
            return true;
        }
        if(target != 0 && index >= nums.length) {
            return false;
        }
        if(nums[index] <= target) {
            if(helper(nums, target - nums[index], index + 1)) {
                return true;
            }
        }
        if(helper(nums, target, index + 1)) {
            return true;
        }
        return false;
    }
}
