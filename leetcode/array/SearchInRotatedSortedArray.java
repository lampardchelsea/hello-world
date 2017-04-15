// Refer to
// https://leetcode.com/problems/search-in-rotated-sorted-array/#/description
/**
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 * You may assume no duplicate exists in the array.
*/

// Solution:
// Refer to
// https://discuss.leetcode.com/topic/3538/concise-o-log-n-binary-search-solution
public class Solution {
    public int search(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        while(lo < hi) {
            int mid = lo + (hi - lo)/2;
            // find the index of the smallest value using binary search.
            // Loop will terminate since mid < hi, and lo or hi will shrink by at least 1.
            // Proof by contradiction that mid < hi: if mid==hi, then lo==hi and loop would have been terminated.
            if(nums[mid] > nums[hi]) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        // lo==hi is the index of the smallest value and also the number of places rotated,
        // record lo index into rotateIndex
        int rotateIndex = lo;
        lo = 0;
        hi = nums.length - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo)/2;
            // Every loop will re-calculate realMid based on new mid value
            int realMid = (rotateIndex + mid) % (nums.length);
            if(nums[realMid] < target) {
                lo = mid + 1;
            } else if(nums[realMid] > target) {
                hi = mid - 1;
            } else {
                return realMid;
            }
        }
        return -1;
    }
}
