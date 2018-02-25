/**
 * Refer to
 * https://leetcode.com/problems/sum-of-two-integers/description/
 * Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.
	 Example:
	 Given a = 1 and b = 2, return 3.
 * 
 * Solution
 * https://stackoverflow.com/questions/4014535/differences-in-boolean-operators-vs-and-vs
 * https://leetcode.com/problems/sum-of-two-integers/discuss/84290/Java-simple-easy-understand-solution-with-explanation
 */
class Solution {
    /**
     For this, problem, for example, we have a = 1, b = 3,
     In bit representation, a = 0001, b = 0011,
     First, we can use “and”("&") operation between a and b to find a carry.
     carry = a & b, then carry = 0001
     Second, we can use “xor” ("^") operation between a and b to find the 
     different bit, and assign it to a,
     Then, we shift carry one position left and assign it to b, b = 0010.
     Iterate until there is no carry (or b == 0)
    */
    public int getSum(int a, int b) {
        if(a == 0) {
            return b;
        }
        if(b == 0) {
            return a;
        }
        while(b != 0) {
            // E.g if a = 0001 (1)
            //        b = 0011 (3)
            //    carry = 0001 -> 1
            //     if a = 0010 (2)
            //        b = 0010 (2)
            //    carry = 0010 -> 2
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }
}
