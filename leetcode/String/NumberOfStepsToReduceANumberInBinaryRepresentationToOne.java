/**
Refer to
https://leetcode.com/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/
Given a number s in their binary representation. Return the number of steps to reduce it to 1 under the following rules:

If the current number is even, you have to divide it by 2.

If the current number is odd, you have to add 1 to it.

It's guaranteed that you can always reach to one for all testcases.

Example 1:
Input: s = "1101"
Output: 6
Explanation: "1101" corressponds to number 13 in their decimal representation.
Step 1) 13 is odd, add 1 and obtain 14. 
Step 2) 14 is even, divide by 2 and obtain 7.
Step 3) 7 is odd, add 1 and obtain 8.
Step 4) 8 is even, divide by 2 and obtain 4.  
Step 5) 4 is even, divide by 2 and obtain 2. 
Step 6) 2 is even, divide by 2 and obtain 1.  

Example 2:
Input: s = "10"
Output: 1
Explanation: "10" corressponds to number 2 in their decimal representation.
Step 1) 2 is even, divide by 2 and obtain 1.  

Example 3:
Input: s = "1"
Output: 0

Constraints:
1 <= s.length <= 500
s consists of characters '0' or '1'
s[0] == '1'
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/discuss/564287/C%2B%2BJava-O(n)
/**
Intuition: division by two is the same as the right shift by one bit (character). If the bit is 0, we just do the shift - one operation. If the bit is 1 - we do plus one, and our bit changes to zero. So, we set carry to 1 and shift. Two operations.

Algorithm
We have three phases here:
1.We haven't encountered any 1. Every char adds one operation.
2.We encounter our first 1. We set carry to 1 and add two operations.
3.The rest:
3A. Every 1 needs one operation (carry makes it 0). carry is still 1 due to addition.
3B. Every 0 needs two operations (carry makes it 1). carry is still 1 as we need to add 1 in this case.
Observation: as you can see from 3A and 3B, carry is always 1 after the second phase.

public int numSteps(String s) {
    int res = 0, carry = 0;
    for (int i = s.length() - 1; i > 0; --i) {
        ++res;
        if (s.charAt(i) - '0' + carry == 1) {
            carry = 1;
            ++res;
        }
    }
    return res + carry;
}
*/

// https://leetcode.com/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/discuss/1184352/JavaPython-Clean-and-Concise-Clear-Explanation-O(N)
/**
Example
Input: s = "1101", which represents nunber 13
Output: 6 (steps)
Explanation:
Step 1: 13 is odd, add 1 and obtain 14.
Step 2: 14 is even, divide by 2 and obtain 7.
Step 3: 7 is odd, add 1 and obtain 8.
Step 4: 8 is even, divide by 2 and obtain 4.
Step 5: 4 is even, divide by 2 and obtain 2.
Step 6: 2 is even, divide by 2 and obtain 1.

Idea
We need to simulate the steps described in the binary string.
Read the string from right to left, if the string ends in 0 then the number is even otherwise it is odd.
If s[i] == 1 -> It's an odd number, we need 2 operations: add 1 and divide by 2. So we can just divide by 2 and set carry = 1 for the next operation.

Algorithm
Intialize carry = 0, ans = 0
We iterate character of binary string from the end to the beginning, i = [n-1..1], except the first digit
If s[i] + carry == 1 -> It's an odd number, we need 2 operations: Add 1 and Divide by two, ans += 2
Else if s[i] + carry == 0 -> It's an even number, we need 1 operation: Divide by two, ans += 1
Then finally, if carry = 1 then total = s[0] + carry = 2, need one additional operation divide by 2 to become one, 
else if carry = 0 then total = s[0] + carry = 1, no need any additional operation
So the result is ans + carry

Complexity
Time: O(N)
Space: O(1)

class Solution {
    public int numSteps(String s) {
        int n = s.length();
        int carry = 0;
        int ans = 0;
        for (int i = n - 1; i >= 1; i--) {
            if (s.charAt(i) - '0' + carry == 1) {
                carry = 1;
                ans += 2;
            } else {
                ans += 1;
            }
        }
        return ans + carry;
    }
}
*/
class Solution {
    // Intuition: division by two is the same as the right shift 
    // by one bit (character). If the bit is 0, we just do the 
    // shift - one operation. If the bit is 1 - we do plus one, 
    // and our bit changes to zero. So, we set carry to 1 and 
    // shift. Two operations.
    public int numSteps(String s) {
        int result = 0;
        int n = s.length();
        int carry = 0;
        // Except first digit
        for(int i = n - 1; i > 0; i--) {
            // Odd number
            if(s.charAt(i) - '0' + carry == 1) {
                // 2 operations: Add 1 and divide by two
                result += 2;
                carry = 1;
            // Even number
            } else {
                // 1 operation: Divide by 2
                result += 1;
            }
        }
        // If carry = 1 then total = s[0] + carry = 2, need one 
        // additional operation divide by 2 to become one, else 
        // if carry = 0 then total = s[0] + carry = 1, no need 
        // any additional operation
        return carry + result;
    }
}
