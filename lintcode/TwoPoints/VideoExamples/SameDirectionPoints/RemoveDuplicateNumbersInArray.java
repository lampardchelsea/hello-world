/**
 * Refer to
 * http://www.lintcode.com/en/problem/remove-duplicates-from-sorted-array/
 * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
    Do not allocate extra space for another array, you must do this in place with constant memory.

    Have you met this question in a real interview? Yes
    Example
    Given input array A = [1,1,2],

    Your function should return length = 2, and A is now [1,2].
 *
 * Solution
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/solution/
 * Approach #1 (Two Pointers) [Accepted]
   Algorithm
   Since the array is already sorted, we can keep two pointers i and j, where i is the slow-runner 
   while j is the fast-runner. As long as nums[i] = nums[j], we increment j to skip the duplicate.
   When we encounter nums[j]≠nums[i], the duplicate run has ended so we must copy its 
   value to nums[i + 1]. i is then incremented and we repeat the same process again until j
   reaches the end of array.
   Complexity analysis
   Time complextiy : O(n). Assume that nn is the length of array. Each of i and j traverses at most n steps.
   Space complexity : O(1).
 *
 * http://www.jiuzhang.com/solutions/remove-duplicates-from-sorted-array/
*/
public class Solution {
    /**
     * @param A: a array of integers
     * @return : return an integer
     */
    public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int i = 0;
        for(int j = 0; j < nums.length; j++) {
            if(nums[j] != nums[i]) {
                // Same way as
                // i++;
                // nums[i] = nums[j];
                nums[++i] = nums[j];
            }
        }
        return i + 1;
    }
}
