import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/#/description
 * Given an array of integers that is already sorted in ascending order, find two numbers 
 * such that they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such that they add up to 
 * the target, where index1 must be less than index2. Please note that your returned 
 * answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution and you may not use 
 * the same element twice.
 * Input: numbers={2, 7, 11, 15}, target=9
 * Output: index1=1, index2=2 
 * 
 * Solution
 * https://segmentfault.com/a/1190000002986095
 * 排序双指针法 Sorting with Two Pointers
 * 复杂度
 * O(n)空间 O(nlogn)时间
 * 思路
 * 首先将双指针指向头部与尾部元素，进行迭代。如果双指针指向元素之和大于目标和，则将尾部指针向前移一位，
 * 反之则将头部指针向后移一位，直到双指针指向元素之和等于目标和，
 * 
 */
public class TwoSumForThreeAndFourSum {
    public List<List<Integer>> twoSum(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        while(left < right) {
        	if(nums[left] + nums[right] == target) {
        		List<Integer> curr = new ArrayList<Integer>();
        		curr.add(nums[left]);
        		curr.add(nums[right]);
        		result.add(curr);
        		// To find all unique combination, we must skip all same 
        		// items of both current nums[left] and nums[right]
        		do {
        			left++;
        		} while(left < nums.length && nums[left] == nums[left - 1]);
        		do {
        			right--;
        		} while(right >= 0 && nums[right] == nums[right + 1]);
        		// do-while loop has same effect as below while loop
//                left++;
//                while(left < nums.length && nums[left] == nums[left - 1]) {
//                    left++;
//                }
//                right--;
//                while(right >= 0 && nums[right] == nums[right + 1]) {
//                    right--;
//                }
        	} else if(nums[left] + nums[right] > target) {
        		right--;
        	} else {
        		left++;
        	}
        }
        return result;
    }
}
