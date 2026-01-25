https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/description/
A parentheses string is valid if and only if:
- It is the empty string,
- It can be written as AB (A concatenated with B), where A and B are valid strings, or
- It can be written as (A), where A is a valid string.
You are given a parentheses string s. In one move, you can insert a parenthesis at any position of the string.
- For example, if s = "()))", you can insert an opening parenthesis to be "(()))" or a closing parenthesis to be "())))".
Return the minimum number of moves required to make s valid.
 
Example 1:
Input: s = "())"
Output: 1

Example 2:
Input: s = "((("
Output: 3
 
Constraints:
- 1 <= s.length <= 1000
- s[i] is either '(' or ')'.
--------------------------------------------------------------------------------
Attempt 1: 2026-01-19
Solution 1: Math (10 min)
class Solution {
    public int minAddToMakeValid(String s) {
        int openRequired = 0;
        int closeRequired = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                closeRequired++;
            } else {
                if(closeRequired > 0) {
                    closeRequired--;
                } else {
                    openRequired++;
                }
            }
        }
        return openRequired + closeRequired;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Solution 2: Stack (10 min)
class Solution {
    public int minAddToMakeValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(c);
            } else {
                if(!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Valid pair
                } else {
                    stack.push(c); // Unmatched ')'
                }
            }
        }
        // Remaining stack size = unmatched parentheses (either '(' or ')')
        return stack.size();
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to Deepseek
Problem Understanding
Given a string of parentheses '(' and ')', return the minimum number of parentheses we must add to make the string valid.
A parentheses string is valid if:
1.It is empty
2.It can be written as AB (A concatenated with B)
3.It can be written as (A) where A is valid
Solution 1: Balance Counter (Most Efficient)
class Solution {
    public int minAddToMakeValid(String s) {
        int openNeeded = 0;  // Count of '(' needed
        int closeNeeded = 0; // Count of ')' needed
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                // Need a closing parenthesis for this '('
                closeNeeded++;
            } else { // c == ')'
                if (closeNeeded > 0) {
                    // We have an unmatched '(' that this can close
                    closeNeeded--;
                } else {
                    // No unmatched '(' to close, need an opening parenthesis
                    openNeeded++;
                }
            }
        }
        
        return openNeeded + closeNeeded;
    }
}
Solution 2: Simplified Balance Counter
class Solution {
    public int minAddToMakeValid(String s) {
        int open = 0;  // Current unmatched '(' count
        int result = 0; // Total additions needed
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                open++; // Need a matching ')'
            } else { // c == ')'
                if (open > 0) {
                    open--; // Match with existing '('
                } else {
                    result++; // Need to add '(' for this ')'
                }
            }
        }
        
        // Add needed ')' for unmatched '('
        return result + open;
    }
}
Solution 3: Using Stack (Conceptual)
class Solution {
    public int minAddToMakeValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else { // c == ')'
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Valid pair
                } else {
                    stack.push(c); // Unmatched ')'
                }
            }
        }
        
        // Remaining stack size = unmatched parentheses
        return stack.size();
    }
}
Solution 4: Two-pass Approach
class Solution {
    public int minAddToMakeValid(String s) {
        int additions = 0;
        int balance = 0;
        
        // First pass: handle missing '(' for ')'
        for (char c : s.toCharArray()) {
            balance += (c == '(') ? 1 : -1;
            if (balance < 0) { // More ')' than '('
                additions++;    // Need to add '('
                balance = 0;    // Reset balance
            }
        }
        
        // Second: add ')' for unmatched '('
        return additions + balance;
    }
}
Solution 5: One-pass with Single Counter
class Solution {
    public int minAddToMakeValid(String s) {
        int balance = 0;
        int ans = 0;
        
        for (char c : s.toCharArray()) {
            balance += c == '(' ? 1 : -1;
            
            // If balance becomes -1, we have an extra ')'
            if (balance == -1) {
                ans++;      // Need to add '('
                balance = 0; // Reset balance
            }
        }
        
        return ans + balance; // balance = unmatched '(' count
    }
}
Solution 6: Constant Space with Two Pointers
class Solution {
    public int minAddToMakeValid(String s) {
        int open = 0; // Unmatched '('
        int close = 0; // Unmatched ')'
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                open++;
            } else {
                if (open > 0) {
                    open--; // Match
                } else {
                    close++; // Unmatched ')'
                }
            }
        }
        
        return open + close;
    }
}
Detailed Explanation
Key Insight:
- Each '(' needs a matching ')' to its right
- Each ')' needs a matching '(' to its left
- We can track the balance: +1 for '(', -1 for ')'
Example Walkthrough:
Example 1: "())"
Solution 2 trace:
s = "())"
result = 0, open = 0

c='(' → open=1
c=')' → open>0 → open=0
c=')' → open=0 → result=1

Result: 1 + 0 = 1
Example 2: "((("
Solution 2 trace:
s = "((("
result = 0, open = 0

c='(' → open=1
c='(' → open=2
c='(' → open=3

Result: 0 + 3 = 3
Example 3: "()))(("
Solution 2 trace:
s = "()))(("
result = 0, open = 0

c='(' → open=1
c=')' → open>0 → open=0
c=')' → open=0 → result=1
c=')' → open=0 → result=2
c='(' → open=1
c='(' → open=2

Result: 2 + 2 = 4
Complexity Analysis
Solution 1/2 (Balance Counter):
- Time: O(n) - single pass through string
- Space: O(1) - only variables
Solution 3 (Stack):
- Time: O(n) - single pass
- Space: O(n) - stack in worst case
Recommended Solutions
Best for Interviews: Solution 2
class Solution {
    public int minAddToMakeValid(String s) {
        int open = 0;  // Unmatched '(' count
        int add = 0;   // Additions needed
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                open++;
            } else {
                if (open > 0) {
                    open--;  // Match with existing '('
                } else {
                    add++;   // Need to add '(' for this ')'
                }
            }
        }
        
        return add + open; // Add '(' for unmatched ')', add ')' for unmatched '('
    }
}
Alternative: Solution 5 (Mathematically Elegant)
class Solution {
    public int minAddToMakeValid(String s) {
        int balance = 0, ans = 0;
        
        for (char c : s.toCharArray()) {
            balance += c == '(' ? 1 : -1;
            if (balance < 0) { // Too many ')'
                ans++;          // Need '('
                balance = 0;    // Reset
            }
        }
        
        return ans + balance; // balance = unmatched '('
    }
}
Visual Representation
For "()))((":
String:  (  )  )  )  (  (
Index:   0  1  2  3  4  5
Balance: 1  0 -1 -2 -1  0
                ↑  ↑
            Need '(' here
            (balance < 0)
            
Open at end: 2 (positions 4,5)
Add for negative: 2 (positions 2,3)
Total: 4
For "()":
String:  (  )
Index:   0  1
Balance: 1  0
Total: 0
Edge Cases
1.Empty string: "" → 0
2.All open: "(((" → 3
3.All close: ")))" → 3
4.Alternating: "()()()" → 0
5.Nested: "(()())" → 0
6.Mixed: "())(" → 2
Why It Works
Intuition:
- When we see ')' without a preceding unmatched '(', we need to add '('
- At the end, any remaining unmatched '(' need ')' to close them
Mathematical Proof:
Let:
- open = count of unmatched '('
- add = count of '(' we need to add
Algorithm:
1.Initialize open = 0, add = 0
2.For each '(': open++
3.For each ')':
- If open > 0: open-- (match)
- Else: add++ (need to add '(')
4.Answer = add + open
This is optimal because:
- Each unmatched ')' must be fixed by adding '(' before it
- Each unmatched '(' must be fixed by adding ')' after it
Comparison with Similar Problems
LeetCode 921 vs LeetCode 1249 (Minimum Remove to Make Valid):
- 921: Add minimum parentheses
- 1249: Remove minimum parentheses
Similar logic but opposite operations!
Extended Example
Input: ")(()))("
Process:
Char  Action          open  add
' )'  open=0 → add++   0     1
' ('  open++           1     1
' ('  open++           2     1  
' )'  open>0 → open--  1     1
' )'  open>0 → open--  0     1
' )'  open=0 → add++   0     2
' ('  open++           1     2

Result: add + open = 2 + 1 = 3
Verification:
- Add '(' at beginning: "()(())("
- Add '(' before last ')': "()(())()"
- Add ')' at end: "()(())()"
Result: "()(())()" is valid with 3 additions.
Summary
The optimal solution uses a simple counter:
1.Track unmatched '(' with open counter
2.When we see ')' without a matching '(', increment add counter
3.Final answer = add (missing '(') + open (missing ')')
This is O(n) time, O(1) space, and easy to understand and implement.


Refer to
L20.P11.5.Valid Parentheses (Ref.L32)
L22.Generate Parentheses (Ref.L20,L301)
L32.Longest Valid Parentheses (Ref.L20)
L241.Different Ways to Add Parentheses (Ref.L95)
L301.Remove Invalid Parentheses (Ref.L22)
L856.Score of Parentheses (Ref.L678,L761,L2116)
L1021.Remove Outermost Parentheses (Ref.L678,L761,L2116)
L1249.Minimum Remove to Make Valid Parentheses (Ref.L921)
L1541.Minimum Insertions to Balance a Parentheses String (Ref.L921,L1963)
L1963.Minimum Number of Swaps to Make the String Balanced (Ref.L301,L2116)
