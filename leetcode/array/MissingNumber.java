/**
 * Refer to
 * https://leetcode.com/problems/missing-number/#/description
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
 * For example,
 * Given nums = [0, 1, 3] return 2.
 * Note:
 * Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
*/
// Solution 1: Sum
// Refer to
// https://discuss.leetcode.com/topic/23427/3-different-ideas-xor-sum-binary-search-java-code
public class Solution {
    public int missingNumber(int[] nums) {
        int sum = 0;
        int len = nums.length;
        for(int i = 0; i < len; i++) {
            sum += nums[i];
        }
        // Because the original (0, 1, 2... n) array need to added
        // based on (0 + n) * (n + 1) / 2, and n = nums.length = len
        int a = (len + 1) * len / 2;
        return a - sum;
    } 
}


// Solution 2: Binary Search
// Refer to
// https://discuss.leetcode.com/topic/23427/3-different-ideas-xor-sum-binary-search-java-code/26
public class Solution {
    public int missingNumber(int[] nums) {
        // Refer to
        // Will Arrays.sort() increase time complexity and space time complexity?
        // http://stackoverflow.com/questions/22571586/will-arrays-sort-increase-time-complexity-and-space-time-complexity
        Arrays.sort(nums);
        int len = nums.length;
        int lo = 0;
        int hi = len - 1;
        while(lo <= hi) {
            /**
             * Refer to
             * https://discuss.leetcode.com/topic/23427/3-different-ideas-xor-sum-binary-search-java-code/33
             * If the nums is already sorted, we can use binary search to find out the missing element.
             * And the time complexity is O(logN), better than the XOR solution O(N).
             * The logic behind the binary search solution is:
             * If nums[index] > index, it means that something is missing on the left of the element.
             * Therefore, we can discard the elements on the right, and focus on searching on the right 
             * half of the array. 
             */
            int mid = lo + (hi - lo)/2;
            if(nums[mid] > mid) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
}
