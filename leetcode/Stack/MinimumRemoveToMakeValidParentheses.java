
https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/description/
Given a string s of '(' , ')' and lowercase English characters.
Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any valid string.
Formally, a parentheses string is valid if and only if:
- It is the empty string, contains only lowercase characters, or
- It can be written as AB (A concatenated with B), where A and B are valid strings, or
- It can be written as (A), where A is a valid string.
 
Example 1:
Input: s = "lee(t(c)o)de)"
Output: "lee(t(c)o)de"
Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.

Example 2:
Input: s = "a)b(c)d"
Output: "ab(c)d"

Example 3:
Input: s = "))(("
Output: ""
Explanation: An empty string is also valid.
 
Constraints:
- 1 <= s.length <= 10^5
- s[i] is either'(' , ')', or lowercase English letter
--------------------------------------------------------------------------------
Attempt 1: 2023-08-12
Solution 1: Two Stacks (360min, TLE, 62/62)
Key points:
1. Paired opening bracket '(' will convert into '#', unpaired opening bracket '(' will ignore during final string builder
2. 'tmp' stack temporarily used to cache the substring before current closing bracket ')' till its corresponding opening bracket '(' or if no corresponding opening bracket '(' then cache till the beginning of original input string
3. Two stacks 'tmp' and 'stack' cooperate to go back and forth to construct "filtered" string by constructing session by session based on encountering closing bracket ')'
4. In final scanning "filtered" string still need to handle redundant opening bracket '('  (e.g "))((", "ab(c(d", "ab(c(d)"...)
class Solution {
    public String minRemoveToMakeValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        Stack<Character> tmp = new Stack<Character>();
        for(char c : s.toCharArray()) {
            if(c != ')') {
                stack.push(c);
            } else {
                while(!stack.isEmpty()) {
                    if(stack.peek() != '(') {
                        // Store all english letters in tmp stack
                        // including '#' which means updated previous
                        // used '(' and previous ')'
                        tmp.push(stack.pop());
                    } else {
                        // Find previous not used corresponding '(' from
                        // stack update it from '(' to '#' for later
                        // identify it is a already used one
                        stack.pop();
                        stack.push('#');
                        break;
                    }
                }
                // If after removing all elements from stack and not able
                // to find previous not used corresponding '(', then no need
                // to push ')' onto stack, since we have to remove all
                // non-pairable ')' also, only need to restore stack from
                // tmp stack, if not empty means we find one '(' to build
                // a pair, then need to restore stack and also push ')'
                if(stack.isEmpty()) {
                    while(!tmp.isEmpty()) {
                        stack.push(tmp.pop());
                    }
                } else {
                    while(!tmp.isEmpty()) {
                        stack.push(tmp.pop());
                    }
                    stack.push(c); // equal to stack.push(')')     
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            char c = stack.pop();
            // Skip all unpaired '(', e.g "))((", expected "" not "(("
            if(c != '(') {
                if(c == '#') {
                    sb.insert(0, '(');
                } else {
                    sb.insert(0, c);
                }
            }
        }
        return sb.toString();
    }
}

Time Complexity: O(N^2)
Space Complexity: O(N)

--------------------------------------------------------------------------------
Solution 2: Stack + boolean[] array (30min)
class Solution {
    public String minRemoveToMakeValid(String s) {
        boolean[] b = new boolean[s.length()];
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(i);
            } else if(c == ')') {
                if(!stack.isEmpty()) {
                    b[i] = true;
                    b[(int)stack.pop()] = true;
                }
            } else {
                b[i] = true;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            if(b[i]) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}

Time Complexity: O(N) 
Space Complexity: O(N)

Refer to
https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/solutions/419402/java-c-stack/
Intuition
To make the string valid with minimum removals, we need to get rid of all parentheses that do not have a matching pair.
1.Push char index into the stack when we see '('.
2.Pop from the stack when we see ')'.
- If the stack is empty, then we have ')' without the pair, and it needs to be removed.
3.In the end, the stack will contain indexes of '(' without the pair, if any. We need to remove all of them too
If you are using a stack, you can maintain a simple boolean array to decide whether to take a character into result or not.
https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/solutions/419402/java-c-stack/comments/413619
class Solution {
    public String minRemoveToMakeValid(String s) {
        int len = s.length();
        boolean[] b = new boolean[s.length()];
        StringBuilder res = new StringBuilder("");
        Stack<Integer> st = new Stack<Integer>();
        for(int i=0;i<len;++i){
            if(s.charAt(i) == '(') st.push(i);
            else if(s.charAt(i) == ')'){
                if(!st.isEmpty()){
                   // match these pairs, all unmatched are false anyway
                    b[i] = true;
                    b[(int)st.pop()] = true; 
                }
            }else{
                b[i] = true; // any character other than ( and ) are true anyway
            }
        }               
        
        for(int i=0;i<len;++i){
            if(b[i]) res.append(s.charAt(i));
        }   
        
        return res.toString();
    }
}

--------------------------------------------------------------------------------
Solution 3: Stack to record all opening bracket '(' index + Set to record redundant closing bracket ')' index  (30min)
class Solution {
    public String minRemoveToMakeValid(String s) {
        Set<Integer> set = new HashSet<Integer>();
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(i);
            } else if(c == ')') {
                if(!stack.isEmpty()) {
                    // Find valid '(' on stack to build "()"
                    // with current ')', pop out '(', then
                    // index for '(' removed from stack 
                    stack.pop();
                } else {
                    // Since no valid '(' on stack, ignore ')'
                    // and store ')' index in set
                    set.add(i);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        // Why we need to reversely scan the array ?
        // Because stack store redundant '(' indexes reversely,
        // if scan from 0 to n - 1, stack.peek() will always not
        // able to match i, because order reversed
        // Test out by: "))(("
        for(int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if(c == '(') {
                if(!stack.isEmpty() && stack.peek() == i) {
                    // It's a redundant '(', remove it
                    stack.pop();
                } else {
                    // It's not a redundant '(' since its index not 
                    // stored on stack, then add to final string
                    sb.insert(0, '(');
                }
            } else if (c == ')') {
                // If not a redundant ')' then add to final string
                if(!set.contains(i)) {
                    sb.insert(0, ')');
                }
            } else {
                sb.insert(0, c);
            }
        }
        return sb.toString();
    }
}

Time Complexity: O(N) 
Space Complexity: O(N)

--------------------------------------------------------------------------------
Solution 4: Stack without Set (30min)
Based on Solution 3: we can remove Set
class Solution {
    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = new StringBuilder(s);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(i);
            } else if(c == ')') {
                if(!stack.isEmpty()) {
                    // Find valid '(' on stack to build "()"
                    // with current ')', pop out '(', then
                    // index for '(' removed from stack 
                    stack.pop();
                } else {
                    // Since no valid '(' on stack, ignore ')'
                    // use StringBuilder directly, mask as '*'
                    sb.setCharAt(i, '*');
                }
            }
        }
        // Update all recorded redundant '(' and mask same as '*'
        while(!stack.isEmpty()) {
            sb.setCharAt(stack.pop(), '*');
        }
        // Remove all '*' placeholder and left result string
        return sb.toString().replaceAll("\\*", "");
    }
}

Time Complexity: O(N) 
Space Complexity: O(N)

Refer to
https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/solutions/419402/java-c-stack/
Approach 1: Stack and Placeholder
We mark removed parentheses with '*', and erase all of them in the end.

Java
public String minRemoveToMakeValid(String s) {
  StringBuilder sb = new StringBuilder(s);
  Stack<Integer> st = new Stack<>();
  for (int i = 0; i < sb.length(); ++i) {
    if (sb.charAt(i) == '(') st.add(i);
    if (sb.charAt(i) == ')') {
      if (!st.empty()) st.pop();
      else sb.setCharAt(i, '*');
    }
  }
  while (!st.empty())
    sb.setCharAt(st.pop(), '*');
  return sb.toString().replaceAll("\\*", "");
}

Refer to Deepseek
Problem Understanding
Given a string with parentheses and other characters, remove the minimum number of parentheses to make the string valid. Return any valid result.
Solution 1: Two-pass with Stack (Most Common)
class Solution {
    public String minRemoveToMakeValid(String s) {
        // First pass: mark invalid parentheses
        Stack<Integer> stack = new Stack<>();
        boolean[] invalid = new boolean[s.length()];
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i); // Store index of '('
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    // No matching '(' for this ')'
                    invalid[i] = true;
                } else {
                    stack.pop(); // Valid pair
                }
            }
        }
        
        // Mark remaining '(' in stack as invalid
        while (!stack.isEmpty()) {
            invalid[stack.pop()] = true;
        }
        
        // Second pass: build result string
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!invalid[i]) {
                result.append(s.charAt(i));
            }
        }
        
        return result.toString();
    }
}
Solution 2: Two-pass without Stack (Balance Counting)
class Solution {
    public String minRemoveToMakeValid(String s) {
        // First pass: remove extra ')'
        StringBuilder sb = new StringBuilder();
        int openCount = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                openCount++;
                sb.append(c);
            } else if (c == ')') {
                if (openCount > 0) {
                    openCount--;
                    sb.append(c);
                }
                // Else: skip this ')'
            } else {
                sb.append(c);
            }
        }
        
        // Second pass: remove extra '(' from the end
        StringBuilder result = new StringBuilder();
        int closeCount = 0;
        
        for (int i = sb.length() - 1; i >= 0; i--) {
            char c = sb.charAt(i);
            if (c == ')') {
                closeCount++;
                result.append(c);
            } else if (c == '(') {
                if (closeCount > 0) {
                    closeCount--;
                    result.append(c);
                }
                // Else: skip this '('
            } else {
                result.append(c);
            }
        }
        
        return result.reverse().toString();
    }
}
Solution 3: One-pass with Two Builders
class Solution {
    public String minRemoveToMakeValid(String s) {
        // Parse from left to right
        StringBuilder leftToRight = new StringBuilder();
        int openSeen = 0;
        int balance = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                openSeen++;
                balance++;
                leftToRight.append(c);
            } else if (c == ')') {
                if (balance > 0) {
                    balance--;
                    leftToRight.append(c);
                }
            } else {
                leftToRight.append(c);
            }
        }
        
        // Parse from right to left
        StringBuilder result = new StringBuilder();
        int openToKeep = openSeen - balance; // Only keep balanced '('
        
        for (int i = leftToRight.length() - 1; i >= 0; i--) {
            char c = leftToRight.charAt(i);
            if (c == '(') {
                if (openToKeep > 0) {
                    openToKeep--;
                    result.append(c);
                }
            } else {
                result.append(c);
            }
        }
        
        return result.reverse().toString();
    }
}
Solution 4: Using Set for Invalid Indices
class Solution {
    public String minRemoveToMakeValid(String s) {
        Set<Integer> invalid = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        
        // Identify invalid parentheses
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                if (stack.isEmpty()) {
                    invalid.add(i);
                } else {
                    stack.pop();
                }
            }
        }
        
        // Any '(' left in stack are invalid
        while (!stack.isEmpty()) {
            invalid.add(stack.pop());
        }
        
        // Build result
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!invalid.contains(i)) {
                sb.append(s.charAt(i));
            }
        }
        
        return sb.toString();
    }
}
Solution 5: Optimized with Char Array
class Solution {
    public String minRemoveToMakeValid(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        
        // Pass 1: remove invalid ')'
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == '(') {
                count++;
            } else if (arr[i] == ')') {
                if (count == 0) {
                    arr[i] = '#'; // Mark for removal
                } else {
                    count--;
                }
            }
        }
        
        // Pass 2: remove invalid '(' from the end
        count = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] == ')') {
                count++;
            } else if (arr[i] == '(') {
                if (count == 0) {
                    arr[i] = '#'; // Mark for removal
                } else {
                    count--;
                }
            }
        }
        
        // Build result
        StringBuilder result = new StringBuilder();
        for (char c : arr) {
            if (c != '#') {
                result.append(c);
            }
        }
        
        return result.toString();
    }
}
Solution 6: Minimal Memory (Two-pass In-place)
class Solution {
    public String minRemoveToMakeValid(String s) {
        char[] chars = s.toCharArray();
        int write = 0;
        int balance = 0;
        
        // First pass: remove extra ')'
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                balance++;
                chars[write++] = chars[i];
            } else if (chars[i] == ')') {
                if (balance > 0) {
                    balance--;
                    chars[write++] = chars[i];
                }
                // else skip this ')'
            } else {
                chars[write++] = chars[i];
            }
        }
        
        // Second pass: remove extra '(' from the end
        int newWrite = 0;
        int closeNeeded = 0;
        
        for (int i = write - 1; i >= 0; i--) {
            if (chars[i] == ')') {
                closeNeeded++;
                chars[write - 1 - newWrite++] = chars[i];
            } else if (chars[i] == '(') {
                if (closeNeeded > 0) {
                    closeNeeded--;
                    chars[write - 1 - newWrite++] = chars[i];
                }
                // else skip this '('
            } else {
                chars[write - 1 - newWrite++] = chars[i];
            }
        }
        
        return new String(chars, write - newWrite, newWrite);
    }
}
Detailed Explanation
Key Insight:
- Track balance of parentheses: +1 for '(', -1 for ')'
- Balance should never go negative (would mean extra ')')
- At the end, balance should be 0 (no extra '(')
Example Walkthrough:
Example: "a)b(c)d"
Solution 1 (Stack) trace:
Step 1: Mark invalid parentheses
  i=0: 'a' → skip
  i=1: ')' → stack empty → mark invalid[1] = true
  i=2: 'b' → skip
  i=3: '(' → push 3
  i=4: 'c' → skip
  i=5: ')' → stack not empty → pop 3
  i=6: 'd' → skip
  
Step 2: Stack empty → no remaining '('

Step 3: Build result skipping index 1
  Result: "ab(c)d"
Solution 2 (Two-pass balance) trace:
First pass (remove extra ')'):
  "a)b(c)d" → balance never negative
  Skip first ')' → "ab(c)d"
  openCount = 1

Second pass (reverse, remove extra '('):
  Reverse: "d)c(ba"
  Process: keep all, closeCount tracks ')'
  Reverse back: "ab(c)d"
Complexity Analysis
Solution 1 (Stack with boolean array):
- Time: O(n) - two passes
- Space: O(n) - stack + boolean array
Solution 2 (Two-pass without stack):
- Time: O(n) - two passes
- Space: O(n) - StringBuilder
Solution 5 (Char array marking):
- Time: O(n) - three passes (two marking, one building)
- Space: O(n) - char array
Recommended Solutions
For Interviews: Solution 1
class Solution {
    public String minRemoveToMakeValid(String s) {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> toRemove = new HashSet<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    toRemove.add(i);
                } else {
                    stack.pop();
                }
            }
        }
        
        // Add remaining '(' to remove set
        while (!stack.isEmpty()) {
            toRemove.add(stack.pop());
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!toRemove.contains(i)) {
                sb.append(s.charAt(i));
            }
        }
        
        return sb.toString();
    }
}
Most Efficient: Solution 5
class Solution {
    public String minRemoveToMakeValid(String s) {
        char[] arr = s.toCharArray();
        
        // Remove invalid ')'
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '(') count++;
            else if (arr[i] == ')') {
                if (count == 0) arr[i] = '#';
                else count--;
            }
        }
        
        // Remove invalid '('
        count = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == ')') count++;
            else if (arr[i] == '(') {
                if (count == 0) arr[i] = '#';
                else count--;
            }
        }
        
        // Build result
        StringBuilder sb = new StringBuilder();
        for (char c : arr) {
            if (c != '#') sb.append(c);
        }
        return sb.toString();
    }
}
Visual Example
Input: "lee(t(c)o)de)"
Process with Solution 1:
Index:  0 1 2 3 4 5 6 7 8 9 10 11 12 13
String: l e e ( t ( c ) o ) d e  )  ?

Step 1 - Stack trace:
  '(' at 3 → push 3
  '(' at 5 → push 5
  ')' at 7 → pop 5
  ')' at 9 → pop 3
  ')' at 12 → stack empty → mark invalid[12] = true

Step 2 - Build result (skip index 12):
  Result: "lee(t(c)o)de"
Edge Cases
1.All valid: "(a(b)c)" → "(a(b)c)"
2.Extra closing: "a)b(c)d" → "ab(c)d"
3.Extra opening: "(a(b(c)d)" → "(a(bc)d)" or "a(b(c)d)"
4.Nested with extra: "))((" → ""
5.No parentheses: "abc" → "abc"
6.Mixed: "a(b))c(d)e)" → "a(b)c(d)e"
Comparison with LeetCode 921
LeetCode 921 (Minimum Add):
- We add parentheses to make valid
- Solution: track balance, add when needed
LeetCode 1249 (Minimum Remove):
- We remove parentheses to make valid
- Solution: track invalid indices, remove them
Why Two Passes?
We need two passes because:
1.Left-to-right: Removes extra ')' (when balance would go negative)
2.Right-to-left: Removes extra '(' (remaining after first pass)
Example "(()":
- First pass: keeps all (no negative balance)
- Second pass (reverse): removes extra '('
Optimization Tips
1.Use char array for in-place modification
2.Mark for removal with special character instead of storing indices
3.Process right-to-left to remove extra '(' efficiently
4.Count rather than stack when possible for O(1) space
Summary
The optimal approach:
1.Identify invalid parentheses using stack or balance counting
2.Build result skipping invalid indices
3.Return any valid result (multiple valid answers possible)
Time: O(n), Space: O(n) for most readable solutions, can be optimized to O(1) extra space with clever in-place modifications.


Refer to
L921.Minimum Add to Make Parentheses Valid (Ref.L1249,L1963)
