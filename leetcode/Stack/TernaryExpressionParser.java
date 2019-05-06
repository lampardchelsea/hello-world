/**
 Refer to
 https://yeqiuquan.blogspot.com/2017/07/439-ternary-expression-parser.html
 Given a string representing arbitrarily nested ternary expressions, calculate the result of the expression. 
 You can always assume that the given expression is valid and only consists of digits 0-9, ?, :, T and F 
 (T and F represent True and False respectively).
 
Note:
The length of the given string is ≤ 10000.
Each number will contain only one digit.
The conditional expressions group right-to-left (as usual in most languages).
The condition will always be either T or F. That is, the condition will never be a digit.
The result of the expression will always evaluate to either a digit 0-9, T or F.

Example 1:
Input: "T?2:3"
Output: "2"
Explanation: If true, then result is 2; otherwise result is 3.

Example 2:
Input: "F?1:T?4:5"
Output: "4"
Explanation: The conditional expressions group right-to-left. Using parenthesis, it is read/evaluated as:

             "(F ? 1 : (T ? 4 : 5))"                   "(F ? 1 : (T ? 4 : 5))"
          -> "(F ? 1 : 4)"                 or       -> "(T ? 4 : 5)"
          -> "4"                                    -> "4"

Example 3:
Input: "T?T?F:5:3"
Output: "F"
Explanation: The conditional expressions group right-to-left. Using parenthesis, it is read/evaluated as:

             "(T ? (T ? F : 5) : 3)"                   "(T ? (T ? F : 5) : 3)"
          -> "(T ? F : 3)"                 or       -> "(T ? F : 5)"
          -> "F"                                    -> "F"
*/

// Style 1: Stack but not insert '?' when encounter, need change i condition 
// to >= 1 since char[i - 1] means the character before '?' which used to
// make judgement
public class Solution {
    public static String parseTernary(String expression) {
        if(expression == null || expression.length() == 0) {
            return "";
        }
        char[] chars = expression.toCharArray();
        Stack<Character> stack = new Stack<Character>();
        for(int i = chars.length - 1; i >= 1; i--) {
            char c = chars[i];
            if(c != '?') {
                stack.push(c);
            } else {
                char temp1 = stack.pop();
                stack.pop();
                char temp2 = stack.pop();
                if(chars[i - 1] == 'T') {
                    stack.push(temp1);
                    i--;
                } else {
                    stack.push(temp2);
                    i--;
                }
            }
        }
        return String.valueOf(stack.pop());
	   }
}


// Style 2: Stack insert '?' but pop it out after insert
// Refer to
// https://yeqiuquan.blogspot.com/2017/07/439-ternary-expression-parser.html
/**
 Solution:
We push each character of the expression into a stack reversely.
If the last character in the stack is ''?", it means the answer to evaluate the current character is in the stack.
We pop up the two potential answer and evaluate it.
After that, we push back the answer.
If the last character in the stack is not "?", we push the current character into the stack.
Finally, the answer is the last element left in the stack.
*/
public class Solution {
    public String parseTernary(String expression) {
        if (expression == null || expression.length() == 0) {
            return "";
        }
        Stack<Character> stack = new Stack<>();
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (!stack.isEmpty() && stack.peek() == '?') {
                stack.pop(); // pop out '?'
                char first = stack.pop();
                stack.pop();
                char second = stack.pop();
                if (c == 'T') {
                    stack.push(first);
                }
                else {
                    stack.push(second);
                }
            } else {
                stack.push(c); // also push '?' onto stack first but later pop out
            }
        }
        return String.valueOf(stack.pop());
    }
}
