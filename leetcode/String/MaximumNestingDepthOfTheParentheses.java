/**
Refer to
https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/
A string is a valid parentheses string (denoted VPS) if it meets one of the following:

It is an empty string "", or a single character not equal to "(" or ")",
It can be written as AB (A concatenated with B), where A and B are VPS's, or
It can be written as (A), where A is a VPS.
We can similarly define the nesting depth depth(S) of any VPS S as follows:

depth("") = 0
depth(C) = 0, where C is a string with a single character not equal to "(" or ")".
depth(A + B) = max(depth(A), depth(B)), where A and B are VPS's.
depth("(" + A + ")") = 1 + depth(A), where A is a VPS.
For example, "", "()()", and "()(()())" are VPS's (with nesting depths 0, 1, and 2), and ")(" and "(()" are not VPS's.
                 "()()"---> depth = 1 because "" can be recognize as A and inside (), so "()" depth = 1 + depth("") = 1
                 and because of "()()" can be write as depth(A + B) = max(depth("()"), depth("()")) = max(1, 1) = 1 

Given a VPS represented as string s, return the nesting depth of s.

Example 1:
Input: s = "(1+(2*3)+((8)/4))+1"
Output: 3
Explanation: Digit 8 is inside of 3 nested parentheses in the string.

Example 2:
Input: s = "(1)+((2))+(((3)))"
Output: 3

Example 3:
Input: s = "1+(2*3)/(2-1)"
Output: 1

Example 4:
Input: s = "1"
Output: 0

Constraints:
1 <= s.length <= 100
s consists of digits 0-9 and characters '+', '-', '*', '/', '(', and ')'.
It is guaranteed that parentheses expression s is a VPS.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/discuss/888949/JavaC%2B%2BPython-Parentheses-Problem-Foundation
/**
Exlanation
Ignore digits and signs,
only count the current open parentheses cur.

The depth equals to the maximum open parentheses

Complexity
Time O(N)
Space O(1)

Java:
    public int maxDepth(String s) {
        int res = 0, cur = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(')
                res = Math.max(res, ++cur);
            if (s.charAt(i) == ')')
                cur--;
        }
        return res;
    }
 
More Parentheses Problem To Advance
Here is a ladder of parentheses problem, in order of problem number.
1541. Minimum Insertions to Balance a Parentheses String
1249. Minimum Remove to Make Valid Parentheses
1111. Maximum Nesting Depth of Two Valid Parentheses Strings
1190. Reverse Substrings Between Each Pair of Parentheses
1021. Remove Outermost Parentheses
921. Minimum Add to Make Parentheses Valid
856. Score of Parentheses

https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/discuss/888948/JavaPython-3-Parentheses-balance%3A-O(n)-code-w-brief-explanation-and-analysis..
20. Valid Parentheses
22. Generate Parentheses
32. Longest Valid Parentheses
241. Different Ways to Add Parentheses
301. Remove Invalid Parentheses
678. Valid Parenthesis String
856. Score of Parentheses
921. Minimum Add to Make Parentheses Valid
1021. Remove Outermost Parentheses
1111. Maximum Nesting Depth of Two Valid Parentheses Strings
1190. Reverse Substrings Between Each Pair of Parentheses
1249. Minimum Remove to Make Valid Parentheses
1541. Minimum Insertions to Balance a Parentheses String
*/
class Solution {
    public int maxDepth(String s) {
        int max = 0;
        int count = 0;
        for(char c : s.toCharArray()) {
            if(c == '(') {
                count++;
                max = Math.max(max, count);
            // Must count-- and test out by:
            // "(1+(2*3)+((8)/4))+1"
            } else if(c == ')') {
                count--;
            }
        }
        return max;
    }
}
