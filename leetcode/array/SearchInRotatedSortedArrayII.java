// Refer to
// https://leetcode.com/problems/search-in-rotated-sorted-array-ii/#/description
/**
 * Follow up for "Search in Rotated Sorted Array":
 * What if duplicates are allowed?
 * Would this affect the run-time complexity? How and why?
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Write a function to determine if a given target is in the array.
 * The array may contain duplicates.
*/

// Solution
// Refer to
// https://segmentfault.com/a/1190000003811864
/**
 * 二分法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 如果可能有重复，那我们之前判断左右部分是否有序的方法就失效了，因为可能有这种13111情况，
 * 虽然起点小于等于中间，但不代表右边就不是有序的，因为中点也小于等于终点，所有右边也是有序的。
 * 所以，如果遇到这种中点和两边相同的情况，我们两边都要搜索。
*/
public class Solution {
    public boolean search(int[] nums, int target) {
        return helper(nums, 0, nums.length - 1, target);
    }
    
    public boolean helper(int[] nums, int lo, int hi, int target) {
        // Base case: if not match --> min <= max, return false
        if(lo > hi) {
            return false;
        }
        int mid = lo + (hi - lo)/2;
        if(nums[mid] == target) {
            return true;
        }
        boolean left = false;
        boolean right = false;
        // If left part in order
        if(nums[lo] <= nums[mid]) {
            // Check if target in inorder left part
            if(nums[lo] <= target && target < nums[mid]) {
                left = helper(nums, lo, mid - 1, target);
            } else {
                left = helper(nums, mid + 1, hi, target);
            }
        } 
        // If right part in order
        if(nums[mid] <= nums[hi]){
            // Check if target in inorder right part
            if(nums[mid] < target && target <= nums[hi]) {
                right = helper(nums, mid + 1, hi, target);
            } else {
                right = helper(nums, lo, mid - 1, target);
            }
        }
        return left || right;
    }
}
