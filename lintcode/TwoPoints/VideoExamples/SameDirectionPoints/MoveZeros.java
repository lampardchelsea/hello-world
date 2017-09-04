/**
 * Refer to
 * http://www.lintcode.com/en/problem/move-zeroes/
 * Given an array nums, write a function to move all 0's to the end of it while maintaining 
   the relative order of the non-zero elements.
   Notice
   You must do this in-place without making a copy of the array.
   Minimize the total number of operations.
   Have you met this question in a real interview? Yes
   Example
   Given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].
 *
 * Solution
 * http://www.jiuzhang.com/solutions/move-zeroes/
 * 
*/
public class Solution {
    /**
     * @param nums an integer array
     * @return nothing, do this in-place
     */
    public void moveZeroes(int[] nums) {
        int i = 0;
        int j = 0;
        while(j < nums.length) {
            if(nums[j] != 0) {
                int temp = nums[j];
                nums[j] = 0;
                nums[i] = temp;
                i++;
            }
            j++;
        }
    }
}
