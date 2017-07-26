/**
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Find the minimum element.
 * Notice
 * You may assume no duplicate exists in the array.
*/
public class Solution {
    /**
     * @param nums: a rotated sorted array
     * @return: the minimum number in the array
     */
    public int findMin(int[] nums) {
        // Check null and empty case
        if(nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        int target = nums[end];
        // Find the first item which no larger
        // than the last item in given nums
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(nums[mid] == target) {
                end = mid;   
            } else if(nums[mid] < target) {
                // Different than template, because we
                // are trying to find the first item
                // <= target, when condition satisfy
                // 'nums[mid] < target', which means
                // candidates exist on left(inclusive) 
                // of current 'mid', we need to cut off
                // second half and continue search first
                // half, that's why use 'end = mid' not
                // as normal as 'start = mid' in
                // FirstPositionOfTarget.java, because
                // in normal case we are finding 'target'
                // itself, not the item exist in rnage 
                // before 'target'
                end = mid;
            } else if(nums[mid] > target) {
                start = mid;
            }
        }
        // Also need to change final check
        // E.g
        // If given {1, 2, 3}, final start = 0, end = 1,
        // which will go into nums[0] <= 3 branch,
        // return nums[start] = nums[0] = 1
        // If given {4, 1, 2, 3}, final start = 0, end = 1,
        // which will go into nums[0] > 3 branch,
        // return nums[end] = nums[1] = 1
        // More detail, why we can directly return
        // nums[end] if 'nums[start] <= target' ?
        // Because this 'nums[start]' must be the
        // peak value after rotation, which the very
        // previous item of the bottom value, like 4
        // is the peak, and just before bottom as 1,
        // what we need to do is return bottom one,
        // which exactly the first item no larger than
        // the last item as 3 here
        if(nums[start] <= target) {
            return nums[start];
        } else {
            return nums[end];
        }
    }
}
