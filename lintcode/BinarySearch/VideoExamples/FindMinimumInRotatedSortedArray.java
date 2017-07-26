/**
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Find the minimum element.
 * Notice
 * You may assume no duplicate exists in the array.
 *
 * Solution
 * Refer to
 * http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LintCode-159-Find-Minimum-in-Rotated-Sorted-Array
 * 思路
 * 这题的难点是找到要搜索的那个目标。 因为给的数据是一个 shifted 之后的 sorted array，那么我我们来分析一下可能出现的几种情况。

    0 1 2 3 4 5 7 本身就是没有 shift 过的 sorted array
    3 4 5 7 0 1 2 大概 shift 了一半的 array
    1 2 3 4 5 7 0 只 shift 了一位，最小值在 array 最后

 * 我们可以把 shift 之后的 aray 想象成这样一个图形，我们可以看到，shift 之后的 sorted array，前半部分 的最小值肯定是比后半
 * 部分的最大值要大，即后半部分无论如何也不会大于前半部分的最小值。

          7
        5 
      4
    3
    ---------------
                 2
              1
            0

 * 那么我们可以发现我们要找的最小值，肯定是后半部分的第一个值。 那么我们需要找的值就是 第一个比 最后一个值（后半部分最大值） 要小的值。
 * 再来看看前面几种情况，我们这个思路是否适用。
   
   0 1 2 3 4 5 7 本身就是 sorted 的，那么第一个比 7 小的值就是0。
   3 4 5 7 0 1 2 shift 过的 array，那么第一个比2小的值就是0
   1 2 3 4 5 7 0 这个情况，我们的最小值同时也是最后一个值，那么我们发现这个思路就要稍微改一改，要包括等于最后一个值得情况。所以我们最终要找的值就是
   第一个 小于或者等于 最后一个数的数
   妈的怎么感觉这么别扭，中文语法学的不好？？？
 * The first element that's smaller or equal to the last element
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
