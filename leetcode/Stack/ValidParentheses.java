/**
 Refer to
 https://leetcode.com/problems/valid-parentheses/
 Given a string containing just the characters '(', ')', '{', '}', '[' and ']', 
 determine if the input string is valid.

An input string is valid if:
Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Note that an empty string is also considered valid.

Example 1:
Input: "()"
Output: true

Example 2:
Input: "()[]{}"
Output: true

Example 3:
Input: "(]"
Output: false

Example 4:
Input: "([)]"
Output: false

Example 5:
Input: "{[]}"
Output: true
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/valid-parentheses/solution/
// https://leetcode.com/problems/valid-parentheses/discuss/9248/My-easy-to-understand-Java-Solution-with-one-stack
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        char[] chars = s.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            if(chars[i] == '(' || chars[i] == '[' || chars[i] == '{') {
                stack.push(chars[i]);
            } else if(chars[i] == ')' && !stack.isEmpty() && stack.peek() == '(') {
                stack.pop();
            } else if(chars[i] == ']' && !stack.isEmpty() && stack.peek() == '[') {
                stack.pop();
            } else if(chars[i] == '}' && !stack.isEmpty() && stack.peek() == '{') {
                stack.pop();
            } else {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
