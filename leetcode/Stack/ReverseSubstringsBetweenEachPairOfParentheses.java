/**
 Refer to
 https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/
 Given a string s that consists of lower case English letters and brackets. 
Reverse the strings in each pair of matching parentheses, starting from the innermost one.

Your result should not contain any bracket.
Example 1:
Input: s = "(abcd)"
Output: "dcba"

Example 2:
Input: s = "(u(love)i)"
Output: "iloveu"

Example 3:
Input: s = "(ed(et(oc))el)"
Output: "leetcode"

Example 4:
Input: s = "a(bcdefghijkl(mno)p)q"
Output: "apmnolkjihgfedcbq"

Constraints:
0 <= s.length <= 2000
s only contains lower case English characters and parentheses.
It's guaranteed that all parentheses are balanced.
*/

// Solution 1: DFS
// Refer to
// https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/discuss/382358/Simple-Java-Sol(Recursion)
// https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/discuss/382372/Bad-test-cases-again!/343582
/**
 Same here, bro, i start with two pointers moving opposite, pointer start moving forward, and pointer 
 end moving backward, and i write below code which handle given 3 test cases well, but failed because 
 of similar missing test case as "sxmdll(q)eki(x)", which expected result as "sxmdllqekix", below code 
 failed to handle since it will chop "(q)eki(x)" instead of first "(q)" then "(x)", if leetcode adding 
 "sxmdll(q)eki(x)" in the description, it will avoid misleading to scan as sandwich. 
*/
class Solution {
    public String reverseParentheses(String s) {
        return helper(s, 0, s.length() - 1, 0);
    }
    
    private String helper(String s, int start, int end, int count) {
    	if(s.length() == 0 || start > end) {
    		return "";
    	}
        int initStart = start;
        if(count % 2 == 0) {
            while(start < s.length() && s.charAt(start) != '(') {
                start++;
            }
            while(end >= 0 && s.charAt(end) != ')') {
                end--;
            }        	
        } else if(count % 2 == 1) {
            while(start < s.length() && s.charAt(start) != ')') {
                start++;
            }
            while(end >= 0 && s.charAt(end) != '(') {
                end--;
            }      	
        }
        // We found a pair
        if(start < s.length() && end > -1) {
        	String prefix = s.substring(initStart, start);
            String suffix = s.substring(end + 1);
            String temp = s.substring(start + 1, end);
            StringBuilder sb = new StringBuilder();
            String reversed = sb.append(temp).reverse().toString();
            String mid = helper(reversed, 0, reversed.length() - 1, count + 1);
            return prefix + mid + suffix;
        } else {
        	return s;
        }
    }
}
// Compare to below correct solution, the simple change is two pointers start and end both initialize as 0 and moving forward
class Solution {
    public String reverseParentheses(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        return helper(s);
    }
    
    private String helper(String s) {
        int start = 0;
        int end = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                start = i;
            }
            if(s.charAt(i) == ')') {
                end = i;
                String prefix = s.substring(0, start);
                String suffix = s.substring(end + 1);
                String temp = s.substring(start + 1, end);
                String reversed = new StringBuilder().append(temp).reverse().toString();
                return helper(prefix + reversed + suffix);
            }
        }
        // If no brackets pair anymore directly return current string section
        return s;
    }
}

// Solution 2: Stack + Queue
// Refer to
// https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/discuss/382367/Simple-Stack-and-Queue-Solution
class Solution {
    public String reverseParentheses(String s) {
        Stack<Character> stack = new Stack<Character>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c != ')') {
                stack.push(c);
            } else {
                Queue<Character> q = new LinkedList<Character>();
                while(!stack.isEmpty() && stack.peek() != '(') {
                    q.add(stack.pop());
                }
                // If '(' left, remove it from stack, actually since problem guaranteed that all parentheses are balanced.
                // so no need to check if stack is empty or not, since when we see a ')', there must be a '(' match for it
                // and now it must left on stack, just need to pop it out.
                //if(!stack.isEmpty()) {
                    stack.pop();
                //}
                while(!q.isEmpty()) {
                    stack.push(q.remove());
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }
}

























































https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/description/
You are given a string s that consists of lower case English letters and brackets.
Reverse the strings in each pair of matching parentheses, starting from the innermost one.
Your result should not contain any brackets.
 
Example 1:
Input: s = "(abcd)"
Output: "dcba"

Example 2:
Input: s = "(u(love)i)"
Output: "iloveu"
Explanation: The substring "love" is reversed first, then the whole string is reversed.

Example 3:
Input: s = "(ed(et(oc))el)"
Output: "leetcode"
Explanation: First, we reverse the substring "oc", then "etco", and finally, the whole string.
 
Constraints:
- 1 <= s.length <= 2000
- s only contains lower case English characters and parentheses.
- It is guaranteed that all parentheses are balanced.
--------------------------------------------------------------------------------
Attempt 1: 2026-01-19
Solution 1: Stack (60 min)
Style 1: Stack with LinkedList
class Solution {
    public String reverseParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == ')') {
                // Pop until '(' is found, collect characters
                List<Character> tmp = new LinkedList<>();
                while(!stack.isEmpty() && stack.peek() != '(') {
                    tmp.add(stack.pop());
                }
                // Remove the '('
                stack.pop();
                // Push reversed characters back
                while(!tmp.isEmpty()) {
                    stack.push(tmp.removeFirst());
                }
            } else {
                stack.push(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }
}

Time Complexity: O(n^2) in worst case (nested parentheses causing multiple reversals)
Space Complexity: O(n)
Style 2: Stack with StringBuilder
class Solution {
    public String reverseParentheses(String s) {
        Stack<StringBuilder> stack = new Stack<>();
        StringBuilder curr = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                // Push current string to stack
                stack.push(curr);
                // Start new string for inside parentheses
                curr = new StringBuilder();
            } else if(c == ')') {
                // Reverse current string
                curr.reverse();
                // Prepend with previous string
                StringBuilder prev = stack.pop();
                prev.append(curr);
                curr = prev;
            } else {
                curr.append(c);
            }
        }
        return curr.toString();
    }
}

Time Complexity: O(n^2) in worst case (nested parentheses causing multiple reversals)
Space Complexity: O(n)

Solution 2: DFS (60 min)
class Solution {
    // Shared index across recursive calls
    int index = 0;
    public String reverseParentheses(String s) {
        return helper(s);
    }

    // Classic DFS pattern: base case first, then recursive processing.
    private String helper(String s) {
        // Base case 1: End of string for the current path
        if(index >= s.length()) {
            return "";
        }
        StringBuilder curr = new StringBuilder();
        // Process the string at the current depth
        while(index < s.length()) {
            char c = s.charAt(index);
            // Consume the character
            index++;
            if(c == '(') {
                String inner = helper(s);
                // **Recursive Descent**: Enter a new deeper level.
                // The result is the inner string that needs to be reversed.
                String reverse = new StringBuilder(inner).reverse().toString();
                curr.append(reverse);
            } else if(c == ')') {
                // **Base case 2**: Finished processing the current nested level.
                // Return the accumulated string for this level to the parent call.
                // 'break out' while loop here to use the last statement 
                // 'return currentLevel.toString();' in this method, otherwise we
                // have to write down the exactly same statement here to return result.
                break;
            } else {
                // Regular character, append directly.
                curr.append(c);
            }
        }
        // Return the fully processed string for the current recursive level.
        return curr.toString();
    }
}

Time Complexity: O(n^2) in worst case (nested parentheses causing multiple reversals)
Space Complexity: O(n^2)

Refer to Deepseek
Problem Understanding
Given a string s containing parentheses, reverse the strings in each pair of matching parentheses from the innermost to the outermost, and return the final result.
Solution 1: Stack (Most Common)
class Solution {
    public String reverseParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == ')') {
                // Pop until '(' is found, collect characters
                LinkedList<Character> temp = new LinkedList<>();
                while (!stack.isEmpty() && stack.peek() != '(') {
                    temp.add(stack.pop());
                }
                // Remove the '('
                stack.pop();
                // Push reversed characters back
                while (!temp.isEmpty()) {
                    stack.push(temp.removeFirst());
                }
            } else {
                stack.push(c);
            }
        }
        
        // Build result from stack
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        
        return result.reverse().toString();
    }
}
Solution 2: Stack with StringBuilder (More Efficient)
class Solution {
    public String reverseParentheses(String s) {
        Stack<StringBuilder> stack = new Stack<>();
        StringBuilder current = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                // Push current string to stack
                stack.push(current);
                // Start new string for inside parentheses
                current = new StringBuilder();
            } else if (c == ')') {
                // Reverse current string
                current.reverse();
                // Prepend with previous string
                StringBuilder prev = stack.pop();
                prev.append(current);
                current = prev;
            } else {
                current.append(c);
            }
        }
        
        return current.toString();
    }
}
Solution 3: Two-pointers with Pre-processing (Wormholes)
class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();
        Stack<Integer> stack = new Stack<>();
        int[] pair = new int[n];
        
        // Pre-process: store matching parenthesis indices
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;  // Closing at i matches opening at j
                pair[j] = i;  // Opening at j matches closing at i
            }
        }
        
        // Build result using wormhole technique
        StringBuilder result = new StringBuilder();
        int direction = 1; // 1 = right, -1 = left
        int i = 0;
        
        while (i < n) {
            if (s.charAt(i) == '(' || s.charAt(i) == ')') {
                // Jump to matching parenthesis
                i = pair[i];
                // Change direction
                direction = -direction;
            } else {
                result.append(s.charAt(i));
            }
            i += direction;
        }
        
        return result.toString();
    }
}
Solution 4: Recursive Approach
class Solution {
    private int index = 0;
    
    public String reverseParentheses(String s) {
        return helper(s);
    }
    
    private String helper(String s) {
        StringBuilder sb = new StringBuilder();
        
        while (index < s.length()) {
            char c = s.charAt(index++);
            
            if (c == '(') {
                // Recursively process inside parentheses
                String inner = helper(s);
                sb.append(new StringBuilder(inner).reverse());
            } else if (c == ')') {
                // End of current level
                break;
            } else {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
}
Solution 5: BFS-like Layer Processing
class Solution {
    public String reverseParentheses(String s) {
        char[] arr = s.toCharArray();
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '(') {
                stack.push(i);
            } else if (arr[i] == ')') {
                int start = stack.pop();
                // Reverse substring between start+1 and i-1
                reverse(arr, start + 1, i - 1);
            }
        }
        
        // Build result, skipping parentheses
        StringBuilder result = new StringBuilder();
        for (char c : arr) {
            if (c != '(' && c != ')') {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    private void reverse(char[] arr, int left, int right) {
        while (left < right) {
            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
Solution 6: Deque Approach
class Solution {
    public String reverseParentheses(String s) {
        Deque<Character> deque = new ArrayDeque<>();
        
        for (char c : s.toCharArray()) {
            if (c == ')') {
                // Collect characters until '('
                List<Character> temp = new ArrayList<>();
                while (!deque.isEmpty() && deque.peekLast() != '(') {
                    temp.add(deque.removeLast());
                }
                // Remove the '('
                deque.removeLast();
                // Add reversed characters back
                for (char ch : temp) {
                    deque.addLast(ch);
                }
            } else {
                deque.addLast(c);
            }
        }
        
        // Build result
        StringBuilder result = new StringBuilder();
        while (!deque.isEmpty()) {
            result.append(deque.removeFirst());
        }
        
        return result.toString();
    }
}
Detailed Explanation
Key Insight:
- Process from innermost parentheses outward
- Each time we encounter ')', reverse the substring since the last '('
- Can use stack to track nested structure
Example Walkthrough:
Example 1: "(abcd)"
Solution 1 trace:
Initial: stack = []

c='(' → push '(': ['(']
c='a' → push 'a': ['(', 'a']
c='b' → push 'b': ['(', 'a', 'b']
c='c' → push 'c': ['(', 'a', 'b', 'c']
c='d' → push 'd': ['(', 'a', 'b', 'c', 'd']
c=')' → 
  Pop until '(': temp = ['d', 'c', 'b', 'a']
  Pop '('
  Push reversed: stack = ['a', 'b', 'c', 'd']

Build result: stack = ['a', 'b', 'c', 'd'] → "abcd" (reversed to "abcd")
Example 2: "(u(love)i)"
Initial: "(u(love)i)"

Process:
1. Innermost: "(love)" → "evol"
2. Now string: "(uevoli)"
3. Reverse whole: "iloveu"

Result: "iloveu"
Step-by-step for "(u(love)i)":
Stack trace:
'(', 'u', '(', 'l', 'o', 'v', 'e'  // After pushing "u(love"
')' → reverse "love" to "evol": stack becomes ['(', 'u', 'e', 'v', 'o', 'l']
'i', ')' → reverse "uevoli" to "iloveu": final stack = ['i', 'l', 'o', 'v', 'e', 'u']
Result: "iloveu"
Complexity Analysis
Solution 1 (Stack with LinkedList):
- Time: O(n²) in worst case (nested parentheses causing multiple reversals)
- Space: O(n) for stack
Solution 2 (Stack with StringBuilder):
- Time: O(n²) worst case
- Space: O(n)
Solution 3 (Wormhole technique):
- Time: O(n) - brilliant O(n) solution
- Space: O(n) for pair array
Recommended Solutions
For Interviews: Solution 1
class Solution {
    public String reverseParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == ')') {
                // Collect characters between parentheses
                List<Character> temp = new ArrayList<>();
                while (!stack.isEmpty() && stack.peek() != '(') {
                    temp.add(stack.pop());
                }
                stack.pop(); // Remove '('
                
                // Add back in original order (reversed from temp)
                for (char ch : temp) {
                    stack.push(ch);
                }
            } else {
                stack.push(c);
            }
        }
        
        // Build result
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, stack.pop());
        }
        
        return result.toString();
    }
}
Most Efficient: Solution 3 (O(n) Wormhole)
class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();
        Stack<Integer> stack = new Stack<>();
        int[] pair = new int[n];
        
        // Store matching parenthesis indices
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }
        
        // Build result
        StringBuilder sb = new StringBuilder();
        for (int i = 0, d = 1; i < n; i += d) {
            if (s.charAt(i) == '(' || s.charAt(i) == ')') {
                i = pair[i];  // Jump to matching parenthesis
                d = -d;       // Reverse direction
            } else {
                sb.append(s.charAt(i));
            }
        }
        
        return sb.toString();
    }
}
Visual Example
For "a(bc(def)g)h":
Step 1: Innermost: "(def)" → "fed"
String becomes: "a(bcfedg)h"

Step 2: Next: "(bcfedg)" → "gdefcb"
String becomes: "agdefcbh"

Result: "agdefcbh"
Edge Cases
1.No parentheses: "abc" → "abc"
2.Nested parentheses: "((ab)(cd))" → "badc"
3.Single pair: "(abc)" → "cba"
4.Multiple separate pairs: "(ab)(cd)" → "badc"
5.Empty parentheses: "a()b" → "ab"
6.Deep nesting: "a(b(c)d)e" → "acbde"
Why Solution 3 (Wormhole) is Brilliant
The wormhole technique avoids actual string reversal:
1.Pre-process: Find matching parentheses (like in 856, 921)
2.Traverse with direction changes: When hitting '(' or ')', jump to matching parenthesis and reverse direction
3.Collect characters: Only collect non-parenthesis characters
Example: "(ab(cd)ef)"
Indices: 0 1 2 3 4 5 6 7 8
String:  ( a b ( c d ) e f )
Pairs:   8↔0, 5↔3

Traversal:
i=0, d=1 → '(' → jump to 8 → d=-1
i=7, d=-1 → 'f' → collect
i=6, d=-1 → 'e' → collect
i=5, d=-1 → ')' → jump to 3 → d=1
i=4, d=1 → 'd' → collect
i=5, d=1 → ')' → jump to 3 → d=-1 (but we skip)
i=2, d=-1 → 'b' → collect
i=1, d=-1 → 'a' → collect
i=0, d=-1 → '(' → jump to 8 → d=1 (done)

Collected: "f", "e", "d", "b", "a" → "fedba"
After processing: reverse becomes "abdef"? Wait, let's trace correctly...

Actually: The traversal collects: f, e, d, c, b, a
Result: "fedcba" which reversed is "abcdef"
Correct answer: "(ab(cd)ef)" → reversed gives "fe(dc)ba" → "fedcba"
Alternative: Using StringBuilder and Stack
class Solution {
    public String reverseParentheses(String s) {
        Stack<StringBuilder> stack = new Stack<>();
        stack.push(new StringBuilder());
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                // Start new level
                stack.push(new StringBuilder());
            } else if (c == ')') {
                // Reverse current level and append to previous
                StringBuilder current = stack.pop().reverse();
                stack.peek().append(current);
            } else {
                // Append to current level
                stack.peek().append(c);
            }
        }
        
        return stack.pop().toString();
    }
}
Summary
Key approaches:
1.Stack-based: Simple, intuitive, O(n²) worst case
2.Wormhole technique: Clever O(n) solution using parenthesis pairing
3.Recursive: Natural for nested structures
4.In-place reversal: Modify array in place
For most cases, Solution 1 or 2 is sufficient and easier to understand. For optimal performance, Solution 3's wormhole technique is impressive but requires deeper insight into the problem structure.

Refer to QWEN
Here's the optimal Java solution for LeetCode 1190. Reverse Substrings Between Each Pair of Parentheses using a stack-based approach:
class Solution {
    public String reverseParentheses(String s) {
        Stack<StringBuilder> stack = new Stack<>();
        stack.push(new StringBuilder()); // Initialize with empty string builder
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                // Start a new level - push new StringBuilder for inner content
                stack.push(new StringBuilder());
            } else if (c == ')') {
                // End current level - pop, reverse, and append to parent
                StringBuilder inner = stack.pop();
                inner.reverse(); // Reverse the inner content
                stack.peek().append(inner); // Append to parent level
            } else {
                // Regular character - append to current level
                stack.peek().append(c);
            }
        }
        
        return stack.peek().toString();
    }
}
Alternative: Index-based Simulation (More Efficient)
class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();
        // Precompute matching parentheses pairs
        int[] pair = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }
        
        StringBuilder result = new StringBuilder();
        int index = 0;
        int direction = 1; // 1 = forward, -1 = backward
        
        while (index < n) {
            if (s.charAt(index) == '(' || s.charAt(index) == ')') {
                // Jump to matching parenthesis and reverse direction
                index = pair[index];
                direction = -direction;
            } else {
                // Add character to result
                result.append(s.charAt(index));
            }
            index += direction;
        }
        
        return result.toString();
    }
}
Key Concepts Explained:
Problem Understanding:
- Reverse the string inside each pair of parentheses
- Handle nested parentheses correctly
- Remove all parentheses from the final result
- Process from innermost to outermost parentheses
Stack Approach Logic:
1.Each stack level represents a nesting level of parentheses
2.(: Push new StringBuilder to start collecting inner content
3.): Pop the inner content, reverse it, and append to the parent level
4.Regular characters: Append to current (top) level
Example Walkthrough:
Input: "(abcd)"
CharStack StateAction([""] → ["", ""]Push new levela["", "a"]Append to topb["", "ab"]Append to topc["", "abc"]Append to topd["", "abcd"]Append to top)["dcba"]Pop, reverse, append to parent
Result: "dcba"
Nested Example: "((ab))"
- After processing inner (ab): stack = ["", "ba"]
- After processing outer ): stack = ["ab"]
- Result: "ab"
Index-based Approach Logic:
1.Precompute matching pairs using a stack
2.Simulate traversal with direction changes:
- When hitting ( or ), jump to matching parenthesis and reverse direction
- This effectively reverses the substring between parentheses
3.Collect characters in the order they're visited
Why Two Approaches?
- Stack approach: More intuitive, easier to understand
- Index-based approach: More space-efficient (O(n) vs O(n²) in worst case)
Time & Space Complexity:
Stack Approach:
- Time: O(n²) in worst case (reversing strings at each level)
- Space: O(n²) in worst case (storing substrings at each nesting level)
Index-based Approach:
- Time: O(n) - each character visited once
- Space: O(n) - only for the pair array and result
Edge Cases Handled:
- No parentheses: "abc" → "abc"
- Single pair: "(abc)" → "cba"
- Nested pairs: "((ab))" → "ab"
- Multiple separate pairs: "(ab)(cd)" → "bacd"
The stack approach is recommended for interviews due to its clarity, while the index-based approach is optimal for performance-critical scenarios.

Q1: You have list out a DFS solution, but it not match what i expected, usually in recursion method body we start with base condition (terminate condition), but in your DFS solution below i could not find this pattern, could you write a classic native DFS solution pattern for this problem ?
You're absolutely right to point out the difference in structure. The provided solution uses a stateful recursion with a global index, which processes characters in a loop within each recursive call. A more "classic" DFS pattern would use a pure recursive function that processes one logical unit per call, with a clear base case at the start.
Here is a revised solution that follows that classic pattern, treating the string as a sequence of tokens and leveraging a shared index but with a more typical DFS structure.
class Solution {
    private int idx = 0; // Shared index across recursive calls

    public String reverseParentheses(String s) {
        return dfs(s);
    }

    // Classic DFS pattern: base case first, then recursive processing.
    private String dfs(String s) {
        // Base case 1: End of string for the current path
        if (idx >= s.length()) {
            return "";
        }

        StringBuilder currentLevel = new StringBuilder();

        // Process the string at the current depth
        while (idx < s.length()) {
            char currChar = s.charAt(idx);
            idx++; // Consume the character

            if (currChar == '(') {
                // **Recursive Descent**: Enter a new deeper level.
                // The result is the inner string that needs to be reversed.
                String innerString = dfs(s);
                StringBuilder reversedInner = new StringBuilder(innerString).reverse();
                currentLevel.append(reversedInner);
            } else if (currChar == ')') {
                // **Base case 2**: Finished processing the current nested level.
                // Return the accumulated string for this level to the parent call.
                // 'break out' while loop here to use the last statement 
                // 'return currentLevel.toString();' in this method, otherwise we
                // have to write down the exactly same statement here to return result.
                break;
            } else {
                // Regular character, append directly.
                currentLevel.append(currChar);
            }
        }
        // Return the fully processed string for the current recursive level.
        return currentLevel.toString();
    }
}
🧱 Classic DFS Pattern Breakdown
This solution adheres more closely to the classic recursive DFS pattern you expected:
1.Explicit Base Cases at Method Start:
- if (idx >= s.length()) return "";: Handles the end of the string for the current recursive branch.
- The else if (currChar == ')') block also acts as a base case that terminates the current level's processing and returns control to the previous level (break).
2.Recursive Descent on Encountering New State:
- The call String innerString = dfs(s); is made when encountering '(', representing a descent into a deeper nested level (similar to visiting a child node in a tree).
3.Process and Return Results:
- The result from the deeper level (innerString) is processed (reversed) and incorporated into the current level's result.
- The method returns the fully built string for its own level.
🔍 Key Differences from the Original Pattern
AspectOriginal "Loop within Recursion" Pattern"Classic DFS" PatternFlow ControlA while loop inside the function drives progression. The function call represents a level, and the loop processes multiple items at that level.Each character/decision point can lead to a new recursive call. The call stack progression directly mirrors the depth of nesting.Base Case RoleThe break on ')' exits the loop for the current level. The while condition handles termination.The base case is checked first thing in the method. Returning from a ')' is a clear exit from the current recursion frame.Conceptual ModelIt's like using recursion to implement a stack-based parser, where each recursive call handles a segment.It's more akin to a depth-first tree traversal, where each '(' opens a new child subtree.
💡 Why the Original Pattern Was Used
The original pattern is also valid and is often efficient for this type of linear string parsing with nesting. The while loop cleanly handles sequences of characters at the same depth (e.g., "ab(cd)ef"), making the code concise. It blends iterative and recursive styles.
For another excellent example of the "classic DFS" pattern applied to a very similar problem, I highly recommend studying LeetCode 394. Decode String. Its structure—where a recursive call is triggered by '[' and returns at ']' to process a multiplier—is a perfect parallel and will help solidify this pattern.
Would you like to see a side-by-side walkthrough of how both patterns execute for a specific string like "(ab(cd)ef)"?
Let's analyze the time and space complexity of your "classic DFS" solution.
Time Complexity Analysis
Worst Case: O(n²)
Why O(n²) and not O(n)?
The key issue is the .reverse() operation inside the recursion. Let's break it down:
1.String reversal cost: new StringBuilder(innerString).reverse() takes O(k) time where k is the length of the string being reversed.
2.Worst-case scenario: Deeply nested parentheses where each level contains most of the string.
Example: "a(b(c(d(e))))"
- Process innermost: "e" → reverse (O(1))
- Next level: "de" → reverse O(2)
- Next level: "cde" → reverse O(3)
- Next level: "bcde" → reverse O(4)
- Outer level: "abcde" → reverse O(5)
Total work: 1 + 2 + 3 + 4 + 5 = O(n²) where n = 5
Mathematical analysis:
- Each character gets reversed multiple times as it bubbles up through recursion levels
- In worst-case nested structure, a character at position i gets reversed O(n-i) times
- Total: Σ(i=1 to n) i = O(n²)
Space Complexity Analysis
O(n²) in worst case
Why O(n²) and not O(n)? Here's the breakdown:
1.Recursion stack: O(n) in worst case (deeply nested like "(((...)))")
- Each recursive call for '(' adds a stack frame
- Maximum depth = number of nested parentheses
2.StringBuilder memory: This is the bigger issue
- Each recursive call creates its own StringBuilder (currentLevel)
- Each level builds a string containing characters from deeper levels
- Multiple string copies exist simultaneously during recursion
Memory usage example for "a(b(c(d(e))))":
dfs Level 0: StringBuilder with "abcde" (5 chars)
dfs Level 1: StringBuilder with "bcde" (4 chars)  
dfs Level 2: StringBuilder with "cde" (3 chars)
dfs Level 3: StringBuilder with "de" (2 chars)
dfs Level 4: StringBuilder with "e" (1 char)

Total characters stored simultaneously: 5+4+3+2+1 = O(n²)
1.String reversal overhead: .reverse() creates new StringBuilder objects
- Each .reverse() creates a copy of the string
- These temporary objects exist during the reversal
Visualization of the Problem
Consider input: "((((abc))))"
Recursive calls:
Level 4: reverse("abc") → "cba" (copy 3 chars)
Level 3: reverse("cba") → "abc" (copy 3 chars)  
Level 2: reverse("abc") → "cba" (copy 3 chars)
Level 1: reverse("cba") → "abc" (copy 3 chars)
Level 0: append "abc"

Total character operations: 3 × 4 = 12 (n×(depth-1))
Total memory: Multiple copies of same string at different levels
Comparison with Other Solutions
Solution TypeTime ComplexitySpace ComplexityKey IssueThis DFS SolutionO(n²)O(n²)Repeated string reversal & copyingStack SolutionO(n²)O(n)Still reverses strings but more efficientWormhole O(n) SolutionO(n)O(n)No actual string reversal
Optimization Opportunities
1. Avoid Repeated Reversal
The main inefficiency is reversing the same substring multiple times. You could:
- Pass a "shouldReverse" flag down the recursion
- Build strings normally, then reverse at the very end if needed
- This reduces to O(n) time, O(n) space
2. Use Character Array
Instead of building many StringBuilder objects:
- Process in-place on a character array
- Reverse subarrays directly
- Still O(n²) time but O(n) space
3. Memoization (Not helpful here)
- Can't memoize because result depends on position/index
- Global idx prevents reusing computations
Revised Complexity Summary
For balanced parentheses with maximum depth d:
- Time: O(n × d) where d = maximum nesting depth
- In worst case d = n/2 → O(n²)
- In average case d is small → closer to O(n)
- Space: O(n × d) for strings + O(d) for recursion stack
Example cases:
- "(abc)(def)": Time O(n), Space O(n) (shallow nesting)
- "((((abc))))": Time O(n²), Space O(n²) (deep nesting)
- "a(b(c)d(e)f)": Time O(n²), Space O(n²) (mixed nesting)
Bottom Line
Your classic DFS solution has quadratic time and space complexity in worst-case scenarios due to:
1.Repeated string reversal operations
2.Multiple string copies existing simultaneously in the recursion stack
3.Character-level manipulation for each reversal
This is why the "wormhole" O(n) solution is preferred for optimal performance, though your DFS solution is more intuitive and easier to understand for moderate input sizes.


Refer to
L394.Decode String
L856.Score of Parentheses (Ref.L678,L761,L2116)
L1021.Remove Outermost Parentheses (Ref.L678,L761,L2116)
L1111.Maximum Nesting Depth of Two Valid Parentheses Strings (Ref.L1614)
L1249.Minimum Remove to Make Valid Parentheses (Ref.L921)
