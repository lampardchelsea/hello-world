/**
Refer to
https://www.cnblogs.com/Dylan-Java-NYC/p/12076239.html
Given an array nums sorted in non-decreasing order, and a number target, return True if and only if target is a majority element.

A majority element is an element that appears more than N/2 times in an array of length N.

Example 1:
Input: nums = [2,4,5,5,5,5,5,6,6], target = 5
Output: true
Explanation: 
The value 5 appears 5 times and the length of the array is 9.
Thus, 5 is a majority element because 5 > 9/2 is true.

Example 2:
Input: nums = [10,100,101,101], target = 101
Output: false
Explanation: 
The value 101 appears 2 times and the length of the array is 4.
Thus, 101 is not a majority element because 2 > 4/2 is false.

Note:
1 <= nums.length <= 1000
1 <= nums[i] <= 10^9
1 <= target <= 10^9
*/

// Solution 1: Binary Search
// Same way as
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/BinarySearch/ElementAppearingMoreThan25%25InSortedArray.java
// Since a sorted array, use binary search to find the first position of given element, then check half of elments later is same value or not
class Solution {
    public boolean isMajorityElement(int[] nums, int target) {
        int n = nums.length;
        int lo = 0;
        int hi = n - 1;
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        if(lo + n / 2 <= n && nums[lo + n / 2] == target) {
            return true;
        }
        return false;
    }
}





