/**
 * Refer to
 * https://leetcode.com/problems/number-of-1-bits/description/
 * Write a function that takes an unsigned integer and returns the number 
   of ’1' bits it has (also known as the Hamming weight).
   For example, the 32-bit integer ’11' has binary representation 
   00000000000000000000000000001011, so the function should return 3.
 *
 * Solution
 * https://discuss.leetcode.com/topic/11385/simple-java-solution-bit-shifting
 * https://leetcode.com/articles/number-1-bits/
*/
public class Solution {
    // you need to treat n as an unsigned value
    // Style 1:
    // https://discuss.leetcode.com/topic/11385/simple-java-solution-bit-shifting
    /**
        An Integer in Java has 32 bits, e.g. 00101000011110010100001000011010.
        To count the 1s in the Integer representation we put the input int
        n in bit AND with 1 (that is represented as 00000000000000000000000000000001, 
        and if this operation result is 1, that means that the last bit of the input 
        integer is 1. Thus we add it to the 1s count.
        ones = ones + (n & 1);

        Then we shift the input Integer by one on the right, to check for the
        next bit.
        n = n>>>1;

        We need to use bit shifting unsigned operation >>> (while >> depends on sign extension)

        We keep doing this until the input Integer is 0.
        In Java we need to put attention on the fact that the maximum integer is 2147483647. 
        Integer type in Java is signed and there is no unsigned int. So the input 2147483648 
        is represented in Java as -2147483648 (in java int type has a cyclic representation, 
        that means Integer.MAX_VALUE+1==Integer.MIN_VALUE).
        This force us to use

        n!=0

        in the while condition and we cannot use

        n>0

        because the input 2147483648 would correspond to -2147483648 in java and 
        the code would not enter the while if the condition is n>0 for n=2147483648.
    */
    public int hammingWeight(int n) {
        int ones = 0;
        while(n != 0) {
            ones += (n & 1);
            // In this style we shift given input number n, but it will face n = 2147483648 issue
            n = n >>> 1;
        }
        return ones;
    }
   
   
    // Style 2:
    // https://leetcode.com/articles/number-1-bits/
    /**
     * Approach #1 (Loop and Flip) [Accepted]
       Algorithm
       The solution is straight-forward. We check each of the 32 bits of the number. 
       If the bit is 1, we add one to the number of 1-bits.
       We can check the ith bit of a number using a bit mask. We start with a mask m=1, 
       because the binary representation of 1 is,
       0000 0000 0000 0000 0000 0000 0000 0001
       Clearly, a logical AND between any number and the mask 1 gives us the least 
       significant bit of this number. To check the next bit, we shift the mask to the 
       left by one.
       0000 0000 0000 0000 0000 0000 0000 0010
       And so on.
    */
    public int hammingWeight2(int n) {
        int result = 0;
        int mask = 1;
        for(int i = 0; i < 32; i++) {
            if((n & mask) != 0) {
                result++;
            }
            // In this style we shift mask, we don't need to face given input number n = 2147483648 issue
            mask <<= 1;
        }
        return result;
    }
}
