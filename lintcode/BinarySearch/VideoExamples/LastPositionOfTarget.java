/**
 * Find the last position of a target number in a sorted array. Return -1 if target does not exist.
    Example
    Given [1, 2, 2, 4, 5, 5].
    For target = 2, return 2.
    For target = 5, return 5.
    For target = 6, return -1.
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
                // Not directly return here, we need to find last happening
                // of one value in array, always keep removing the first
                // half of current subset, its tendency is try to find most
                // later one even we already find one item matching target
                // (Since there are duplicates, so even find a matching item
                // still need continuous process later section, should
                // not return directly)
                start = mid;
            } else if(nums[mid] < target) {
                // Use last position template should not write as 
                // 'start = mid + 1' or 'end = mid - 1',
                // because if we find the 'mid' position
                // item is the last one matching target, and we
                // minus 1, will cause time out exception
                // check lesson2_A for example
                start = mid + 1;
            } else if(nums[mid] > target) {
                end = mid - 1;
            }
        }
        // Check on 'end' first for requirement
        // about find the last position 
        if(nums[end] == target) {
            return end;
        }
        if(nums[start] == target) {
            return start;
        }
        return -1;
    }
}
