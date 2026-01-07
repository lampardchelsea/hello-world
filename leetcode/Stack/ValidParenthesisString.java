https://leetcode.com/problems/valid-parenthesis-string/description/
Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
The following rules define a valid string:
- Any left parenthesis '(' must have a corresponding right parenthesis ')'.
- Any right parenthesis ')' must have a corresponding left parenthesis '('.
- Left parenthesis '(' must go before the corresponding right parenthesis ')'.
- '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
 
Example 1:
Input: s = "()"
Output: true

Example 2:
Input: s = "(*)"
Output: true

Example 3:
Input: s = "(*))"
Output: true
 
Constraints:
- 1 <= s.length <= 100
- s[i] is '(', ')' or '*'.
--------------------------------------------------------------------------------
Attempt 1: 2026-01-04
Solution 1: Greedy + Math (10 min)
Style 1: Two-Pass Greedy (Exactly same as L2116.Check if a Parentheses String Can Be Valid (Ref.L301,L678,L1963))
class Solution {
    public boolean checkValidString(String s) {
        int balance = 0;
        int n = s.length();
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            // Check from left to right - treat * as (
            if(c == '(' || c == '*') {
                balance++;
            } else {
                balance--;
            }
            if(balance < 0) {
                return false;
            }
        }
        // Check from right to left - treat * as )
        balance = 0;
        for(int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if(c == ')' || c == '*') {
                balance++;
            } else {
                balance--;
            }
            if(balance < 0) {
                return false;
            }
        }
        return true;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Style 2: Range tracking
class Solution {
    public boolean checkValidString(String s) {
        int n = s.length();
        int minOpen = 0;
        int maxOpen = 0;
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == '(') {
                minOpen++;
                maxOpen++;
            } else if(c == ')') {
                // Cannot be less than 0
                minOpen = Math.max(minOpen - 1, 0);
                maxOpen--;
                // Too many closing brackets
                if(maxOpen < 0) {
                    return false;
                }
            } else {
                // '*' as ')'
                minOpen = Math.max(minOpen - 1, 0);
                // '*' as '('
                maxOpen++;
            }
        }
        // Can we achieve exactly 0 open brackets?
        return minOpen == 0;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Solution 2: Native DFS (10 min, TLE 82/83)
class Solution {
    public boolean checkValidString(String s) {
        return helper(s, 0, 0);
    }

    private boolean helper(String s, int index, int open) {
        // Base case
        if(index == s.length()) {
            return open == 0;
        }
        // Impossible to balance
        if(open < 0 || open > s.length() - index) {
            return false;
        }
        char c = s.charAt(index);
        if(c == '(') {
            return helper(s, index + 1, open + 1); 
        } else if(c == ')') {
            return helper(s, index + 1, open - 1);
        } else {
            // * as ( and * as ) and * as empty string
            return helper(s, index + 1, open + 1) || helper(s, index + 1, open - 1) || helper(s, index + 1, open); 
        }
    }
}

Time Complexity: O(3^n)
Space Complexity: O(n)

Solution 3: DFS + Memoization (10 min)
class Solution {
    public boolean checkValidString(String s) {
        int n = s.length();
        Boolean[][] memo = new Boolean[n][n + 1];
        return helper(s, 0, 0, memo);
    }

    private boolean helper(String s, int index, int open, Boolean[][] memo) {
        // Base case
        if(index == s.length()) {
            return open == 0;
        }
        // Impossible to balance
        if(open < 0 || open > s.length() - index) {
            return false;
        }
        if(memo[index][open] != null) {
            return memo[index][open];
        }
        char c = s.charAt(index);
        if(c == '(') {
            return memo[index][open] = helper(s, index + 1, open + 1, memo); 
        } else if(c == ')') {
            return memo[index][open] = helper(s, index + 1, open - 1, memo);
        } else {
            // * as ( and * as ) and * as empty string
            return memo[index][open] = (helper(s, index + 1, open + 1, memo) || helper(s, index + 1, open - 1, memo) || helper(s, index + 1, open, memo));
        }
    }
}

Time Complexity: O(n^2)
Space Complexity: O(n^2)

Solution 4: Two Stacks (30 min)
class Solution {
    public boolean checkValidString(String s) {
        Stack<Integer> openStack = new Stack<>();
        Stack<Integer> starStack = new Stack<>();
        
        // First pass: handle closing brackets
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                openStack.push(i);
            } else if (c == '*') {
                starStack.push(i);
            } else { // c == ')'
                if (!openStack.isEmpty()) {
                    openStack.pop();
                } else if (!starStack.isEmpty()) {
                    starStack.pop();
                } else {
                    return false;
                }
            }
        }
        
        // Second pass: match remaining open brackets with stars
        // We need stars to appear AFTER the open brackets
        while (!openStack.isEmpty() && !starStack.isEmpty()) {
            int openPos = openStack.pop();
            int starPos = starStack.pop();
            
            // Star must come AFTER the open bracket to close it
            if (starPos < openPos) {
                // This star is useless for remaining open brackets
                // But since stacks are LIFO, all remaining stars are even earlier
                return false;
            }
            // Successfully matched open bracket at openPos with star at starPos
        }
        
        return openStack.isEmpty();
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to QWEN
Looking at LeetCode 678. Valid Parenthesis String, this problem involves checking if a string with '(', ')', and '' characters can be valid. The key insight is that '' can act as '(', ')', or an empty string.
Key Approaches
Approach 1: Two-Pass Greedy (Most Intuitive)
The idea is to check validity in both directions:
- Left to right: Treat '*' as '(' when needed (maximize open brackets)
- Right to left: Treat '*' as ')' when needed (maximize close brackets)
class Solution {
    public boolean checkValidString(String s) {
        // Check from left to right - treat * as (
        int open = 0;
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '*') {
                open++;
            } else { // c == ')'
                open--;
                if (open < 0) return false; // Too many closing brackets
            }
        }
        
        // Check from right to left - treat * as )
        int close = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')' || c == '*') {
                close++;
            } else { // c == '('
                close--;
                if (close < 0) return false; // Too many opening brackets
            }
        }
        
        return true;
    }
}
Approach 2: Range Tracking (Most Efficient)
Track the possible range of open parentheses:
- minOpen: minimum possible open parentheses (treat * as ')')
- maxOpen: maximum possible open parentheses (treat * as '(')
class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0, maxOpen = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                minOpen++;
                maxOpen++;
            } else if (c == ')') {
                minOpen = Math.max(0, minOpen - 1); // Can't go below 0
                maxOpen--;
                if (maxOpen < 0) return false; // Too many closing brackets
            } else { // c == '*'
                minOpen = Math.max(0, minOpen - 1); // * as )
                maxOpen++; // * as (
            }
        }
        
        return minOpen == 0; // Can we achieve exactly 0 open brackets?
    }
}
Approach 3: Dynamic Programming
Use memoization to track if we can balance the string starting from position i with open parentheses.
class Solution {
    public boolean checkValidString(String s) {
        Boolean[][] memo = new Boolean[s.length()][s.length() + 1];
        return dp(s, 0, 0, memo);
    }
    
    private boolean dp(String s, int i, int open, Boolean[][] memo) {
        if (i == s.length()) {
            return open == 0;
        }
        
        if (open < 0 || open > s.length() - i) {
            return false; // Impossible to balance
        }
        
        if (memo[i][open] != null) {
            return memo[i][open];
        }
        
        char c = s.charAt(i);
        boolean result = false;
        
        if (c == '(') {
            result = dp(s, i + 1, open + 1, memo);
        } else if (c == ')') {
            result = dp(s, i + 1, open - 1, memo);
        } else { // c == '*'
            result = dp(s, i + 1, open + 1, memo) || // * as (
                     dp(s, i + 1, open - 1, memo) || // * as )
                     dp(s, i + 1, open, memo);        // * as empty
        }
        
        memo[i][open] = result;
        return result;
    }
}
How It Works
Example: "(*)"
- Two-pass approach:
- Left to right: open goes 1→2→1 (never < 0) ✓
- Right to left: close goes 1→2→1 (never < 0) ✓
- Range tracking:
- '(': min=1, max=1
- '*': min=0, max=2
- ')': min=0, max=1
- Final: min=0 ✓
Example: "())"
- Two-pass approach: Left to right gives open = 1→0→-1 → return false immediately
Key Insights
1.Two-pass greedy works because:
- If string is valid, there exists some way to replace '*' that works
- Left-to-right ensures we never have too many ')'
- Right-to-left ensures we never have too many '('
2.Range tracking is optimal because:
- We only care about possible range of open brackets
- minOpen represents best case (minimize open)
- maxOpen represents worst case (maximize open)
- If maxOpen goes negative, impossible to fix
- Final answer requires minOpen == 0 (can achieve perfect balance)
Time & Space Complexity
- Two-pass greedy: O(n) time, O(1) space
- Range tracking: O(n) time, O(1) space
- DP: O(n²) time, O(n²) space
The range tracking approach is generally preferred as it's most efficient and captures the essence of the problem in a single pass.

What's the time complexity for Native DFS solution ?
The time complexity of this recursive solution is O(3^n) in the worst case, where n is the length of the string.
Why O(3^n)?
Let's analyze the recursion tree:
- For each character in the string, we have different branching factors:
- If character is '(' or ')': 1 recursive call (no branching)
- If character is '*': 3 recursive calls (branching factor of 3)
- In the worst case, the entire string consists of '*' characters
- This creates a recursion tree where each level has up to 3 times as many nodes as the previous level
- With n levels (one for each character), the total number of nodes is 3^n
Visual Example
For string "***" (n=3):
Level 0:                    helper(0, 0)
                           /      |      \
Level 1:           helper(1,1) helper(1,0) helper(1,-1)
                    / | \       / | \        / | \
Level 2:        ... (each branches 3 ways)
                    ...
Level 3:        (8 leaf nodes that check base case)
Total calls ≈ 1 + 3 + 9 + 27 = (3^(n+1) - 1)/2 = O(3^n)
Space Complexity
- O(n) space complexity due to the recursion stack depth
- The maximum depth of recursion is n (the length of the string)
Why the pruning doesn't change the worst-case complexity
The solution does have some pruning:
- if(open < 0 || open > s.length() - index) returns false early
- This prevents some invalid paths from continuing
However, in the worst case (like a string of all '*' characters), many paths remain valid for most of the recursion depth, so we still approach the full 3^n complexity.
Comparison with optimized solutions
This is why the problem has more efficient solutions:
- Range tracking: O(n) time, O(1) space
- Two-pass greedy: O(n) time, O(1) space
- Memoized DP: O(n²) time, O(n²) space
The pure recursive approach without memoization is exponential and will timeout on LeetCode for larger inputs with many '*' characters.

Do we have a stack based solution for this problem ?
Yes, there is a stack-based solution for LeetCode 678! The key insight is to use two stacks to track different types of positions:
- One stack for opening parentheses '('
- One stack for asterisks '*'
Stack-Based Solution
class Solution {
    public boolean checkValidString(String s) {
        Stack<Integer> openStack = new Stack<>();   // positions of '('
        Stack<Integer> starStack = new Stack<>();   // positions of '*'
        
        // First pass: match closing brackets with opening brackets or stars
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                openStack.push(i);
            } else if (c == '*') {
                starStack.push(i);
            } else { // c == ')'
                if (!openStack.isEmpty()) {
                    // Match ')' with '('
                    openStack.pop();
                } else if (!starStack.isEmpty()) {
                    // Match ')' with '*' (treat '*' as '(')
                    starStack.pop();
                } else {
                    // No matching '(' or '*' available
                    return false;
                }
            }
        }
        
        // Second pass: handle remaining unmatched '('
        // We need to match remaining '(' with '*' that appear AFTER the '('
        while (!openStack.isEmpty() && !starStack.isEmpty()) {
            int openPos = openStack.pop();
            int starPos = starStack.pop();
            
            // The '*' must come AFTER the '(' to be used as ')'
            if (starPos < openPos) {
                // This star appears before the open bracket, so it can't close it
                // But we might have other stars, so push back and continue
                starStack.push(starPos);
                break;
            }
            // Successfully matched '(' at openPos with '*' at starPos as ')'
        }
        
        // Valid if all opening brackets are matched
        return openStack.isEmpty();
    }
}
How It Works
Phase 1: Handle Closing Brackets
- When we encounter ')', we try to match it in this priority:
a.First with a real '(' (from openStack)
b.Then with a '*' treated as '(' (from starStack)
c.If neither available → invalid string
Phase 2: Handle Remaining Opening Brackets
- After processing all characters, we might have unmatched '('
- We need to match each remaining '(' with a '*' that appears after it
- This is why we store positions (indices) in the stacks, not just counts
- We pop from both stacks and verify that starPosition > openPosition
Example Walkthrough
String: "(*))"
- i=0: '(' → openStack = [0]
- i=1: '*' → starStack = [1]
- i=2: ')' → match with '(' → openStack = []
- i=3: ')' → match with '*' → starStack = []
- Both stacks empty → valid ✓
String: "(*()"
- i=0: '(' → openStack = [0]
- i=1: '*' → starStack = [1]
- i=2: '(' → openStack = [2, 0]
- i=3: ')' → match with '(' → openStack = [0]
- After loop: openStack = [0], starStack = [1]
- Match remaining: starPos=1 > openPos=0 → valid ✓
String: "*(()"
- After processing: openStack = [2, 1], starStack = [0]
- Try to match: starPos=0 < openPos=2 → cannot match → invalid ✗
Time & Space Complexity
- Time: O(n) - single pass through string, then at most n/2 matching operations
- Space: O(n) - stacks can store up to n elements in worst case
Why Two Stacks?
We need separate stacks because:
- '*' used to match ')' (Phase 1) cannot be reused to match '(' (Phase 2)
- We need to track positions to ensure '*' appears after '(' when used as closing bracket
- The greedy approach of using real '(' first for ')' is optimal
This stack-based solution is intuitive and directly models the bracket matching process while handling the flexibility of asterisks.

You above two stacks solution is wrong based on input below:
Input
s = "(())((())()()(*)(*()(())())())()()((()())((()))(*"
Output = true
Expected = false
You're absolutely right! Let me analyze why my two-stack solution is incorrect and provide the correct stack-based approach.
Looking at the failing test case: "(())((())()()(*)(*()(())())())()()((()())((()))(*"
The issue with my previous solution is in the second phase logic. When I find a star that appears before an opening bracket, I break out of the loop, but I should actually continue checking other stars since there might be valid stars later in the stack.
Let me trace through the problem:
In the second phase, I have remaining opening brackets and stars. I need to ensure that for every opening bracket, there exists a star that comes after it. My previous logic was flawed because I was processing stacks in LIFO order, but I need to match the earliest opening bracket with the earliest possible star that comes after it.
Correct Stack-Based Solution
The key insight is that we should use greedy matching in the second phase:
class Solution {
    public boolean checkValidString(String s) {
        Stack<Integer> openStack = new Stack<>();
        Stack<Integer> starStack = new Stack<>();
        
        // First pass: handle closing brackets
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                openStack.push(i);
            } else if (c == '*') {
                starStack.push(i);
            } else { // c == ')'
                if (!openStack.isEmpty()) {
                    openStack.pop();
                } else if (!starStack.isEmpty()) {
                    starStack.pop();
                } else {
                    return false;
                }
            }
        }
        
        // Second pass: match remaining open brackets with stars
        // We need stars to appear AFTER the open brackets
        while (!openStack.isEmpty() && !starStack.isEmpty()) {
            int openPos = openStack.pop();
            int starPos = starStack.pop();
            
            // Star must come AFTER the open bracket to close it
            if (starPos < openPos) {
                // This star is useless for remaining open brackets
                // But since stacks are LIFO, all remaining stars are even earlier
                return false;
            }
            // Successfully matched open bracket at openPos with star at starPos
        }
        
        return openStack.isEmpty();
    }
}


Refer to
L20.P11.5.Valid Parentheses (Ref.L32)
L22.Generate Parentheses (Ref.L20,L301)
L32.Longest Valid Parentheses (Ref.L20)
L301.Remove Invalid Parentheses (Ref.L22)
L761.Special Binary String (Ref.L678,L2116)
L2116.Check if a Parentheses String Can Be Valid (Ref.L301,L678,L1963)
