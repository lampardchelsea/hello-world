/**
Refer to
https://leetcode.com/problems/score-of-parentheses/
Given a balanced parentheses string S, compute the score of the string based on the following rule:

() has score 1
AB has score A + B, where A and B are balanced parentheses strings.
(A) has score 2 * A, where A is a balanced parentheses string.

Example 1:
Input: "()"
Output: 1

Example 2:
Input: "(())"
Output: 2

Example 3:
Input: "()()"
Output: 2

Example 4:
Input: "(()(()))"
Output: 6

Note:
S is a balanced parentheses string, containing only ( and ).
2 <= S.length <= 50
*/

// Solution 1: Java Basic Stack-Based Solution O(n) time O(n) space
// Refer to
// https://leetcode.com/problems/score-of-parentheses/discuss/197544/Java-Basic-Stack-Based-Solution-O(n)-time-O(n)-space
/**
Our line of thinking is like this: when you see a new opening bracket, you'll later need to double the "score" of everything 
inside of it. When you see a closing bracket, pop the stack to retrieve the "score" of what's inside the pair, double the 
score (because of (A) = 2 * A, given in problem), then use it as a sum term; if it's inside another bracket, then add that 
to what's now at the top of the stack, because it needs to be part of that "doubling" operation. If the stack is empty, 
then just add it straight to your final answer.

The ternary operator in the popping operation area is because if you end up with '()', there is nothing inside of it, but the 
score should be 1, not 0. So, we treat that as a special case.

class Solution {
    public int scoreOfParentheses(String S) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c == '(') {
                stack.push(0);
            } else if (c == ')') {
                int pop = stack.pop() * 2;
                pop = (pop == 0) ? 1 : pop;
                if (! stack.isEmpty()) {
                    int top = stack.pop();
                    stack.push(top + pop);
                } else {
                    res += pop;
                }
            }
        }
        return res;
    }
}
*/
class Solution {
    public int scoreOfParentheses(String S) {
        int result = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for(char c : S.toCharArray()) {
            if(c == '(') {
                // When encounter open bracket, push 0 on stack
                // to initialize calculate for this section (the
                // actual calculate will happen when encounter
                // its corresponding close bracket)
                stack.push(0);
            } else {
                // When encounter a close bracket, pop current
                // score from stack and start actual calculate
                int cur_score = stack.pop();
                // Based on given example, we have two scenarios:
                // 1. If current close bracket's corresponding open bracket just 
                // one index ahead, and construct "()", e.g "(()(()))" when encounter 
                // ')' at index = 2, cur_score is 0 and it pushed onto stack because
                // '(' at index = 1, these 2 chars able to construct "()", so when
                // calculate we update the current score from 0 to 1
                // 2. If current close bracket's corresponding open bracket not
                // one index ahead, e.g "(()(()))" when encounter ')' at index = 6,
                // its corresponding '(' at index = 3, cur_score is 1 and we should
                // update from 1 to 1 * 2 = 2
                if(cur_score == 0) {
                    cur_score = 1;
                } else {
                    cur_score *= 2;
                }
                // Most important part: after pop current score (which actually means
                // pop out current ')'), if the stack not empty, it means at same
                // level (from current ')' close bracket's perspective), we have 
                // previous calculated section stored onto stack, we have to sum up
                // previous section and current section, so pop peek value which
                // means previous section score plus current score and push the sum
                // onto stack to finish sum up
                if(!stack.isEmpty()) {
                    stack.push(stack.pop() + cur_score);
                } else {
                    // Or if stack is empty, which means we reach the out most level
                    // of brackets, just add onto result
                    result += cur_score;
                }
            }
        }
        return result;
    }
}

// Solution 2: 
// Refer to
// https://leetcode.com/problems/score-of-parentheses/discuss/141777/C%2B%2BJavaPython-O(1)-Space
/**
Approach 0: Stack
cur record the score at the current layer level.

If we meet '(',
we push the current score to stack,
enter the next inner layer level,
and reset cur = 0.

If we meet ')',
the cur score will be doubled and will be at least 1.
We exit the current layer level,
and set cur += stack.pop() + cur

Complexity: O(N) time and O(N) space

Java

    public int scoreOfParentheses(String S) {
        Stack<Integer> stack = new Stack<>();
        int cur = 0;
        for (char c : S.toCharArray()) {
            if (c == '(') {
                stack.push(cur);
                cur = 0;
            } else {
                cur = stack.pop() + Math.max(cur * 2, 1);
            }
        }
        return cur;
    }
*/
