import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/3sum/#/description
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? 
 * Find all unique triplets in the array which gives the sum of zero.
 * 
 * Note: The solution set must not contain duplicate triplets.
 * For example, given array S = [-1, 0, 1, 2, -1, -4],
	A solution set is:
	[
	  [-1, 0, 1],
	  [-1, -1, 2]
	]
 * 
 * Solution
 * https://discuss.leetcode.com/topic/8125/concise-o-n-2-java-solution
 * 
 * https://segmentfault.com/a/1190000002986095
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/TwoSumForThreeAndFourSum.java
 * 排序双指针法 Sorting with Two Pointers
 * 复杂度
 * O(n)空间 O(nlogn)时间
 * 思路
 * 首先将双指针指向头部与尾部元素，进行迭代。如果双指针指向元素之和大于目标和，则将尾部指针向前移一位，
 * 反之则将头部指针向后移一位，直到双指针指向元素之和等于目标和，并且必须跳过所有与当前pair值相同的
 * 值，这样可以保证取得的combination是unique的
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
        	} else if(nums[left] + nums[right] > target) {
        		right--;
        	} else {
        		left++;
        	}
        }
        return result;
    }
 * 
 * 
 * https://segmentfault.com/a/1190000003740669
 * 双指针法
 * 复杂度
 * 时间 O(N^2) 空间 O(1)
 * 思路
 * 3Sum其实可以转化成一个2Sum的题，我们先从数组中选一个数，并将目标数减去这个数，得到一个新目标数。然后再在剩下的数中
 * 找一对和是这个新目标数的数，其实就转化为2Sum了。为了避免得到重复结果，我们不仅要跳过重复元素，而且要保证2Sum找的
 * 范围要是在我们最先选定的那个数之后的。
 */
public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // Since we should reserve 2 positions for 2 numbers used
        // for 'twoSum', so we only need to find nums[i] at most
        // (nums.length - 2) times, range [0, nums.length - 3](inclusively)
        for(int i = 0; i < nums.length - 2; i++) {
            // Skip all duplicates of nums[i]
            if(i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            List<List<Integer>> res = twoSum(nums, i, 0 - nums[i]);
            // Must use 'addAll' as 'twoSum' returns collections
            result.addAll(res);
        }
        return result;
    }
    
    private List<List<Integer>> twoSum(int[] nums, int i, int target) {
        // To make sure twoSum apply on range after the position i(after nums[i])
        // not start with 'left = 0' like twoSum
        int left = i + 1;
        // The right range is still (nums.length - 1)
        int right = nums.length - 1;
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        while(left < right) {
            if(nums[left] + nums[right] == target) {
                List<Integer> curr = new ArrayList<Integer>();
                // Don't forget add nums[i]
                curr.add(nums[i]);
                curr.add(nums[left]);
                curr.add(nums[right]);
                res.add(curr);
                do {
                    left++;
                } while(left < nums.length && nums[left] == nums[left - 1]);
                do {
                    right--;
                } while(right >= 0 && nums[right] == nums[right + 1]);
            } else if(nums[left] + nums[right] > target) {
                right--;
            } else {
                left++;
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
    	
    }
    
}


