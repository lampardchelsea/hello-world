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
 * https://discuss.leetcode.com/topic/40195/how-we-handle-this-question-on-interview-thinking-process-dp-solution
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

// Solution 2:
/**
 * Refer to
 * How we handle this question on interview [Thinking process + DP solution]
 * https://discuss.leetcode.com/topic/40195/how-we-handle-this-question-on-interview-thinking-process-dp-solution
 * Question:
   Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate 
   the number of 1's in their binary representation and return them as an array.

   Thinking:
   We do not need check the input parameter, because the question has already mentioned that the 
   number is non negative.

   How we do this? The first idea come up with is find the pattern or rules for the result. 
   Therefore, we can get following pattern
   Index : 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
   num : 0 1 1 2 1 2 2 3 1 2 2 3 2 3 3 4

   Do you find the pattern?
   Obviously, this is overlap sub problem, and we can come up the DP solution. For now, we need 
   find the function to implement DP.

   dp[0] = 0;
   dp[1] = dp[0] + 1;
   dp[2] = dp[0] + 1;
   dp[3] = dp[1] +1;
   dp[4] = dp[0] + 1;
   dp[5] = dp[1] + 1;
   dp[6] = dp[2] + 1;
   dp[7] = dp[3] + 1;
   dp[8] = dp[0] + 1;
   ...

   This is the function we get, now we need find the other pattern for the function to get the general 
   function. After we analyze the above function, we can get
   dp[0] = 0;
   dp[1] = dp[1-1] + 1;
   dp[2] = dp[2-2] + 1;
   dp[3] = dp[3-2] + 1;
   dp[4] = dp[4-4] + 1;
   dp[5] = dp[5-4] + 1;
   dp[6] = dp[6-4] + 1;
   dp[7] = dp[7-4] + 1;
   dp[8] = dp[8-8] + 1;
   ..

   Obviously, we can find the pattern for above example, so now we get the general function
   dp[index] = dp[index - offset] + 1;
*/
class Solution {
    public int[] countBits(int num) {
        int[] result = new int[num + 1];
        int offset = 1;
        for(int i = 1; i < num + 1; i++) {
            if(offset * 2 == i) {
                offset *= 2;
            }
            result[i] = result[i - offset] + 1;
        }
        return result;
    }
}
