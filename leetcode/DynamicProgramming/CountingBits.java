/**
 * Refer to
 * https://leetcode.com/problems/counting-bits/description/
 * Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate 
   the number of 1's in their binary representation and return them as an array.

    Example:
    For num = 5 you should return [0,1,1,2,1,2].

    Follow up:
    It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it 
    in linear time O(n) /possibly in a single pass?
    Space complexity should be O(n).
    Can you do it like a boss? Do it without using any builtin function like __builtin_popcount 
    in c++ or in any other language.
 *
 * Solution
 * https://leetcode.com/problems/number-of-1-bits/solution/
 * 
*/
// Solution 1: Same as NumberOf1Bits.java
// Refer to
// https://leetcode.com/problems/number-of-1-bits/solution/
/**
 Approach #1 (Loop and Flip) [Accepted]
 Algorithm
 The solution is straight-forward. We check each of the 32 bits of the number. If the bit is 1, 
 we add one to the number of 1-bits.
 
  We can check the ith bit of a number using a bit mask. We start with a mask m=1, because the 
  binary representation of 1 is,
  0001 0000 0000 0000 0000 0000 0000 0000 0001
  
  Clearly, a logical AND between any number and the mask 1 gives us the least significant bit 
  of this number. 
  To check the next bit, we shift the mask to the left by one.
  0010 0000 0000 0000 0000 0000 0000 0000 0010
  
  Complexity Analysis
  The run time depends on the number of bits in n. Because nn in this piece of code is a 32-bit integer, 
  the time complexity is O(1). -> For CountingBits is O(n * 1) = O(n)
  The space complexity is O(1), since no additional space is allocated.-> For CountingBits is O(n * 1) = O(n)
*/
class Solution {
    public int[] countBits(int num) {
        int[] result = new int[num + 1];
        for(int i = 0; i <= num; i++) {
            result[i] = hammingWeight(i);
        }
        return result;
    }
    
    private int hammingWeight(int n) {
        int bits = 0;
        int mask = 1;
        for(int i = 0; i < 32; i++) {
            if((n & mask) != 0) {
                bits++;
            }
            mask <<= 1;
        }
        return bits;
    }
}
