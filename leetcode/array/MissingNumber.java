/**
 * Refer to
 * https://leetcode.com/problems/missing-number/#/description
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
 * For example,
 * Given nums = [0, 1, 3] return 2.
 * Note:
 * Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
*/
// Solution 1: Sum
// Refer to
// https://discuss.leetcode.com/topic/23427/3-different-ideas-xor-sum-binary-search-java-code
public class Solution {
    public int missingNumber(int[] nums) {
        int sum = 0;
        int len = nums.length;
        for(int i = 0; i < len; i++) {
            sum += nums[i];
        }
        // Because the original (0, 1, 2... n) array need to added
        // based on (0 + n) * (n + 1) / 2, and n = nums.length = len
        int a = (len + 1) * len / 2;
        return a - sum;
    } 
}
