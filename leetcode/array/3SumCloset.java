import java.util.Arrays;

/**
 * Refer to
 * https://leetcode.com/problems/3sum-closest/#/description
 * Given an array S of n integers, find three integers in S such that the sum is closest 
 * to a given number, target. Return the sum of the three integers. You may assume that 
 * each input would have exactly one solution.
 * 
 * For example, given array S = {-1 2 1 -4}, and target = 1.
 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 * 
 * Solution
 * https://segmentfault.com/a/1190000003740669
 * 双指针法
 * 复杂度
 * 时间 O(N^2) 空间 O(1)
 * 思路
 * 和3Sum的解法一样。在3Sum中，我们只有找到和目标完全一样的时候才返回，但在Closet中，我们要记录一个最小的差值，
 * 并同时记录下这个最小差值所对应的和。
 * 
 * 
 * https://discuss.leetcode.com/topic/5192/java-solution-with-o-n2-for-reference
 * Similar to 3 Sum problem, use 3 pointers to point current element, next element and the last element. 
 * If the sum is less than target, it means we have to add a larger element so next element move to 
 * the next. If the sum is greater, it means we have to add a smaller element so last element move to 
 * the second last element. Keep doing this until the end. Each time compare the difference between 
 * sum and target, if it is less than minimum difference so far, then replace result with it, 
 * otherwise keep iterating.
 * 
 */
public class ThreeSumCloset {
    public int threeSumClosest(int[] nums, int target) {
        int result = nums[0] + nums[1] + nums[nums.length - 1];
        Arrays.sort(nums);
        for(int i = 0; i < nums.length - 2; i++) {
            // The range of 'i' must take care of the situation
            // on 'left' < 'right', which means (i + 1 < nums.length - 1)
            // => i < nums.length - 2
            int left = i + 1;
            int right = nums.length - 1;
            while(left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if(sum > target) {
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    return sum;
                }
                if(Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
    	
    }
}

