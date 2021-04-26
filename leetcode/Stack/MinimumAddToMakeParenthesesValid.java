/**
Refer to
https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/
Given a string S of '(' and ')' parentheses, we add the minimum number of parentheses ( '(' or ')', and in any positions ) 
so that the resulting parentheses string is valid.

Formally, a parentheses string is valid if and only if:

It is the empty string, or
It can be written as AB (A concatenated with B), where A and B are valid strings, or
It can be written as (A), where A is a valid string.
Given a parentheses string, return the minimum number of parentheses we must add to make the resulting string valid.

Example 1:
Input: "())"
Output: 1

Example 2:
Input: "((("
Output: 3

Example 3:
Input: "()"
Output: 0

Example 4:
Input: "()))(("
Output: 4

Note:
S.length <= 1000
S only consists of '(' and ')' characters.
*/

// Solution 1: Stack simulation
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Stack/MinimumInsertionsToBalanceAParenthesesString.java
class Solution {
    public int minAddToMakeValid(String S) {
        int count = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for(char c: S.toCharArray()) {
            if(c == '(') {
                // A '(' in any condition: empty stack, peek as '(' or ')'
                // will require a ')' to close it, push 1 on stack
                stack.push(1);
            } else {
                if(stack.isEmpty() || stack.peek() == 0) {
                    // If stack is empty or peek as 0 and we have ')' on hand, 
                    // it means we need one '(' to close it, increase count 
                    // and push 0 on stack for identify ')' on stack peek
                    count++;
                    stack.push(0);
                } else if(stack.peek() == 1) {
                    // If stack peek is 1 means one previous '(' pending for
                    // a ')' to close and now we have one ')' in hand, then
                    // use it and pop out the 1
                    stack.pop();
                }
            }
        }
        while(!stack.isEmpty()) {
            count += stack.pop();
        }
        return count;
    }
}

