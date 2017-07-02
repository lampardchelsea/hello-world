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
 * Solution 1
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
 * 
 * 
 * Solution 2
 * https://leetcode.com/articles/longest-valid-parentheses/
 * Algorithm
 * This problem can be solved by using Dynamic Programming. We make use of a {dp} array where ith element of 
 * {dp} represents the length of the longest valid substring ending at ith index. We initialize the complete {dp}
 * array with 0's. Now, it's obvious that the valid substrings must end with ‘)
 * This further leads to the conclusion that the substrings ending with ‘(’ will always contain '0' at their 
 * corresponding {dp} indices. Thus, we update the {dp} array only when ‘)’ is encountered.
 * To fill {dp} array we will check every two consecutive characters of the string and if

    s[i]=‘)’ and s[i−1]=‘(’ , i.e. string looks like ‘‘.......()"⇒``.......()" ⇒
    dp[i]=dp[i−2]+2
    
 * We do so because the ending "()" portion is a valid substring anyhow and leads to an increment of 2 in the
 * length of the just previous valid substring's length.

   s[i]=‘)’ and s[i−1]=‘)’ , i.e. string looks like ‘‘.......))"⇒``.......))" ⇒
   if s[i−dp[i−1]−1]=‘(’ then
    dp[i]=dp[i−1]+dp[i−dp[i−1]−2]+2

 * The reason behind this is that if the 2nd last ‘)’ was a part of a valid substring (say subs), 
 * for the last ‘)’ to be a part of a larger substring, there must be a corresponding starting ‘(’ which 
 * lies before the valid substring of which the 2nd last ‘)’ is a part (i.e. before subs​​). Thus, 
 * if the character before subs​​ happens to be ‘(’, we update the {dp}[i] as an addition of 2 in the length of 
 * subs​​ which is {dp}[i-1]. To this, we also add the length of the valid substring just before the term 
 * "(,sub_s,)" , i.e. dp[i−dp[i−1]−2].
 * 
 */
public class LongestValidParentheses {
	// Solution 1: Using Stack
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
	
	// Solution 2: DP
    public int longestValidParentheses_2(String s) {
        int maxans = 0;
        int dp[] = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }
	
	
	public static void main(String[] args) {
		
	}
}
