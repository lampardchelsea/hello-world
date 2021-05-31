/**
Refer to
https://leetcode.com/problems/split-array-into-fibonacci-sequence/
You are given a string of digits num, such as "123456579". We can split it into a Fibonacci-like sequence [123, 456, 579].

Formally, a Fibonacci-like sequence is a list f of non-negative integers such that:

0 <= f[i] < 231, (that is, each integer fits in a 32-bit signed integer type),
f.length >= 3, and
f[i] + f[i + 1] == f[i + 2] for all 0 <= i < f.length - 2.
Note that when splitting the string into pieces, each piece must not have extra leading zeroes, except if the piece is the number 0 itself.

Return any Fibonacci-like sequence split from num, or return [] if it cannot be done.

Example 1:
Input: num = "123456579"
Output: [123,456,579]

Example 2:
Input: num = "11235813"
Output: [1,1,2,3,5,8,13]

Example 3:
Input: num = "112358130"
Output: []
Explanation: The task is impossible.

Example 4:
Input: num = "0123"
Output: []
Explanation: Leading zeroes are not allowed, so "01", "2", "3" is not valid.

Example 5:
Input: num = "1101111"
Output: [11,0,11,11]
Explanation: The output [11, 0, 11, 11] would also be accepted.

Constraints:
1 <= num.length <= 200
num contains only digits.
*/

// Solution 1: Exactly same way as 306. Additive Number
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/AdditiveNumber.java

// Refer to
// https://leetcode.com/problems/split-array-into-fibonacci-sequence/discuss/139690/Logical-Thinking-with-Clear-Java-Code
/**
If we create a pointer curIdx, such that the sequence to the left of it is Fibonacci-like, and result, to save the Fibonacci-like sequence split from S, then
Start State: curIdx = 0 and result.size = 0;
End(Aim) State: curIdx = S.length() and result.size() >= 3
State Transformation:
for the current sequence, its start index is curIdx (its previous sequence's end index + 1), and its end index should be in range [curIdx, S.length() - 1]. 
We simply list all the possibilities, check if the current sequence is Fibonacci-like for the sequence before, i.e.
if (result.size() <= 1 || num == (long) result.get(result.size() - 1) + (long) result.get(result.size() - 2)), and check if it can form a 
Fibonacci-like sequence afterwards, i.e., if (splitIntoFibonacciFrom(i + 1, result, S)). We terminate the Recursion if we meet corner cases or the base case, 
i.e., the End State.

The clear code in Java is as below:

    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> result = new ArrayList<>();
        splitIntoFibonacciFrom(0, result, S); // start state
        return result;
    }

    private boolean splitIntoFibonacciFrom(int curIdx, List<Integer> result, String S) {
        if (curIdx == S.length() && result.size() >= 3) { // end state (base cases)
            return true;
        }
        for (int i = curIdx; i <= S.length() - 1; i++) {
            if (S.charAt(curIdx) == '0' && i > curIdx) {
                break;
            }
            long num = Long.valueOf(S.substring(curIdx, i + 1));
            if (num > Integer.MAX_VALUE) {
                break;
            }
            if (result.size() <= 1 || num == (long) result.get(result.size() - 1) + (long) result.get(result.size() - 2)) {
                result.add((int) num);
                if (splitIntoFibonacciFrom(i + 1, result, S)) {
                    return true;
                }
                result.remove(result.size() - 1);
            }
        }
        return false;
    }
*/
class Solution {
    public List<Integer> splitIntoFibonacci(String num) {
        List<Integer> result = new ArrayList<Integer>();
        helper(0, result, num);
        return result;
    }
    
    private boolean helper(int curIndex, List<Integer> result, String s) {
        if(curIndex == s.length() && result.size() >= 3) {
            return true;
        }
        for(int i = curIndex; i < s.length(); i++) {
            if(s.charAt(curIndex) == '0' && i > curIndex) {
                break;
            }
            // Current number ending till i, to include must use substring till i + 1
            long num = Long.valueOf(s.substring(curIndex, i + 1));
            if(num > Integer.MAX_VALUE) {
                break;
            }
            if(result.size() <= 1 || (long)result.get(result.size() - 2) + (long)result.get(result.size() - 1) == num) {
                result.add((int)num);
                if(helper(i + 1, result, s)) {
                    return true;
                }
                result.remove(result.size() - 1);
            }
        }
        return false;
    }
}

