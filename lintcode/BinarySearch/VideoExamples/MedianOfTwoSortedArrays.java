/**
 * Refer to
 * https://leetcode.com/problems/median-of-two-sorted-arrays/description/
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
    Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
    Example 1:
    nums1 = [1, 3]
    nums2 = [2]

    The median is 2.0
    Example 2:
    nums1 = [1, 2]
    nums2 = [3, 4]

    The median is (2 + 3)/2 = 2.5
 * 
 * Solution
 * https://discuss.leetcode.com/topic/28602/concise-java-solution-based-on-binary-search/32?page=2
   This is a great solution. But it is a little hard to totally understand.
    Thus I comment on it and put my analysis of it as following.
    Hope it helps.

    ANALYSIS:
    In order to solve this question, we need to first understand what a median is. A median is the middle value of a dataset.
    Since we have 2 seperately sorted array in this question, to find the middle value is somewhat complicated. However, 
    keep in mind that we do not care about the actual value of the numbers, what we want is the middle point from the 
    combination of 2 arrays. In other words, we are looking for the middle index of the 2 arrays. Thus approach like 
    binary search could be employed.
    Based on the fact that the 2 arrays are sorted seperatedly, we could try to get the submedian of the 2 arrays in each 
    round. Than compare them. And the basic idea is that the left half of the array with a smaller submedian can never 
    contains the common median.

    if (mid1 < mid2) keep nums1.right + nums2
    else keep nums1 + nums2.right
    Thus a O(log(m + n)) is like following:
*/
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        // left half of the combined median
        int l = (m + n + 1) / 2;
        // right half of the combined median
        int r = (m + n + 2) / 2;
        // If the nums1.length + nums2.length is odd, the 2 function will return the same number
        // else if nums1.length + nums2.length is even, the 2 function will return the left number
        // and right number that make up a median
        return (getKth(nums1, 0, nums2, 0, l) + getKth(nums1, 0, nums2, 0, r)) / 2.0;
    }

    private double getKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
        // If nums1 is exhausted, return kth number in nums2
        // E.g nums1 = [], nums2 = [2]
        if(start1 > nums1.length - 1) {
            return nums2[start2 + k - 1];
        }
        // If nums2 is exhausted, return kth number in nums1
        // E.g nums1 = [2], nums2 = []
        if(start2 > nums2.length - 1) {
            return nums1[start1 + k - 1];
        }
        // If k == 1, return first number
        // Since nums1 and nums2 is sorted, the smaller one among the nums1 and nums2 is the first one
        // E.g nums1 = [1, 3], nums2 = [2]
        if(k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }
        int mid1 = Integer.MAX_VALUE;
        int mid2 = Integer.MAX_VALUE;
        if(start1 + k / 2 - 1 < nums1.length) {
            mid1 = nums1[start1 + k / 2 - 1];
        }
        if(start2 + k / 2 - 1 < nums2.length) {
            mid2 = nums2[start2 + k / 2 - 1];
        }
        // Throw away half of the array from nums1 or nums2, and cut k in half
        if(mid1 < mid2) {
            // nums1.right + nums2
            return getKth(nums1, start1 + k / 2, nums2, start2, k - k / 2);
        } else {
            // nums1 + nums2.right
            return getKth(nums1, start1, nums2, start2 + k / 2, k - k / 2);
        }
    }
}
