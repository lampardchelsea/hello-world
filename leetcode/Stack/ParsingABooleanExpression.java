/**
Refer to
https://leetcode.com/problems/parsing-a-boolean-expression/
Return the result of evaluating a given boolean expression, represented as a string.

An expression can either be:

"t", evaluating to True;
"f", evaluating to False;
"!(expr)", evaluating to the logical NOT of the inner expression expr;
"&(expr1,expr2,...)", evaluating to the logical AND of 2 or more inner expressions expr1, expr2, ...;
"|(expr1,expr2,...)", evaluating to the logical OR of 2 or more inner expressions expr1, expr2, ...

Example 1:
Input: expression = "!(f)"
Output: true

Example 2:
Input: expression = "|(f,t)"
Output: true

Example 3:
Input: expression = "&(t,f)"
Output: false

Example 4:
Input: expression = "|(&(t,f,t),!(t))"
Output: false

Constraints:
1 <= expression.length <= 20000
expression[i] consists of characters in {'(', ')', '&', '|', '!', 't', 'f', ','}.
expression is a valid expression representing a boolean, as given in the description.
*/

// Solution 1: Stack
// Refer to
// https://leetcode.com/problems/parsing-a-boolean-expression/discuss/323532/JavaPython-3-Iterative-and-recursive-solutions-w-explanation-and-analysis.
/**
Method 1: Iterative version - Use Stack and Set.

Loop through the input String:

Use a stack to store chars except ',' and ')';
If we find a ')', keep popping out the chars from the stack till find a '('; add the popped-out into a Set.
Pop out the operator after popping ')' out, push into stack the corresponding result according to the operator.
repeat the above till the end, and the remaining is the result.
[Java]

    public boolean parseBoolExpr(String expression) {
        Deque<Character> stk = new ArrayDeque<>();
        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
            if (c == ')') {
                Set<Character> seen = new HashSet<>();
                while (stk.peek() != '(')
                    seen.add(stk.pop());
                stk.pop();// pop out '('.
                char operator = stk.pop(); // get operator for current expression.
                if (operator == '&') {
                    stk.push(seen.contains('f') ? 'f' : 't'); // if there is any 'f', & expression results to 'f'
                }else if (operator == '|') {
                    stk.push(seen.contains('t') ? 't' : 'f'); // if there is any 't', | expression results to 't'
                }else { // ! expression.
                    stk.push(seen.contains('t') ? 'f' : 't'); // Logical NOT flips the expression.
                }
            }else if (c != ',') {
                stk.push(c);
            }
        }
        return stk.pop() == 't';
    }
*/
class Solution {
    public boolean parseBoolExpr(String expression) {
        Stack<Character> stack = new Stack<Character>();
        for(char c : expression.toCharArray()) {
            if(c == ')') {
                // set will only contain 't' or 'f'
                Set<Character> set = new HashSet<Character>();
                while(!stack.isEmpty() && stack.peek() != '(') {
                    set.add(stack.pop());
                }
                // pop out '('
                stack.pop();
                char ops = stack.pop();
                if(ops == '!') {
                    // Logical NOT flips the expression.
                    stack.push(set.contains('t') ? 'f' : 't');
                } else if(ops == '|') {
                    // if there is any 't', | expression results to 't'
                    stack.push(set.contains('t') ? 't' : 'f');
                } else if(ops == '&') {
                    // if there is any 'f', & expression results to 'f'
                    stack.push(set.contains('f') ? 'f' : 't');
                }
            } else if(c != ',') {
                stack.push(c);
            }
        }
        return stack.pop() == 't';
    }
}
