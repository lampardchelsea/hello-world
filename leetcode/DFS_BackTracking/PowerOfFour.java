/**
 * Refer to
 * https://leetcode.com/problems/power-of-four/description/
 * 
    Given an integer (signed 32 bits), write a function to check whether it is a power of 4.

    Example:
    Given num = 16, return true. Given num = 5, return false.
 *
 * Solution
*/
class Solution {
    public boolean isPowerOfFour(int num) {
        if(num == 0) {
            return false;
        }
        return helper(num);
    }
    
    private boolean helper(int num) {
        if(num == 1) {
            return true;
        } else {
            return num % 4 == 0 && helper(num / 4);
        }
    }
}
