/**
Refer to
https://leetcode.com/problems/largest-number-at-least-twice-of-others/
In a given integer array nums, there is always exactly one largest element.

Find whether the largest element in the array is at least twice as much as every other number in the array.

If it is, return the index of the largest element, otherwise return -1.

Example 1:
Input: nums = [3, 6, 1, 0]
Output: 1
Explanation: 6 is the largest integer, and for every other number in the array x,
6 is more than twice as big as x.  The index of value 6 is 1, so we return 1.

Example 2:
Input: nums = [1, 2, 3, 4]
Output: -1
Explanation: 4 isn't at least as big as twice the value of 3, so we return -1.

Note:
nums will have a length in the range [1, 50].
Every nums[i] will be an integer in the range [0, 99].
*/
class Solution {
    public int dominantIndex(int[] nums) {
        int idx = -1;
        int max = -1;
        int second_max = -1;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] > max) {
                second_max = max;
                max = nums[i];
                idx = i;
            } else if(nums[i] > second_max) {
                second_max = nums[i];
            }
        }
        // To avoid divide by 0 must use multiply instead, similar to 1232. Check If It Is a Straight Line
        return max >= second_max * 2 ? idx : -1;
    }
}
