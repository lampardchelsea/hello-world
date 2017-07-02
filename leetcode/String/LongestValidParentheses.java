import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/longest-valid-parentheses/#/description
 * Given a string containing just the characters '(' and ')', find the length of the 
 * longest valid (well-formed) parentheses substring.
 * For "(()", the longest valid parentheses substring is "()", which has length = 2.
 * Another example is ")()())", where the longest valid parentheses substring is "()()", 
 * which has length = 4. 
 *
 * Solution
 * https://leetcode.com/articles/longest-valid-parentheses/
 * Approach #3 Using Stack [Accepted]
 * Algorithm
 * Instead of finding every possible string and checking its validity, we can make use of 
 * stack while scanning the given string to check if the string scanned so far is valid, 
 * and also the length of the longest valid string. In order to do so, we start by pushing 
 * −1 onto the stack (This is a magic step).
 * For every ‘(’ encountered, we push its index onto the stack.
 * For every ‘)’ encountered, we pop the topmost element and subtract the current element's 
 * index from the top element of the stack, which gives the length of the currently 
 * encountered valid string of parentheses. If while popping the element, the stack 
 * becomes empty, we push the current element's index onto the stack. In this way, we keep 
 * on calculating the lengths of the valid substrings, and return the length of the longest 
 * valid string at the end.
 * We can use to simulate the process
 * ( ) ) ( ( ( ) ) 
 * 0 1 2 3 4 5 6 7
 * 
 * Complexity Analysis
 * Time complexity : O(n). n is the length of the given string.
 * Space complexity : O(n). The size of stack can go up to n.
 */
public class LongestValidParentheses {
	public int longestValidParentheses(String s) {
		int max = 0;
		Stack<Integer> stack = new Stack<Integer>();
		// A magic push of -1
		stack.push(-1);
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '(') {
				stack.push(i);
			} else {
				// Make sure pop first, the magic push of -1 avoid
				// problem on string stack with push ')'
				stack.pop();
				if(stack.empty()) {
					stack.push(i);
				} else {
					max = Math.max(max, i - stack.peek());
				}
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		
	}
}
