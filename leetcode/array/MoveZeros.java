/**
 * Given an array nums, write a function to move all 0's to the end of it while 
 * maintaining the relative order of the non-zero elements.
 * For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums 
 * should be [1, 3, 12, 0, 0].
 * Note:
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
*/
public class Solution {
    public void moveZeroes(int[] nums) {
        int count = 0;
        int length = nums.length;
        int i = 0;
        int j = 0;
        
        // Skip all 0
        for(i = 0; i < length; i++) {
            if(nums[i] != 0) {
                nums[j++] = nums[i];
            }
        }
        
        // Fill up all remained elements with 0
        while(j < length) {
            nums[j++] = 0;
        }
    }
}
