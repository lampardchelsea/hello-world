https://leetcode.com/problems/remove-outermost-parentheses/description/
A valid parentheses string is either empty "", "(" + A + ")", or A + B, where A and B are valid parentheses strings, and + represents string concatenation.
- For example, "", "()", "(())()", and "(()(()))" are all valid parentheses strings.
A valid parentheses string s is primitive if it is nonempty, and there does not exist a way to split it into s = A + B, with A and B nonempty valid parentheses strings.
Given a valid parentheses string s, consider its primitive decomposition: s = P1 + P2 + ... + Pk, where Pi are primitive valid parentheses strings.
Return s after removing the outermost parentheses of every primitive string in the primitive decomposition of s.
 
Example 1:
Input: s = "(()())(())"
Output: "()()()"
Explanation: 
The input string is "(()())(())", with primitive decomposition "(()())" + "(())".
After removing outer parentheses of each part, this is "()()" + "()" = "()()()".

Example 2:
Input: s = "(()())(())(()(()))"
Output: "()()()()(())"
Explanation: 
The input string is "(()())(())(()(()))", with primitive decomposition "(()())" + "(())" + "(()(()))".
After removing outer parentheses of each part, this is "()()" + "()" + "()(())" = "()()()()(())".

Example 3:
Input: s = "()()"
Output: ""
Explanation: 
The input string is "()()", with primitive decomposition "()" + "()".
After removing outer parentheses of each part, this is "" + "" = "".
 
Constraints:
- 1 <= s.length <= 105
- s[i] is either '(' or ')'.
- s is a valid parentheses string.
--------------------------------------------------------------------------------
Attempt 1: 2026-01-18
Solution 1: String (10 min)
Style 1: Intuitive
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        int start = 0;
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                count++;
            } else {
                count--;
            }
            // Find a balanced substring
            if(count == 0) {
                sb.append(s.substring(start + 1, i));
                start = i + 1;
            }
        }
        return sb.toString();
    }
}

Time Complexity: O(n)
Space Complexity: O(n)
Style 2: More elegant
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                // Not the first '(' of primitive
                if(count > 0) {
                    sb.append('(');
                }
                count++;
            } else {
                count--;
                // Not the last ')' of primitive
                if(count > 0) {
                    sb.append(')');
                }
            }
        }
        return sb.toString();
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Solution 2: Stack (10 min)
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                // Not the first '(' of primitive
                if(!stack.isEmpty()) {
                    sb.append(c);
                }
                stack.push(c);
            } else {
                stack.pop();
                // Not the last ')' of primitive
                if(!stack.isEmpty()) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to Deepseek
Problem Understanding
Remove the outermost parentheses of every primitive string in the decomposition of a valid parentheses string.
A primitive string is either:
1."()"
2.(A) where A is a primitive string concatenation
Solution 1: Count Balance (Most Efficient)
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        int balance = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (balance > 0) { // Not the first '(' of primitive
                    result.append(c);
                }
                balance++;
            } else { // c == ')'
                balance--;
                if (balance > 0) { // Not the last ')' of primitive
                    result.append(c);
                }
            }
        }
        
        return result.toString();
    }
}
Solution 2: Using Stack (Conceptual)
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (!stack.isEmpty()) { // Not the outermost '('
                    result.append(c);
                }
                stack.push(c);
            } else { // c == ')'
                stack.pop();
                if (!stack.isEmpty()) { // Not the outermost ')'
                    result.append(c);
                }
            }
        }
        
        return result.toString();
    }
}
Solution 3: Two Pointers (Find Primitive Boundaries)
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        int start = 0;
        int balance = 0;
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                balance++;
            } else {
                balance--;
            }
            
            // Found end of primitive
            if (balance == 0) {
                // Add inner part (excluding first and last chars)
                result.append(s.substring(start + 1, i));
                start = i + 1; // Move to next primitive
            }
        }
        
        return result.toString();
    }
}
Solution 4: Using Counter without StringBuilder
class Solution {
    public String removeOuterParentheses(String s) {
        char[] chars = s.toCharArray();
        int writeIndex = 0;
        int balance = 0;
        
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '(') {
                if (balance > 0) {
                    chars[writeIndex++] = c;
                }
                balance++;
            } else { // c == ')'
                balance--;
                if (balance > 0) {
                    chars[writeIndex++] = c;
                }
            }
        }
        
        return new String(chars, 0, writeIndex);
    }
}
Solution 5: Recursive Approach
class Solution {
    public String removeOuterParentheses(String s) {
        List<String> primitives = new ArrayList<>();
        int start = 0;
        int balance = 0;
        
        // Split into primitives
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') balance++;
            else balance--;
            
            if (balance == 0) {
                primitives.add(s.substring(start, i + 1));
                start = i + 1;
            }
        }
        
        // Remove outer parentheses from each primitive
        StringBuilder result = new StringBuilder();
        for (String primitive : primitives) {
            result.append(primitive.substring(1, primitive.length() - 1));
        }
        
        return result.toString();
    }
}
Solution 6: One-liner with Streams (Java 8+)
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        final int[] balance = {0};
        
        s.chars().forEach(c -> {
            if (c == '(') {
                if (balance[0]++ > 0) result.append((char) c);
            } else {
                if (--balance[0] > 0) result.append((char) c);
            }
        });
        
        return result.toString();
    }
}
Detailed Explanation
Key Insight:
- A primitive string is balanced at the outermost level
- When balance = 0, we've completed a primitive
- The first ( and last ) of each primitive should be removed
Example Walkthrough:
Input: "(()())(())"
Decomposition: "(()())" + "(())"
Process with Solution 1:
String: ( ( ) ( ) ) ( ( ) )
Index:  0 1 2 3 4 5 6 7 8 9

Step-by-step:
c='(' at i=0: balance=0 → skip (outermost)
            balance=1
c='(' at i=1: balance=1>0 → append '(' → result="("
            balance=2
c=')' at i=2: balance=2 → balance=1
            balance=1>0 → append ')' → result="()"
c='(' at i=3: balance=1>0 → append '(' → result="()("
            balance=2
c=')' at i=4: balance=2 → balance=1
            balance=1>0 → append ')' → result="()()"
c=')' at i=5: balance=1 → balance=0
            balance=0 → skip (outermost)
c='(' at i=6: balance=0 → skip (outermost)
            balance=1
c='(' at i=7: balance=1>0 → append '(' → result="()()("
            balance=2
c=')' at i=8: balance=2 → balance=1
            balance=1>0 → append ')' → result="()()()"
c=')' at i=9: balance=1 → balance=0
            balance=0 → skip (outermost)

Result: "()()()"
Complexity Analysis
Solution 1 (Balance Counter):
- Time: O(n) - single pass through string
- Space: O(n) - for StringBuilder (could be O(1) if modifying input array)
Solution 2 (Stack):
- Time: O(n) - single pass
- Space: O(n) - stack space in worst case
Solution 3 (Two Pointers):
- Time: O(n) - single pass
- Space: O(n) - for StringBuilder
Recommended Solutions
Best for Interviews: Solution 1
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        int open = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (open > 0) sb.append(c); // Not outer
                open++;
            } else { // c == ')'
                open--;
                if (open > 0) sb.append(c); // Not outer
            }
        }
        
        return sb.toString();
    }
}
Alternative: Solution 3 (Clear logic)
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        int start = 0, count = 0;
        
        for (int i = 0; i < s.length(); i++) {
            count += s.charAt(i) == '(' ? 1 : -1;
            
            if (count == 0) {
                result.append(s.substring(start + 1, i));
                start = i + 1;
            }
        }
        
        return result.toString();
    }
}
More Examples
Example 1:
Input: "(()())(())"
Output: "()()()"
Explanation: 
The input string is "(()())(())", with primitive decomposition "(()())" + "(())".
After removing outer parentheses of each part, we get "()()" + "()" = "()()()".
Example 2:
Input: "(()())(())(()(()))"
Output: "()()()()(())"
Explanation: 
Primitives: "(()())" + "(())" + "(()(()))"
After removal: "()()" + "()" + "()(())" = "()()()()(())"
Example 3:
Input: "()()"
Output: ""
Explanation: 
Primitives: "()" + "()"
After removal: "" + "" = ""
Edge Cases
1.Single primitive: "()" → ""
2.Nested only: "(())" → "()"
3.Multiple same level: "()()()" → ""
4.Complex nesting: "((()())())" → "(()())()"
5.Empty string: "" → ""
Visual Representation
For "(()())(())":
Original: ( ( ) ( ) ) ( ( ) )
Level:    0 1 2 1 2 1 0 1 2 1
Keep?     N Y Y Y Y N N Y Y N
Result:     ( ) ( )     ( )
Where:
- Level 0: Outermost parentheses (remove)
- Level ≥ 1: Inner parentheses (keep)
Key Insight for Balance Counter
The counter represents nesting depth:
- When we see '(' and depth > 0 → it's not the outermost
- When we see ')' and after decrement depth > 0 → it's not the outermost
Why This Works
For a primitive string S:
- Starts with '(' at depth 0 (remove)
- Ends with ')' at depth 1 before decrement (remove)
- All other characters have depth ≥ 1 (keep)
The balance counter tracks exactly this depth.


Refer to
L20.P11.5.Valid Parentheses (Ref.L32)
L22.Generate Parentheses (Ref.L20,L301)
L32.Longest Valid Parentheses (Ref.L20)
L301.Remove Invalid Parentheses (Ref.L22)
L678.Valid Parenthesis String (Ref.L761,L2116)
L761.Special Binary String (Ref.L678,L856,L2116)
L856.Score of Parentheses (Ref.L678,L761,L2116)
L2116.Check if a Parentheses String Can Be Valid (Ref.L301,L678,L1963)
