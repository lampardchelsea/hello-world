https://leetcode.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/description/
A string is a valid parentheses string (denoted VPS) if and only if it consists of "(" and ")" characters only, and:
- It is the empty string, or
- It can be written as AB (A concatenated with B), where A and B are VPS's, or
- It can be written as (A), where A is a VPS.
We can similarly define the nesting depth depth(S) of any VPS S as follows:
- depth("") = 0
- depth(A + B) = max(depth(A), depth(B)), where A and B are VPS's
- depth("(" + A + ")") = 1 + depth(A), where A is a VPS.
For example,  "", "()()", and "()(()())" are VPS's (with nesting depths 0, 1, and 2), and ")(" and "(()" are not VPS's.
 
Given a VPS seq, split it into two disjoint subsequences A and B, such that A and B are VPS's (and A.length + B.length = seq.length).
Now choose any such A and B such that max(depth(A), depth(B)) is the minimum possible value.
Return an answer array (of length seq.length) that encodes such a choice of A and B:  answer[i] = 0 if seq[i] is part of A, else answer[i] = 1.  Note that even though multiple answers may exist, you may return any of them.
 
Example 1:
Input: seq = "(()())"
Output: [0,1,1,1,1,0]

Example 2:
Input: seq = "()(())()"
Output: [0,0,0,1,1,0,1,1]
 
Constraints:
- 1 <= seq.size <= 10000
--------------------------------------------------------------------------------
Attempt 1: 2026-01-19
Solution 1: Math + Greedy (60 min)
class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int n = seq.length();
        int[] result = new int[n];
        // Tracks current nesting level
        int depth = 0;
        for(int i = 0; i < n; i++) {
            char c = seq.charAt(i);
            if(c == '(') {
                // Assign based on current depth's parity, then go deeper
                result[i] = depth % 2;
                depth++;
            } else {
                // First go up a level, then assign (to match the opening)
                depth--;
                result[i] = depth % 2;
            }
        }
        return result;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to Deepseek
For LeetCode 1111, the key is to split the original valid parentheses string into two subsequences (A and B) to minimize their maximum depth. The optimal approach is a greedy algorithm that alternates parentheses assignments between the two groups based on the current nesting level.
🧠 Core Strategy and Java Solution
The algorithm tracks the current depth (nesting level) as it reads the string. It assigns opening parentheses to groups A or B based on whether the current depth is even or odd. The matching closing parenthesis is assigned to the same group as its opening pair.
Here is the Java implementation:
class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int n = seq.length();
        int[] answer = new int[n];
        int depth = 0; // Tracks current nesting level
        
        for (int i = 0; i < n; i++) {
            char c = seq.charAt(i);
            if (c == '(') {
                // Assign based on current depth's parity, then go deeper
                answer[i] = depth & 1; // depth % 2
                depth++;
            } else { // c == ')'
                // First go up a level, then assign (to match the opening)
                depth--;
                answer[i] = depth & 1; // depth % 2
            }
        }
        return answer;
    }
}
Time Complexity: O(n) - One pass through the string.
Space Complexity: O(n) for the output array, O(1) extra space.
🔍 How It Works: Walkthrough Examples
Let's trace the logic with the example "(()())" to see how the algorithm creates a balanced split.
IndexCharDepth BeforeActionAssign to (Group)Depth After0(0 (even)answer[0] = 0A (0)11(1 (odd)answer[1] = 1B (1)22)2Decrement depth first to 1answer[2] = 1 (odd)B (1)3(1 (odd)answer[3] = 1B (1)24)2Decrement depth first to 1answer[4] = 1 (odd)B (1)5)1Decrement depth first to 0answer[5] = 0 (even)A (0)
Result: [0, 1, 1, 1, 1, 0]
- Group A (0s): Indices 0 and 5 → "()", depth 1.
- Group B (1s): Indices 1, 2, 3, 4 → "()()", depth 1.The maximum depth between A and B is minimized to 1.
💡 Why This Minimizes Maximum Depth
The original string's depth might be concentrated in one deeply nested structure. By alternating the assignment of nesting levels between groups A and B, you effectively distribute the depth.
- Imagine a string like "((()))" with depth 3. If all parentheses were in one group, that group's depth would be 3. The algorithm alternates: the outermost ( (depth 0, even) goes to A, the next (depth 1, odd) to B, the innermost (depth 2, even) back to A, and so on for the closing ones. This splits the original depth, aiming to give each group a depth of about ceil(original_max_depth / 2).
✅ Key Implementation Notes
1.Order of Operations for ')': It's crucial to decrement the depth first before assigning the group for a closing parenthesis. This ensures it matches the group of its corresponding opening parenthesis, which was assigned at the previous depth level.
2.Bitwise Operation: depth & 1 is a fast way to compute depth % 2 to check if a number is even (0) or odd (1).
3.Multiple Valid Answers: The problem may have several correct splits. This greedy method returns one valid, optimal solution.
📊 Comparison with a Simpler But Incorrect Approach
A common mistake is to simply alternate assignments character-by-character, ignoring nesting structure. This fails because matching pairs might end up in different groups, breaking validity.
Incorrect AlternatingCorrect Depth-Based"(()())" → [0, 1, 0, 1, 0, 1]"(()())" → [0, 1, 1, 1, 1, 0]❌ Group A: "()()" (Valid)✅ Group A: "()" (Valid)❌ Group B: "()" (Valid)✅ Group B: "()()" (Valid)Appears okay, but...Always produces two valid VPS.Try "((()))": [0, 1, 0, 1, 0, 1]"((()))": [0, 1, 0, 0, 1, 0]❌ Group B gets ")()" (Invalid!)✅ Both groups are valid.
🧩 Testing Your Solution
You can verify your solution with these cases:
- Input: "()(())()" → One possible output: [0,0,0,1,1,0,1,1]
- Input: "((()))" → One possible output: [0,1,0,0,1,0]
- Edge Case - Flat String: "()()()" (depth 1) → Output could be: [0,0,0,0,0,0] (all in A, B is empty but valid).
I hope this detailed explanation helps you understand both the implementation and the reasoning behind this problem. Would you like to explore how this greedy strategy can be proven to be optimal, or perhaps look at similar problems that involve splitting or balancing sequences?

Refer to QWEN
Here's the optimal Java solution for LeetCode 1111. Maximum Nesting Depth of Two Valid Parentheses Strings:
class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int[] result = new int[seq.length()];
        int depth = 0;
        
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.charAt(i);
            
            if (c == '(') {
                // Assign to group based on current depth parity
                result[i] = depth % 2;
                depth++;
            } else { // c == ')'
                depth--;
                // Assign to same group as its matching opening parenthesis
                result[i] = depth % 2;
            }
        }
        
        return result;
    }
}
Key Concepts Explained:
Problem Understanding:
- Split a valid parentheses string into two disjoint subsequences A and B
- Both A and B must be valid parentheses strings
- Minimize the maximum nesting depth between A and B
- Return an array where 0 = group A, 1 = group B
Core Insight:
The optimal strategy is to alternate assignment based on nesting depth parity:
- Even depth → assign to group 0
- Odd depth → assign to group 1
This ensures that the maximum depth of each group is approximately half of the original depth.
Algorithm Logic:
1.Track current depth as we traverse the string
2.For '(' characters:
- Assign to group based on current depth (before incrementing)
- Then increment depth
3.For ')' characters:
- First decrement depth (to match its opening parenthesis depth)
- Assign to the same group as its matching '('
Example Walkthrough:
Input: "(()())"
IndexCharDepth (before)AssignmentDepth (after)0(00 % 2 = 011(11 % 2 = 122)2→11 % 2 = 113(11 % 2 = 124)2→11 % 2 = 115)1→00 % 2 = 00
Result: [0,1,1,1,1,0]
- Group 0: positions 0,5 → "()" (depth = 1)
- Group 1: positions 1,2,3,4 → "()()" (depth = 1)
- Maximum depth = 1 (original depth was 2)
Why This Works:
- Matching pairs stay together: Each ( and its corresponding ) get the same group assignment
- Balanced distribution: By using depth parity, we ensure roughly equal distribution of nesting levels
- Optimal result: The maximum depth of either group will be ⌈original_depth / 2⌉
Another Example:
Input: "((()))" (original depth = 3)
IndexCharDepthAssignment0(0→101(1→212(2→303)3→204)2→115)1→00
Result: [0,1,0,0,1,0]
- Group 0: "(()())" → depth = 2
- Group 1: "()" → depth = 1
- Maximum depth = 2 = ⌈3/2⌉
Time & Space Complexity:
- Time: O(n) - single pass through the string
- Space: O(1) - only using a few variables (output array doesn't count)
This solution is optimal because it achieves the theoretical minimum possible maximum depth while maintaining validity of both subsequences.



Refer to
L1614.Maximum Nesting Depth of the Parentheses (Ref.L921,L1021,L1111)
