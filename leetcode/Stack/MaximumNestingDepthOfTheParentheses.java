https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/description/
Given a valid parentheses string s, return the nesting depth of s. The nesting depth is the maximum number of nested parentheses.
 
Example 1:
Input: s = "(1+(2*3)+((8)/4))+1"
Output: 3
Explanation:
Digit 8 is inside of 3 nested parentheses in the string.

Example 2:
Input: s = "(1)+((2))+(((3)))"
Output: 3
Explanation:
Digit 3 is inside of 3 nested parentheses in the string.

Example 3:
Input: s = "()(())((()()))"
Output: 3
 
Constraints:
- 1 <= s.length <= 100
- s consists of digits 0-9 and characters '+', '-', '*', '/', '(', and ')'.
- It is guaranteed that parentheses expression s is a VPS.
--------------------------------------------------------------------------------
Attempt 1: 2026-01-19
Solution 1: Math (10 min)
class Solution {
    public int maxDepth(String s) {
        // Ignore digits and signs, only count the current open parentheses.
        // The depth equals to the maximum open parentheses.
        int max = 0;
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Each '(' increases nesting depth by 1
            if(c == '(') {
                count++;
                // Track maximum depth reached during traversal
                max = Math.max(count, max);
            // Each ')' decreases nesting depth by 1
            } else {
                count--;
            }
        }
        return max;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Solution 2: Stack (10 min)
class Solution {
    public int maxDepth(String s) {
        Stack<Character> stack = new Stack<>();
        int maxDepth = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
                maxDepth = Math.max(maxDepth, stack.size());
            } else if (c == ')') {
                stack.pop();
            }
        }
        return maxDepth;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to Deepseek
Problem Understanding
Given a valid parentheses string s, return the maximum nesting depth of the parentheses.
Nesting depth is defined as:
- "()" has depth 1
- "(())" has depth 2
- "()(())" has depth 2 (maximum of both parts)
- "((()))" has depth 3
Solution 1: Simple Counter (Most Efficient)
class Solution {
    public int maxDepth(String s) {
        int maxDepth = 0;
        int currentDepth = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                currentDepth++;
                maxDepth = Math.max(maxDepth, currentDepth);
            } else if (c == ')') {
                currentDepth--;
            }
            // Ignore other characters
        }
        
        return maxDepth;
    }
}
Solution 2: Using Stack (Conceptual)
class Solution {
    public int maxDepth(String s) {
        Stack<Character> stack = new Stack<>();
        int maxDepth = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
                maxDepth = Math.max(maxDepth, stack.size());
            } else if (c == ')') {
                stack.pop();
            }
        }
        
        return maxDepth;
    }
}
Solution 3: One-liner with Streams (Java 8+)
class Solution {
    public int maxDepth(String s) {
        final int[] depth = {0};
        final int[] max = {0};
        
        s.chars().forEach(c -> {
            if (c == '(') max[0] = Math.max(max[0], ++depth[0]);
            else if (c == ')') depth[0]--;
        });
        
        return max[0];
    }
}
Solution 4: Without Math.max (Optimized)
class Solution {
    public int maxDepth(String s) {
        int depth = 0;
        int max = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                depth++;
                if (depth > max) max = depth;
            } else if (c == ')') {
                depth--;
            }
        }
        
        return max;
    }
}
Solution 5: Array-based (No Objects)
class Solution {
    public int maxDepth(String s) {
        char[] chars = s.toCharArray();
        int depth = 0;
        int max = 0;
        
        for (char c : chars) {
            if (c == '(') {
                depth++;
                if (depth > max) max = depth;
            } else if (c == ')') {
                depth--;
            }
        }
        
        return max;
    }
}
Solution 6: Using Recursion (For Understanding)
class Solution {
    public int maxDepth(String s) {
        return helper(s, 0, 0, 0);
    }
    
    private int helper(String s, int index, int currentDepth, int maxDepth) {
        if (index == s.length()) {
            return maxDepth;
        }
        
        char c = s.charAt(index);
        if (c == '(') {
            currentDepth++;
            maxDepth = Math.max(maxDepth, currentDepth);
        } else if (c == ')') {
            currentDepth--;
        }
        
        return helper(s, index + 1, currentDepth, maxDepth);
    }
}
Detailed Explanation
Key Insight:
- Each '(' increases nesting depth by 1
- Each ')' decreases nesting depth by 1
- Track maximum depth reached during traversal
Example Walkthrough:
Example 1: "(1+(2*3)+((8)/4))+1"
Process:
Char  Action          depth  max
'('   depth=1        1      1
'1'   ignore         1      1
'+'   ignore         1      1
'('   depth=2        2      2
'2'   ignore         2      2
'*'   ignore         2      2
'3'   ignore         2      2
')'   depth=1        1      2
'+'   ignore         1      2
'('   depth=2        2      2
'('   depth=3        3      3
'8'   ignore         3      3
')'   depth=2        2      3
'/'   ignore         2      3
'4'   ignore         2      3
')'   depth=1        1      3
')'   depth=0        0      3
'+'   ignore         0      3
'1'   ignore         0      3

Result: 3
Example 2: "()(())((()()))"
Process:
Char  depth  max
'('   1      1
')'   0      1
'('   1      1
'('   2      2
')'   1      2
')'   0      2
'('   1      2
'('   2      2
'('   3      3
')'   2      3
'('   3      3
')'   2      3
')'   1      3
')'   0      3

Result: 3
Complexity Analysis
All solutions:
- Time: O(n) - single pass through string
- Space: O(1) - Solution 1,4,5 use constant space
- Space: O(n) - Solution 2 (stack) worst case
Recommended Solutions
Best Solution: Solution 1
class Solution {
    public int maxDepth(String s) {
        int max = 0;
        int current = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                current++;
                max = Math.max(max, current);
            } else if (c == ')') {
                current--;
            }
        }
        
        return max;
    }
}
Alternative: Solution 4 (Slightly faster)
class Solution {
    public int maxDepth(String s) {
        int depth = 0;
        int max = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                depth++;
                if (depth > max) max = depth;
            } else if (c == ')') {
                depth--;
            }
        }
        
        return max;
    }
}
Edge Cases
1.No parentheses: "abc" → 0
2.Single pair: "()" → 1
3.Nested only: "((()))" → 3
4.Multiple same level: "()()()" → 1
5.Mixed: "(())()(()())" → 2
6.Empty string: "" → 0
Visual Representation
For "(1+(2*3)+((8)/4))+1":
String:  ( 1 + ( 2 * 3 ) + ( ( 8 ) / 4 ) ) + 1
Depth:   1     2           3 3 2     1 0
Max:     1     2           3
For "()(())((()()))":
String:  ( ) ( ( ) ) ( ( ( ) ( ) ) )
Depth:   1 0 1 2 1 0 1 2 3 2 3 2 1 0
Max:     1     2         3
Why Stack is Overkill
The problem guarantees valid parentheses, so:
- We don't need to validate correctness
- We just need to track current depth
- Simple counter suffices
Stack solution (Solution 2) works but:
- Uses O(n) space in worst case
- Creates unnecessary objects
- Slower due to push/pop operations
Mathematical Insight
For valid parentheses:
- Depth counter always non-negative
- Ends at 0 (all parentheses matched)
- Maximum depth = maximum value of counter during traversal
Similar Problems
1.LeetCode 1614 (this): Max nesting depth
2.LeetCode 1111 (Max Depth After Split): Split into two sequences
3.LeetCode 921 (Minimum Add): Make parentheses valid
4.LeetCode 1021 (Remove Outermost): Remove outer parentheses
Test Cases
// Test cases to verify
String[] tests = {
    "(1+(2*3)+((8)/4))+1",  // Expected: 3
    "(1)+((2))+(((3)))",    // Expected: 3  
    "()(())((()()))",       // Expected: 3
    "()()",                 // Expected: 1
    "((()))",               // Expected: 3
    "abc",                  // Expected: 0
    "",                     // Expected: 0
    "((((()))))",           // Expected: 5
};
Performance Comparison
All solutions are O(n) time, but:
- Solution 1: O(1) space, fastest
- Solution 2: O(n) space, slower due to stack operations
- Solution 3: Functional style, clean but creates lambdas
- Solution 4: Manual max check, slightly faster than Math.max
Memory Optimization
If memory is critical:
class Solution {
    public int maxDepth(String s) {
        int depth = 0, max = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);  // No toCharArray() allocation
            if (c == '(') {
                if (++depth > max) max = depth;
            } else if (c == ')') {
                depth--;
            }
        }
        return max;
    }
}
Summary
This is one of the simplest parentheses problems. The optimal solution:
1.Initialize currentDepth = 0, maxDepth = 0
2.Traverse string character by character
3.Increment currentDepth on '(', update maxDepth
4.Decrement currentDepth on ')'
5.Return maxDepth
Time: O(n), Space: O(1), where n is string length.


Refer to
L921.Minimum Add to Make Parentheses Valid (Ref.L1249,L1963)
L1021.Remove Outermost Parentheses (Ref.L678,L761,L2116)
L1111.Maximum Nesting Depth of Two Valid Parentheses Strings (Ref.L1614)
