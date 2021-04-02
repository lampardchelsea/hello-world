/**
Refer to
https://leetcode.com/problems/search-insert-position/
Given a sorted array of distinct integers and a target value, return the index if the target is found. 
If not, return the index where it would be if it were inserted in order.

Example 1:
Input: nums = [1,3,5,6], target = 5
Output: 2

Example 2:
Input: nums = [1,3,5,6], target = 2
Output: 1

Example 3:
Input: nums = [1,3,5,6], target = 7
Output: 4

Example 4:
Input: nums = [1,3,5,6], target = 0
Output: 0

Example 5:
Input: nums = [1], target = 0
Output: 0

Constraints:
1 <= nums.length <= 104
-104 <= nums[i] <= 104
nums contains distinct values sorted in ascending order.
-104 <= target <= 104
*/

// Solution 1: 
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
35. Search Insert Position [Easy]
Given a sorted array and a target value, return the index if the target is found. If not, return the index 
where it would be if it were inserted in order. You may assume no duplicates in the array.

Example:
Input: [1,3,5,6], 5
Output: 2
Input: [1,3,5,6], 2
Output: 1
Very classic application of binary search. We are looking for the minimal k value satisfying nums[k] >= target, 
and we can just copy-paste our template. Notice that our solution is correct regardless of whether the input 
array nums has duplicates. Also notice that the input target might be larger than all elements in nums and 
therefore needs to placed at the end of the array. That's why we should initialize right = len(nums) instead 
of right = len(nums) - 1.

class Solution:
    def searchInsert(self, nums: List[int], target: int) -> int:
        left, right = 0, len(nums)
        while left < right:
            mid = left + (right - left) // 2
            if nums[mid] >= target:
                right = mid
            else:
                left = mid + 1
        return left
*/

