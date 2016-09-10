/**
 * Rotate an array of n elements to the right by k steps.
 * For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 * Note:
 * Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
*/
public class Solution {
    public void rotate(int[] nums, int k) {
        int length = nums.length;
        int[] concatenate = new int[length * 2];
        
        for(int i = 0; i < length; i++) {
            concatenate[i] = nums[i];
            concatenate[length + i] = nums[i];
        }
        
        for(int i = 0; i < length; i++) {
            nums[i] = concatenate[length - k % length + i];
        }
    }
}
