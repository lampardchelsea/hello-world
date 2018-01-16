/**
 * Refer to
 * https://leetcode.com/problems/power-of-three/description/
 * Given an integer, write a function to determine if it is a power of three.

    Follow up:
    Could you do it without using any loop / recursion?
 *
 *
 * Solution
*/
class Solution {
    public boolean isPowerOfThree(int n) {
        if(n == 0) {
            return false;
        }
        while(n % 3 == 0) {
            n = n / 3;
        }
        return n == 1;
    }
}
