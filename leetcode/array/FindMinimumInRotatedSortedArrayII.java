/**
 * Refer to
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/#/description
 * Follow up for "Find Minimum in Rotated Sorted Array":
 * What if duplicates are allowed?
 * Would this affect the run-time complexity? How and why?
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Find the minimum element.
 * The array may contain duplicates.
 * 
 * Solution
 * https://segmentfault.com/a/1190000003488815
 * 二分递归法
 * 复杂度
 * 时间 O(N) 空间 O(N) 递归栈空间
 * 思路
 * 如果有重复的话，一旦中间和右边是相等的，就无法确定是否在左半边还是右半边，这时候我们必须对两边都递归求解。
 * 如果nums[max]大于等于nums[mid]，则右边有可能有，如果nums[max]小于等于nums[mid]，则左边有可能有。
 * 注意
 * 要先将左和右的最小值初始化最大整数，然后求解后，最后返回其中较小的那个
 * 
 * https://leetcode.com/submissions/detail/100222655/
 */
public class FindMinimumInRotatedSortedArrayII {
    public int findMin(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }
    
    public int helper(int[] nums, int min, int max) {
        if(min == max) {
            return nums[min];
        }
        //int leftMin = 0, rightMin = 0;
        // E.g If randomly set up as 0, 0 and use {1, 3} to test, 
        // it will return as 0, which suppose to return 1
        // Important: To find the minimum result on each side, must
        // initialize two variables as Integer.MAX_VALUE.
        // 先将右边和左边可能找到的值都初始化为最大
        int rightMin = Integer.MAX_VALUE, leftMin = Integer.MAX_VALUE;
        int mid = min + (max - min)/2;
        if(nums[mid] >= nums[max]) {
            rightMin = helper(nums, mid + 1, max);
        } 
        if(nums[mid] <= nums[max]){
            leftMin = helper(nums, min, mid);
        }
        return Math.min(leftMin, rightMin);
    }
    
    public static void main(String[] args) {
    	
    }
}

