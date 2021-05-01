/**
Refer to
https://leetcode.com/problems/longest-valid-parentheses/
Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

Example 1:
Input: s = "(()"
Output: 2
Explanation: The longest valid parentheses substring is "()".

Example 2:
Input: s = ")()())"
Output: 4
Explanation: The longest valid parentheses substring is "()()".

Example 3:
Input: s = ""
Output: 0

Constraints:
0 <= s.length <= 3 * 104
s[i] is '(', or ')'.
*/

// Solution 1: Stack + Previous LVP array
// Refer to
// https://leetcode.com/problems/longest-valid-parentheses/discuss/269017/java-7ms-solution-with-stack
/**
len[i] is the length of longest valid substring which ends at the ith element of s.
Apparently, if s[i] == '(' then len[i] = 0 . If s[i] == ')', we calculate len[i] under the help of stack.

public int longestValidParentheses(String s) {
	Stack<Integer> stack = new Stack<Integer>();
	int len[] = new int[s.length()];
	int max = 0;
	for(int i = 0; i < s.length(); i++) {
		if( s.charAt(i) == '(' )
			stack.push(i);
		else if( !stack.isEmpty() ) {
			len[i] = i - stack.peek() + 1; // Currently,len[i] only reprensents the distance between s[i] and its pair '('
			len[i] += stack.peek() > 0 ? len[stack.peek() - 1] : 0; // plus the length of longest valid substring which ends at the previous element of pair '('
			max = Math.max(len[i], max);
			stack.pop();
		}
	}
	return max;
*/
class Solution {
    public int longestValidParentheses(String s) {
        int max = 0;
        int n = s.length();
        Stack<Integer> stack = new Stack<Integer>();
        int[] len = new int[n];
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(i);
            } else {
                if(!stack.isEmpty()) {
                    // Currently, len[i] only reprensents the distance 
                    // between s[i] and its pair '('
                    len[i] = i - stack.peek() + 1;
                    // Its similar to a DP thought, need to plus the length of longest 
                    // valid substring which ends at the previous element of pair '(',
                    // to calculate the accumulate length
                    // We can test out how it works by 2 cases:
                    // 1. )()()) 2. )())())
                    if(stack.peek() > 0) {
                        len[i] += len[stack.peek() - 1];
                    }
                    max = Math.max(max, len[i]);
                    stack.pop();
                }
            }
        }
        return max;
    }
}

// Solution 2:
// Refer to
// 
/**
Approach 3: Using Stack
Algorithm

Instead of finding every possible string and checking its validity, we can make use of stack while scanning the given string 
to check if the string scanned so far is valid, and also the length of the longest valid string. In order to do so, we start 
by pushing -1 onto the stack.

For every ‘(’ encountered, we push its index onto the stack.

For every ‘)’ encountered, we pop the topmost element and subtract the current element's index from the top element 
of the stack, which gives the length of the currently encountered valid string of parentheses. If while popping the element, 
the stack becomes empty, we push the current element's index onto the stack. In this way, we keep on calculating the lengths 
of the valid substrings, and return the length of the longest valid string at the end.

See this example for better understanding.
*/
public class Solution {
    public int longestValidParentheses(String s) {
        int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }
}
