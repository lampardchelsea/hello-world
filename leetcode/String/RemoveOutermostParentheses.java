/**
Refer to
https://leetcode.com/problems/remove-outermost-parentheses/
A valid parentheses string is either empty (""), "(" + A + ")", or A + B, where A and B are valid parentheses strings, 
and + represents string concatenation.  For example, "", "()", "(())()", and "(()(()))" are all valid parentheses strings.

A valid parentheses string S is primitive if it is nonempty, and there does not exist a way to split it into S = A+B, 
with A and B nonempty valid parentheses strings.

Given a valid parentheses string S, consider its primitive decomposition: S = P_1 + P_2 + ... + P_k, where P_i are primitive valid parentheses strings.

Return S after removing the outermost parentheses of every primitive string in the primitive decomposition of S.

Example 1:
Input: "(()())(())"
Output: "()()()"
Explanation: 
The input string is "(()())(())", with primitive decomposition "(()())" + "(())".
After removing outer parentheses of each part, this is "()()" + "()" = "()()()".

Example 2:
Input: "(()())(())(()(()))"
Output: "()()()()(())"
Explanation: 
The input string is "(()())(())(()(()))", with primitive decomposition "(()())" + "(())" + "(()(()))".
After removing outer parentheses of each part, this is "()()" + "()" + "()(())" = "()()()()(())".

Example 3:
Input: "()()"
Output: ""
Explanation: 
The input string is "()()", with primitive decomposition "()" + "()".
After removing outer parentheses of each part, this is "" + "" = "".

Note:
S.length <= 10000
S[i] is "(" or ")"
S is a valid parentheses string
*/

// Solution 1: Primitive string will have equal number of opened and closed paranthesis
// Style 1:
// Refer to
// https://leetcode.com/problems/remove-outermost-parentheses/discuss/270566/My-Java-3ms-Straight-Forward-Solution-or-Beats-100
class Solution {
    // Primitive string will have equal number of opened and closed paranthesis.
    // e.g "(()())" is a primitive string since it cannot split into A + B style, 
    // since A and B must be nonempty valid parentheses strings such as "(())" 
    // or "()" or "", here some bad try such as "(()" + "())", etc. always failed
    public String removeOuterParentheses(String S) {
        StringBuilder sb = new StringBuilder();
        int open = 0;
        int close = 0;
        int start = 0;
        int n = S.length();
        for(int i = 0; i < n; i++) {
            char c = S.charAt(i);
            if(c == '(') {
                open++;
            } else {
                close++;
            }
            // Get a primitive substring
            if(open == close) {
                // Remove the outermost parentheses of every primitive string
                sb.append(S.substring(start + 1, i));
                start = i + 1;
            }
        }
        return sb.toString();
    }
}

// Style 2:
// Refer to
// https://leetcode.com/problems/remove-outermost-parentheses/discuss/270022/JavaC%2B%2BPython-Count-Opened-Parenthesis
/**
Intuition
Quote from @shubhama,
Primitive string will have equal number of opened and closed parenthesis.

Explanation:
opened count the number of opened parenthesis.
Add every char to the result,
unless the first left parenthesis,
and the last right parenthesis.

Time Complexity:
O(N) Time, O(N) space

Java:

    public String removeOuterParentheses(String S) {
        StringBuilder s = new StringBuilder();
        int opened = 0;
        for (char c : S.toCharArray()) {
            if (c == '(' && opened++ > 0) s.append(c);
            if (c == ')' && opened-- > 1) s.append(c);
        }
        return s.toString();
    }
*/
