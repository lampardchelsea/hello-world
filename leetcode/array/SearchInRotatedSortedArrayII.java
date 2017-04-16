// Refer to
// https://leetcode.com/problems/search-in-rotated-sorted-array-ii/#/description
/**
 * Follow up for "Search in Rotated Sorted Array":
 * What if duplicates are allowed?
 * Would this affect the run-time complexity? How and why?
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Write a function to determine if a given target is in the array.
 * The array may contain duplicates.
*/

// Solution
// Refer to
// https://segmentfault.com/a/1190000003811864
/**
 * 二分法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 如果可能有重复，那我们之前判断左右部分是否有序的方法就失效了，因为可能有这种13111情况，
 * 虽然起点小于等于中间，但不代表右边就不是有序的，因为中点也小于等于终点，所有右边也是有序的。
 * 所以，如果遇到这种中点和两边相同的情况，我们两边都要搜索。
*/
