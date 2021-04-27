/**
Refer to
https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
Given a string s of '(' , ')' and lowercase English characters. 

Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting 
parentheses string is valid and return any valid string.

Formally, a parentheses string is valid if and only if:

It is the empty string, contains only lowercase characters, or
It can be written as AB (A concatenated with B), where A and B are valid strings, or
It can be written as (A), where A is a valid string.

Example 1:
Input: s = "lee(t(c)o)de)"
Output: "lee(t(c)o)de"
Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.

Example 2:
Input: s = "a)b(c)d"
Output: "ab(c)d"

Example 3:
Input: s = "))(("
Output: ""
Explanation: An empty string is also valid.

Example 4:
Input: s = "(a(b(c)d)"
Output: "a(b(c)d)"

Constraints:
1 <= s.length <= 10^5
s[i] is one of  '(' , ')' and lowercase English letters.
*/

// Solution 1: Two stack simulation (O(N^2) time complexity very slow but intuitive way)
class Solution {
    public String minRemoveToMakeValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        Stack<Character> tmp = new Stack<Character>();
        for(char c : s.toCharArray()) {
            if(c != ')') {
                stack.push(c);
            } else {
                while(!stack.isEmpty()) {
                    if(stack.peek() != '(') {
                        // Store all english letters in tmp stack
                        // including '#' which means updated previous
                        // used '(' and previous ')'
                        tmp.push(stack.pop());
                    } else {
                        // Find previous not used corresponding '(' from 
                        // stack update it from '(' to '#' for later 
                        // identify it is a already used one
                        stack.pop();
                        stack.push('#');
                        break;
                    }
                }
                // If after removing all elements from stack and not able
                // to find previous not used corresponding '(', then no need
                // to push ')' onto stack, since we have to remove all
                // non-pairable ')' also, only need to restore stack from 
                // tmp stack, if not empty means we find one '(' to build
                // a pair, then need to restore stack and also push ')'
                if(stack.isEmpty()) {
                    while(!tmp.isEmpty()) {
                        stack.push(tmp.pop());
                    }             
                } else {
                    while(!tmp.isEmpty()) {
                        stack.push(tmp.pop());
                    }
                    stack.push(c);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            char c1 = stack.pop();
            // Skip all unpaired '(', e.g "))((", expected "" not "(("
            if(c1 != '(') {
                if(c1 == '#') {
                    sb.insert(0, '(');
                } else {
                    sb.insert(0, c1);
                }                
            }
        }
        return sb.toString();
    }
}
