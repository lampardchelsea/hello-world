// Refer to
// https://leetcode.com/problems/search-in-rotated-sorted-array/#/description
/**
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 * You may assume no duplicate exists in the array.
*/

// Solution 1:
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

// Solution 2:
// Refer to
// https://segmentfault.com/a/1190000003811864
/**
 * 二分法
   复杂度
   时间 O(logN) 空间 O(1)
   思路
   平时我们二分法的时候，直接判断下中点和目标的关系，就可以知道目标在左半部分还是右半部份了，这背后其实隐含一个假设，
   那就是从起点到终点是一段有序的序列。而本题中，如果我们还想继续做二分法，这个假设就不存在了，因为从起点到终点有可
   能有个断片！不过，旋转有序数组有一个特点，假设本身是个升序序列，从左向右。如果左边的点比右边的点小，说明这两个点
   之间是有序的，不存在旋转点。如果左边的点比右边的大，说明这两个点之间有一个旋转点，导致了不再有序。因为只有一个旋转
   点，所以一分为二后，肯定有一半是有序的。所以，我们还是可以用二分法，不过要先判断左半边有序还是右半边有序。如果左
   半边有序，则直接将目标和左半边的边界比较，就知道目标在不在左半边了，如果不在左半边肯定在右半边。同理，如果右半边
   有序，则直接将目标和右半边的边界比较，就知道目标在不在右半边了，如果不在右半边肯定在左半边。这样就完成了二分。
   注意
   这里要注意二分时候的边界条件，一般来说我们要找某个确定的目标时，边界条件为：
    min = 0, max = length - 1;
    if(min <= max){
        min = mid + 1
        max = mid - 1
    }
   另外，判断左半边是否有序的条件是：nums[min] <= nums[mid]，而判断是否在某一个有序区间，则要包含那个区间较远的那一头
*/
public class Solution {
    public int search(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if(nums[mid] == target) {
                return mid;
            }
            if(nums[lo] <= nums[mid]) {
                if(nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else {
                if(nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }
}


