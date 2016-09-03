/**
 * Given an array and a value, remove all instances of that value in place and return the new length.
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 * Example:
 * Given input array nums = [3,2,2,3], val = 3
 * Your function should return length = 2, with the first two elements of nums being 2.
 * 
 * Intuition
 * Now consider cases where the array contains few elements to remove. For example, nums = [1,2,3,5,4], 
 * val = 4nums=[1,2,3,5,4],val=4. The previous algorithm will do unnecessary copy operation of the first 
 * four elements. Another example is nums = [4,1,2,3,5], val = 4nums=[4,1,2,3,5],val=4. It seems 
 * unnecessary to move elements [1,2,3,5][1,2,3,5] one step left as the problem description mentions 
 * that the order of elements could be changed.
*/
public class Solution {
    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        int i = 0;
        while(i < length) {
            if(nums[i] == val) {
                nums[i] = nums[length - 1];
                length--;
            } else {
                i++;
            } 
        }
        return length;
    }
}
