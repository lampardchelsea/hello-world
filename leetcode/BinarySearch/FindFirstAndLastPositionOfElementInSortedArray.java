/**
Refer to
https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.

If target is not found in the array, return [-1, -1].

Follow up: Could you write an algorithm with O(log n) runtime complexity?

Example 1:
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]

Example 2:
Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]

Example 3:
Input: nums = [], target = 0
Output: [-1,-1]

Constraints:
0 <= nums.length <= 105
-109 <= nums[i] <= 109
nums is a non-decreasing array.
-109 <= target <= 109
*/

// Solution 1: Binary Search
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/BinarySearch/VideoExamples/FirstPositionOfTarget.java
// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/BinarySearch/VideoExamples/LastPositionOfTarget.java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first_position = findFirst(nums, target);
        int last_position = findLast(nums, target);
        return new int[] {first_position, last_position};
    }
    
    public int findFirst(int[] nums, int target) {
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
                // For first position template, 'start = mid - 1' or
                // 'end = mid - 1' are both fine, but this is quite
                // different when come to last position template
                start = mid;
            } else if(nums[mid] > target) {
                end = mid;
            }
        }
        // Check on 'start' first for requirement
        // about find the first position 
        if(nums[start] == target) {
            return start;
        }
        if(nums[end] == target) {
            return end;
        }
        return -1;
    }
    
    public int findLast(int[] nums, int target) {
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
                start = mid + 1; // follow template we can use 'start = mid' here
            } else if(nums[mid] > target) {
                end = mid - 1; // follow template we can use 'end = mid' here
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

