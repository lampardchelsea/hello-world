/**
 * Refer to
 * https://leetcode.com/problems/find-peak-element/#/description
 * A peak element is an element that is greater than its neighbors.
 * Given an input array where num[i] ≠ num[i+1], find a peak element and return its index.
 * The array may contain multiple peaks, in that case return the index to any one of the 
 * peaks is fine.You may imagine that num[-1] = num[n] = -∞.
 * For example, in array [1, 2, 3, 1], 3 is a peak element and your function should 
 * return the index number 2.
 * click to show spoilers.
 * Note:
 * Your solution should be in logarithmic complexity.
 *
 * Solution
 * https://segmentfault.com/a/1190000003488794
 * 二分搜索
 * 复杂度
 * 时间 O(logN) 空间 O(1)
 * 思路
 * 题目要求时间复杂度为O(logN)，logN时间的题目一般都是Heap，二叉树，分治法，二分搜索这些很“二”解法。
 * 这题是找特定元素，基本锁定二分搜索法。我们先取中点，由题意可知因为两端都是负无穷，有上坡就必定有一个峰，
 * 我们看中点的左右两边大小，如果向左是上坡，就抛弃右边，如果向右是上坡，就抛弃左边。直到两边都小于中间，就是峰了。
 * 
 * https://discuss.leetcode.com/topic/5848/o-logn-solution-javacode
 * This problem is similar to Local Minimum. And according to the given condition, 
 * num[i] != num[i+1], there must exist a O(logN) solution. So we use binary search for 
 * this problem.
    If num[i-1] < num[i] > num[i+1], then num[i] is peak
    If num[i-1] < num[i] < num[i+1], then num[i+1...n-1] must contains a peak
    If num[i-1] > num[i] > num[i+1], then num[0...i-1] must contains a peak
    If num[i-1] > num[i] < num[i+1], then both sides have peak
    (n is num.length)
 */
public class FindPeakElement {
    public int findPeakElement(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }
    
    public int helper(int[] nums, int lo, int hi) {
        if(lo == hi) {
            return lo;
        } else if(lo + 1 == hi) {
            if(nums[lo] > nums[hi]) {
                return lo;
            } else {
                return hi;
            }
        } else {
            int mid = lo + (hi - lo)/2;
            if(nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid;
            } else if(nums[mid - 1] > nums[mid] && nums[mid] > nums[mid + 1]) {
                return helper(nums, lo, mid - 1);
            } else {
                return helper(nums, mid + 1, hi);
            }
        }
    }
    
    public static void main(String[] args) {
    	
    }
}

