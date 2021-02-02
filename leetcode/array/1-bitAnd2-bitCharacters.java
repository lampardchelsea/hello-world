/**
Refer to
https://leetcode.com/problems/1-bit-and-2-bit-characters/
We have two special characters. The first character can be represented by one bit 0. 
The second character can be represented by two bits (10 or 11).

Now given a string represented by several bits. Return whether the last character must be a one-bit character or not. 
The given string will always end with a zero.

Example 1:
Input: 
bits = [1, 0, 0]
Output: True
Explanation: 
The only way to decode it is two-bit character and one-bit character. So the last character is one-bit character.

Example 2:
Input: 
bits = [1, 1, 1, 0]
Output: False
Explanation: 
The only way to decode it is two-bit character and two-bit character. So the last character is NOT one-bit character.

Note:
1 <= len(bits) <= 1000.
bits[i] is always 0 or 1.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/1-bit-and-2-bit-characters/discuss/108969/Java-solution-1-or-2
// https://leetcode.com/problems/1-bit-and-2-bit-characters/solution/
/**
Approach #1: Increment Pointer [Accepted]
Intuition and Algorithm

When reading from the i-th position, if bits[i] == 0, the next character must have 1 bit; else if bits[i] == 1, 
the next character must have 2 bits. We increment our read-pointer i to the start of the next character appropriately. 
At the end, if our pointer is at bits.length - 1, then the last character must have a size of 1 bit.

Java
class Solution {
    public boolean isOneBitCharacter(int[] bits) {
        int i = 0;
        while (i < bits.length - 1) {
            i += bits[i] + 1;
        }
        return i == bits.length - 1;
    }
}

Complexity Analysis
Time Complexity: O(N), where N is the length of bits.
Space Complexity: O(1), the space used by i.
*/
class Solution {
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        int i = 0;
        while(i < n - 1) {
            if(bits[i] == 0) {
                i ++;
            } else {
                i += 2;
            }
        }
        return i == n - 1;
    }
}
