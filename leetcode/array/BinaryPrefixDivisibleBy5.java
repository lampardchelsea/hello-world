/**
Refer to
https://leetcode.com/problems/binary-prefix-divisible-by-5/
Given an array A of 0s and 1s, consider N_i: the i-th subarray from A[0] to A[i] interpreted as a binary number 
(from most-significant-bit to least-significant-bit.)

Return a list of booleans answer, where answer[i] is true if and only if N_i is divisible by 5.

Example 1:
Input: [0,1,1]
Output: [true,false,false]
Explanation: 
The input numbers in binary are 0, 01, 011; which are 0, 1, and 3 in base-10.  Only the first number is divisible by 5, so answer[0] is true.

Example 2:
Input: [1,1,1]
Output: [false,false,false]

Example 3:
Input: [0,1,1,1,1,1]
Output: [true,false,false,false,true,false]

Example 4:
Input: [1,1,1,0,1]
Output: [false,false,false,false,false]

Note:
1 <= A.length <= 30000
A[i] is 0 or 1
*/

// Solution 1: Native but stackoverflow
// Refer to
// https://leetcode.com/problems/binary-prefix-divisible-by-5/discuss/265554/JavaPython-3-71-liners-left-shift-bitwise-or-and-mod.
class Solution {
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> result = new ArrayList<Boolean>();
        int sum = 0;
        for(int i = 0; i < A.length; i++) {
            sum = sum * 2 + A[i];
            result.add(sum % 5 == 0 ? true : false);
        }
        return result;
    }
}

// Solution 2: Modular Arithmetic O(n)
// Refer to
// https://leetcode.com/problems/binary-prefix-divisible-by-5/discuss/296249/Java-solution-with-best-explanation(don't-know-why-some-explanation-not-concise)
/**
Prerequisite

You need to know how to contruct a number with bits in some numeric representation.

For example:
Contruct the number from a string in decimal like "12345678", key: num = num * 10 + (c - '0') where c is the current character.
Contruct the number from a string in binary like "01010101", key: num = num * 2 + (c - '0'), and faster: (num << 1) | (c - '0') where c is the current character.
Or array format like [0, 1, 0, 1, 0, 1], key: num = (num << 1) | c where c is the current bit.
You should be already very familar with that.

Strategy
If you know above, then you can just contruct the number num and check if num % 5 = 0 and add it to result res.

But Trap:
In Java, an integer int is a 32-bit number, that's why it is in range of [-2^31, 2^31 - 1]. So if we use above way, 
then it can maximumly represent 32 bits in the array A. If beyond that, then overflow will happen, you may not get correct result.

So we need to use some Math Knowledge(I learnt it from Cryptography Course if my memory services my right):

Consider the formula below which is the key to this problem:
(a * b + c) % d = ((a % d) * (b % d) + c % d) % d

Simply say is that we mod each part in a * b + c, then mod the result.

So in this problem, num = (num << 1) + cur which can be written as num = num * 2 + (0 or 1). From above trick, we get 
num % 5 = (num % 5) * (2 % 5) + (0 or 1) % 5. Since 2, 0, 1 all smaller than 5, so they mod 5 do not cause any difference, 
we simplify the formula to => num % 5 = 2 * (num % 5) + (0 or 1).

From above we know that we can update num to num % 5 each time which then avoids overflow for us.

Final Code
class Solution {
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> res = new ArrayList<>();
        int num = 0;
        for(int cur : A){
            num = ((num << 1) + cur) % 5;
            if(num == 0) res.add(true);
            else res.add(false);
        }
        
        return res;
    }
}
TC: O(n) where n is the length of A
SC: O(1)
*/
class Solution {
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> result = new ArrayList<Boolean>();
        int sum = 0;
        for(int i = 0; i < A.length; i++) {
            sum = sum * 2 + A[i];
            sum %= 5; // Avoid stackoverflow
            result.add(sum == 0 ? true : false);
        }
        return result;
    }
}
