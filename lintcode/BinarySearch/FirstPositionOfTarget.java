/**
 * For a given sorted array (ascending order) and a target number, find the first index of 
 * this number in O(log n) time complexity.
 * If the target number does not exist in the array, return -1.
 * Have you met this question in a real interview?
 * Example
 * If the array is [1, 2, 3, 3, 4, 5, 10], for given target 3, return 2.
*/
class Solution {
    /**
     * @param nums: The integer array.
     * @param target: Target to find.
     * @return: The first position of target. Position starts from 0.
     */
    public int binarySearch(int[] nums, int target) {
        // Check on null and empty case
        if(nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(nums[mid] == target) {
                // Not directly return here, we need to find first happening
                // of one value in array, always keep removing the second
                // half of current subset, its tendency is try to find most
                // previous one even we already find one item matching target
                // (Since there are duplicates, so even find a matching item
                // still need continuous process previous section, should
                // not return directly)
                end = mid;
            } else if(nums[mid] < target) {
                // Use this template should not write as 
                // 'start = mid + 1' or 'end = mid - 1',
                // because if we find the 'mid' position
                // item is the last one matching target,
                start = mid;
            } else if(nums[mid] > target) {
                end = mid;
            }
        }
        if(nums[start] == target) {
            return start;
        }
        if(nums[end] == target) {
            return end;
        }
        return -1;
    }
}
