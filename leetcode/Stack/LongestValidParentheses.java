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








































https://leetcode.com/problems/longest-valid-parentheses/description/
Given a string containing just the characters '(' and ')', return the length of the longest valid (well-formed) parentheses substring.
 
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
- 0 <= s.length <= 3 * 104
- s[i] is '(', or ')'.
--------------------------------------------------------------------------------
Attempt 1: 2025-07-13
Solution 1: Stack (60 min)
Style 1: With push -1 as dummy begin first
class Solution {
    public int longestValidParentheses(String s) {
        // Stores the maximum length of valid parentheses found
        int maxLen = 0;
        // Stack to keep track of indices
        Stack<Integer> stack = new Stack<>();
        // The -1 serves as a dummy index representing the position 
        // before the string starts (imaginary index -1)
        // It provides a reference point for calculating the length 
        // of valid parentheses starting from index 0
        stack.push(-1);
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                // Push the index of '(' onto the stack
                stack.push(i);
            // When encountering ')'
            } else {
                stack.pop();
                if(stack.isEmpty()) {
                    // When we pop and the stack becomes empty, we 
                    // know we've encountered an unmatched )
                    // We then push the current index as a new boundary marker
                    stack.push(i);
                } else {
                    // Current valid length is i - stack.peek()
                    // Length Calculation:
                    // When we find a valid pair () at indices (i,j), the 
                    // length is j - stack.peek()
                    // Without -1, we wouldn't have a starting reference 
                    // for valid pairs beginning at index 0
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Style 2: Without push -1 as dummy begin first
class Solution {
    public int longestValidParentheses(String s) {
        // Stores the maximum length of valid parentheses found
        int maxLen = 0;
        // Stack to keep track of indices
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                // Push the index of '(' onto the stack
                stack.push(i);
            // When encountering ')'
            } else {
                if(!stack.isEmpty() && s.charAt(stack.peek()) == '(') {
                    // Found a matching pair - pop the matching '(' index
                    stack.pop();
                    // Calculate the start of the current valid substring:
                    // If stack is empty, the valid substring starts from -1 (before index 0)
                    // Otherwise, it starts from the index after the last unmatched ')'
                    maxLen = Math.max(maxLen, stack.isEmpty() ? i + 1 : i - stack.peek());
                } else {
                    // Push unmatched ')'
                    stack.push(i);
                }
            }
        }
        return maxLen;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to Deepseek
Problem Statement
Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
Approaches
1. Stack Approach (Optimal)
- Use a stack to keep track of indices of parentheses
- Initialize stack with -1 to handle base case
- For every '(', push its index
- For every ')':

- Pop from stack
- If stack becomes empty, push current index
- Else, calculate length from current top of stack to current index
2. Dynamic Programming (Alternative)
- Maintain a DP array where dp[i] represents longest valid parentheses ending at i
- Handle different cases based on previous characters
Solution Code (Stack Approach)
class Solution {
    public int longestValidParentheses(String s) {
        int maxLen = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // Base for length calculation
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i); // New base
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }
}
Solution Code (DP Approach)
class Solution {
    public int longestValidParentheses(String s) {
        int maxLen = 0;
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i-1) == '(') {
                    dp[i] = (i >= 2 ? dp[i-2] : 0) + 2;
                } else {
                    int prevLen = dp[i-1];
                    int prevIndex = i - prevLen - 1;
                    if (prevIndex >= 0 && s.charAt(prevIndex) == '(') {
                        dp[i] = dp[i-1] + 2 + (prevIndex > 0 ? dp[prevIndex-1] : 0);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        }
        return maxLen;
    }
}
Explanation
Stack Approach
- Initialization: Start with -1 in stack to handle base case for length calculation
- Processing:

- For '(', push index onto stack
- For ')':

- Pop from stack (matches with last '(')
- If stack empty, push current index (new base)
- Else, calculate length from current top to current index
- Time Complexity: O(n) - single pass through string
- Space Complexity: O(n) - stack space
DP Approach
- DP Array: dp[i] stores longest valid parentheses ending at i
- Cases:

- Immediate pair "()": dp[i] = dp[i-2] + 2
- Nested case "(())": check matching '(' before the valid substring
- Time Complexity: O(n) - single pass through string
- Space Complexity: O(n) - for DP array
Example
Input: ")()())"
Initial State:
- Stack: [-1] (base index)
- maxLen: 0
Processing each character:
1.i=0, ')':
- Pop: removes -1
- Stack is empty → push current index 0
- Stack: [0]
- maxLen remains 0
2.i=1, '(':
- Push current index 1
- Stack: [0, 1]
- maxLen remains 0
3.i=2, ')':
- Pop: removes 1 (matches with '(' at index 1)
- Stack not empty → length = 2 - 0 = 2
- maxLen = max(0, 2) = 2
- Stack: [0]
4.i=3, '(':
- Push current index 3
- Stack: [0, 3]
- maxLen remains 2
5.i=4, ')':
- Pop: removes 3 (matches with '(' at index 3)
- Stack not empty → length = 4 - 0 = 4
- maxLen = max(2, 4) = 4
- Stack: [0]
6.i=5, ')':
- Pop: removes 0
- Stack is empty → push current index 5
- Stack: [5]
- maxLen remains 4
Key Points:
1.Base Index Handling: The initial -1 helps calculate lengths starting from index 0
2.Stack Operations:
- Every '(' pushes its index
- Every ')' pops and either:

- Sets new base if stack becomes empty
- Calculates new valid length otherwise
3.Efficiency:
- Time: O(n) - single pass through the string
- Space: O(n) - worst case stack size
This corrected implementation properly handles all cases and matches the expected behavior for the given example. The longest valid parentheses substring "()()" (indices 1-4) is correctly identified with length 4.
Why we have to write a tricky statement as "stack.push(-1);" first ? and if i don't write this line, what will be the changed solution ?
Great question! The stack.push(-1) initialization is indeed crucial for the algorithm's correctness. Let me explain why it's needed and what would happen without it.
Why We Need stack.push(-1):
1.Base Case Handling:
- The -1 serves as a dummy index representing the position before the string starts (imaginary index -1)
- It provides a reference point for calculating the length of valid parentheses starting from index 0
2.Length Calculation:
- When we find a valid pair () at indices (i,j), the length is j - stack.peek()
- Without -1, we wouldn't have a starting reference for valid pairs beginning at index 0
3.Empty Stack Handling:
- When we pop and the stack becomes empty, we know we've encountered an unmatched )
- We then push the current index as a new boundary marker
What Happens Without stack.push(-1):
1.For Input Starting with '(':
Input: "(()"
Without -1: 
- Stack becomes empty after first ')', pushes index 1
- Misses valid length calculation for index 0-1
2.For Input Starting with ')':
Input: ")()"
Without -1:
- First ')' causes EmptyStackException (nothing to pop)
Modified Solution Without stack.push(-1):
If you really want to avoid the -1 initialization, you'd need to:
1.Handle empty stack cases differently
2.Add special checks for the start of the string
3.Make the code more complex
Here's how it would look (but I don't recommend this):
class Solution {
    public int longestValidParentheses(String s) {
        int maxLen = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                if (!stack.isEmpty() && s.charAt(stack.peek()) == '(') {
                    stack.pop();
                    int start = stack.isEmpty() ? -1 : stack.peek();
                    maxLen = Math.max(maxLen, i - start);
                } else {
                    stack.push(i); // Push unmatched ')'
                }
            }
        }
        return maxLen;
    }
}
Why the Original is Better:
1.Cleaner Logic:
- The -1 initialization provides a uniform way to handle all cases
- No need for special checks for the string start
2.Easier to Understand:
- The i - stack.peek() formula works consistently for all valid pairs
- Without -1, you need conditional logic for empty stack cases
3.More Robust:
- Handles edge cases like (), ((), )() uniformly
- No risk of EmptyStackException
Example Comparison:
Input: "()"
With -1:
1.Stack: [-1]
2.'(' at 0: push → [-1,0]
3.')' at 1: pop → peek is -1 → length = 1 - (-1) = 2
Without -1:
1.Stack: []
2.'(' at 0: push → [0]
3.')' at 1: pop → stack empty → need special handling
The -1 initialization makes the algorithm more elegant and uniform in handling all cases. While it's possible to write a solution without it, the resulting code would be more complex and harder to maintain.


Refer to
L20.P11.5.Valid Parentheses (Ref.L32)
