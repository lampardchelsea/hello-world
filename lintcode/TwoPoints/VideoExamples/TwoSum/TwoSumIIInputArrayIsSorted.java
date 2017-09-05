/**
 * Refer to
 * http://www.lintcode.com/en/problem/two-sum-input-array-is-sorted/
 * Given an array of integers that is already sorted in ascending order, find two numbers 
   such that they add up to a specific target number.
   The function twoSum should return indices of the two numbers such that they add up to the 
   target, where index1 must be less than index2. Please note that your returned answers 
   (both index1 and index2) are not zero-based.
   Notice
   You may assume that each input would have exactly one solution.
    Have you met this question in a real interview? Yes
    Example
    Given nums = [2, 7, 11, 15], target = 9
    return [1, 2]
 *
 * Solution
 * https://github.com/lampardchelsea/hello-world/blob/f598027b1c7a0ab2757ee86c30eea9e0be7961f1/leetcode/array/TwoSumII.java
 * 
*/
public class Solution {
    /*
     * @param nums: an array of Integer
     * @param target: target = nums[index1] + nums[index2]
     * @return: [index1 + 1, index2 + 1] (index1 < index2)
     */
    public int[] twoSum(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return new int[0];
        }
        int[] result = new int[2];
        int i = 0;
        int j = nums.length - 1;
        while(i < j) {
            int temp = nums[i] + nums[j];
            if(temp == target) {
                result[0] = i + 1;
                result[1] = j + 1;
                //break;
            } else if(temp < target) {
                i++;
            } else if(temp > target) {
                j--;
            }
        }
        return result;
    }
}
