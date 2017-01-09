/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', 
 * determine if the input string is valid.
 * The brackets must close in the correct order, "()" and "()[]{}" are all valid 
 * but "(]" and "([)]" are not.
*/
// Refer to
// https://discuss.leetcode.com/topic/7813/my-easy-to-understand-java-solution-with-one-stack
public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        char[] chars = s.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            if(chars[i] == '(' || chars[i] == '[' || chars[i] == '{') {
                stack.push(chars[i]);
              // !stack.isEmpty() to handle invalid input and because you can't peek when the stack is empty.  
            } else if(chars[i] == ')' && !stack.isEmpty() && stack.peek() == '(') {
                // Check the peek position element in stack is right counterpart
                // and then pop out is right procedure
                stack.pop();
            } else if(chars[i] == ']' && !stack.isEmpty() && stack.peek() == '[') {
                stack.pop();
            } else if(chars[i] == '}' && !stack.isEmpty() && stack.peek() == '{') {
                stack.pop();
            } else {
                // e.g input as ']', because the stack is empty as we don't push
                // anything into stack
                return false;
            }
        }
        return stack.isEmpty();
    }
}
