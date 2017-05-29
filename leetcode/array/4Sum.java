import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/4sum/#/description
 * Given an array S of n integers, are there elements a, b, c, and d in S such that 
 * a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
 * Note: The solution set must not contain duplicate quadruplets.
 * For example, given array S = [1, 0, -1, 0, -2, 2], and target = 0.
	
	A solution set is:
	[
	  [-1,  0, 0, 1],
	  [-2, -1, 1, 2],
	  [-2,  0, 0, 2]
	]

 * 
 * Solution
 * https://segmentfault.com/a/1190000003740669
 * 双指针法
 * 复杂度
 * 时间 O(N^3) 空间 O(1)
 * 思路
 * 和3Sum的思路一样，在计算4Sum时我们可以先选一个数，然后在剩下的数中计算3Sum。
 * 而计算3Sum则同样是先选一个数，然后再剩下的数中计算2Sum。
 */
public class FourSum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for(int i = 0; i < nums.length - 3; i++) {
            if(i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            List<List<Integer>> res = threeSum(nums, i, target - nums[i]);
            result.addAll(res);
        }
        return result;
    }
    
    public List<List<Integer>> threeSum(int[] nums, int i, int newTarget) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // Similar logic as when construct threeSum based on twoSum,
        // the start point of should be m = i + 1
        for(int m = i + 1; m < nums.length - 2; m++) {
            if(m > i + 1 && nums[m] == nums[m - 1]) {
                continue;    
            }
            // Pass both m and i into twoSum for adding both nums[m] and nums[i]
            // into one combination
            List<List<Integer>> res = twoSum(nums, m, i, newTarget - nums[m]);
            result.addAll(res);
        }
        return result;
    }
    
    public List<List<Integer>> twoSum(int[] nums, int m, int i, int newTarget) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int left = m + 1;
        int right = nums.length - 1;
        while(left < right) {
            if(nums[left] + nums[right] == newTarget) {
                List<Integer> curr = new ArrayList<Integer>();
                // Adding both nums[i] and nums[m]
                curr.add(nums[i]);
                curr.add(nums[m]);
                curr.add(nums[left]);
                curr.add(nums[right]);
                result.add(curr);
                do {
                    left++;
                } while(left < nums.length && nums[left] == nums[left - 1]);
                do {
                    right--;
                } while(right >= 0 && nums[right] == nums[right + 1]);
            } else if(nums[left] + nums[right] > newTarget) {
                right--;
            } else {
                left++;
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
    	
    }
    
}

