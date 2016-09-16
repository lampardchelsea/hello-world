/**
 * Given an array of integers, every element appears twice except for one. Find that single one.
 * Note:
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 * The key to solve this problem is bit manipulation. XOR will return 1 only on two different bits. 
 * So if two numbers are the same, XOR will return 0. Finally only one number left.
 * For any number is X ^ X = 0, X ^ 0 = X
*/

public class Solution {
    public int singleNumber(int[] nums) {
        int length = nums.length;
        int result = 0;
        for(int i = 0; i < length; i++) {
            result = result ^ nums[i];
            
        }
        
        return result;
    }
}
