https://leetcode.ca/2017-05-19-536-Construct-Binary-Tree-from-String/
https://www.lintcode.com/problem/880/description
You need to construct a binary tree from a string consisting of parenthesis and integers.
The whole input represents a binary tree. It contains an integer followed by zero, one or two pairs of parenthesis. The integer represents the root’s value and a pair of parenthesis contains a child binary tree with the same structure.
You always start to construct the left child node of the parent first if it exists.
Example 1:
Input: "-4(2(3)(1))(6(5))"
Output: {-4,2,6,3,1,5}
Explanation: 
Return the tree root node representing the following tree:

      -4
     /   \
    2     6
   / \   /
  3   1 5

Example 2:
Input: "1(-1)"
Output: {1,-1}
Explanation:
The output is look like this:
      1
    /
  -1

Note:
1.There will only be '(', ')', '-' and '0' ~ '9' in the input string.
2.An empty tree is represented by "" instead of "()".
--------------------------------------------------------------------------------
Attempt 1: 2026-01-10
Solution 1: DFS (120 min)
The working pattern on constructing tree problems can refer to L297.Serialize and Deserialize Binary Tree (Ref.L449,L536), L449.Serialize and Deserialize BST (Ref.L297,L536):
1st which return type for recursion call (e.g usually return type as TreeNode)
2nd is using which traversal order to build the tree (e.g usually pre-order, first build the root node, then build the left and right child node)
3rd find the terminate condition as base case for recursion call (e.g scanning the string till end)
4th is hardest part as how to retrieve the node value, and also find boundray condition to split root and child node (e.g encounter split symbol like '(' or ')' bracket), and check if we need global variable to support reversal
Style 1: Global variable
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class Solution {
    /**
     * @param s: a string
     * @return: a root of this tree
     */
    // Use a global variable to support index tracking through recursion
    int index = 0;
    public TreeNode str2tree(String s) {
        return helper(s);
    }

    // 1st which return type for recursion call (e.g usually return type as TreeNode)
    private TreeNode helper(String s) {
        // 3rd find the terminate condition as base case for recursion call (e.g scanning the string till end)
        if(index >= s.length()) {
            return null;
        }

        // 4th is hardest part as how to retrieve the node value, and also find boundray 
        // condition to split root and child node (e.g encounter split symbol like '(' 
        // or ')' bracket), and check if we need global variable to support reversal

        // Build the node value
        StringBuilder sb = new StringBuilder();
        while(index < s.length() && (Character.isDigit(s.charAt(index)) || s.charAt(index) == '-')) {
            sb.append(s.charAt(index));
            index++;
        }

        // 2nd is using which traversal order to build the tree (e.g usually pre-order, 
        // first build the root node, then build the left and right child node)

        // Build the root node first
        TreeNode root = new TreeNode(Integer.parseInt(sb.toString()));
        // Build the left child node second
        if(index < s.length() && s.charAt(index) == '(') {
            index++; // Skip '('
            root.left = helper(s);
            index++; // Skip ')'
        }
        // Build the right child node third
        if(index < s.length() && s.charAt(index) == '(') {
            index++; // Skip '('
            root.right = helper(s);
            index++; // Skip ')'
        }
        return root;
    }
}

Time Complexity: O(n)
Space Complexity: O(h)
The algorithm runs in O(n) time and O(h) space (recursion stack), where n is string length and h is tree height

Style 2: No global variable but array object to pass through recursion
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class Solution {
    /**
     * @param s: a string
     * @return: a root of this tree
     */
    public TreeNode str2tree(String s) {
        int[] index = {0}; // mutable index to track current parsing position
        return helper(s, index);
    }

    // 1st which return type for recursion call (e.g usually return type as TreeNode)
    private TreeNode helper(String s, int[] index) {
        // 3rd find the terminate condition as base case for recursion call (e.g scanning the string till end)
        if(index[0] >= s.length()) {
            return null;
        }

        // 4th is hardest part as how to retrieve the node value, and also find boundray 
        // condition to split root and child node (e.g encounter split symbol like '(' 
        // or ')' bracket), and check if we need global variable to support reversal

        // Build the node value
        StringBuilder sb = new StringBuilder();
        while(index[0] < s.length() && (Character.isDigit(s.charAt(index[0])) || s.charAt(index[0]) == '-')) {
            sb.append(s.charAt(index[0]));
            index[0]++;
        }

        // 2nd is using which traversal order to build the tree (e.g usually pre-order, 
        // first build the root node, then build the left and right child node)

        // Build the root node first
        TreeNode root = new TreeNode(Integer.parseInt(sb.toString()));
        // Build the left child node second
        if(index[0] < s.length() && s.charAt(index[0]) == '(') {
            index[0]++; // Skip '('
            root.left = helper(s, index);
            index[0]++; // Skip ')'
        }
        // Build the right child node third
        if(index[0] < s.length() && s.charAt(index[0]) == '(') {
            index[0]++; // Skip '('
            root.right = helper(s, index);
            index[0]++; // Skip ')'
        }
        return root;
    }
}

Time Complexity: O(n)
Space Complexity: O(h)
The algorithm runs in O(n) time and O(h) space (recursion stack), where n is string length and h is tree height

Solution 2: Divide and Conquer (120 min, similar to L761.Special Binary String (Ref.L678,L2116))
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class Solution {
    /**
     * @param s: a string
     * @return: a root of this tree
     */
    public TreeNode str2tree(String s) {
        return helper(s);
    }

    private TreeNode helper(String s) {
        if(s == null || s.isEmpty()) {
            return null;
        }
        int firstOpenBracketIndex = s.indexOf('(');
        // No open bracket imply no close bracket as well since
        // in definition it imply the pair of brackets should
        // happen at the same time and cannot happen as single
        // one: The integer represents the root's value and a 
        // pair of parenthesis contains a child binary tree with 
        // the same structure.
        // Handle the single pure number node
        if(firstOpenBracketIndex == -1) {
            return new TreeNode(Integer.parseInt(s));
        }
        // Parse root node
        TreeNode root = new TreeNode(Integer.parseInt(s.substring(0, firstOpenBracketIndex)));
        // Find the matching closing parenthesis for left child
        int start = firstOpenBracketIndex;
        int openBracketCount = 0;
        for(int i = start; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                openBracketCount++;
            } else if(s.charAt(i) == ')') {
                openBracketCount--;
            }
            // Found the matching closing parenthesis for left child
            if(openBracketCount == 0) {
                // Parse left child node
                String leftTree = s.substring(start + 1, i);
                if(!leftTree.isEmpty()) {
                    root.left = helper(leftTree);
                }
                // Check if there's a right child
                if(i + 1 < s.length() && s.charAt(i + 1) == '(') {
                    // Parse right child node
                    String rightTree = s.substring(i + 2, s.length() - 1);
                    if(!rightTree.isEmpty()) {
                        root.right = helper(rightTree);
                    }
                }
                // Critical statement, without it if multiple 'openBracketCount = 0'
                // condition exist, the for loop will continue during certain recursion
                // level (no need at initial level, may happen when it go till child
                // subtree level, e.g if input as "4(2(3)(1))(6(5))", without 'break',
                // the issue will happen at 2nd level as during process the "2(3)(1)", 
                // the logic will first go into 3rd level to process "(3)" and since 
                // "(1)" at the same level, it will continue on "(1)" as right subtree 
                // as well, after procesing "(1)" it will come back to 2nd level, and 
                // index still hold on first ')' as index = 3, since no 'break', based 
                // on for loop it will continue with remain substring(index = 4 to 6 as 
                // "(1)") and find another 'openBracketCount = 0', and find the 
                // leftTree = "3)(1" which is wrong, so we have to break out the for 
                // loop when we finish the same level right subtree
                break;
            }
        }
        return root;
    }
}

Time Complexity:
Worst case (skewed tree): O(n^2)
Average case (balanced tree): O(n log n)
Best case (no children): O(n)
Space Complexity:
Worst case: O(n^2) due to substring duplication
Average case: O(n log n)
Best case: O(n)

Solution 3: Stack (120 min)
Style 1: For loop
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class Solution {
    /**
     * @param s: a string
     * @return: a root of this tree
     */
    public TreeNode str2tree(String s) {
        Stack<TreeNode> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '-' || Character.isDigit(c)) {
                int start = i;
                while(i < s.length() && (s.charAt(i) == '-' || Character.isDigit(s.charAt(i)))) {
                    i++;
                }
                int val = Integer.parseInt(s.substring(start, i));
                TreeNode node = new TreeNode(val);
                // Add to parent if exist
                if(!stack.isEmpty()) {
                    TreeNode parent = stack.peek();
                    if(parent.left == null) {
                        parent.left = node; 
                    } else {
                        parent.right = node;
                    }
                }
                stack.push(node);
                // Adjust index one step back prepare for next iteration in for loop 
                // since the wihle loop end as one more step forward
                i--;
            } else if(c == ')') {
                stack.pop();
            } else { // C == '('
                // '(' is skipped as it just indicates start of a child
            }
        }
        return stack.isEmpty() ? null : stack.peek();
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Style 2: While loop
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class Solution {
    /**
     * @param s: a string
     * @return: a root of this tree
     */
    public TreeNode str2tree(String s) {
        Stack<TreeNode> stack = new Stack<>();
        int i = 0;
        while(i < s.length()) {
            char c = s.charAt(i);
            if(c == '-' || Character.isDigit(c)) {
                int start = i;
                while(i < s.length() && (s.charAt(i) == '-' || Character.isDigit(s.charAt(i)))) {
                    i++;
                }
                int val = Integer.parseInt(s.substring(start, i));
                TreeNode node = new TreeNode(val);
                // Add to parent if exist
                if(!stack.isEmpty()) {
                    TreeNode parent = stack.peek();
                    if(parent.left == null) {
                        parent.left = node; 
                    } else {
                        parent.right = node;
                    }
                }
                stack.push(node);
            } else if(c == ')') {
                stack.pop();
                i++;
            } else { // C == '('
                // '(' is skipped as it just indicates start of a child
                i++;
            }
        }
        return stack.isEmpty() ? null : stack.peek();
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer QWEN
LeetCode 536. Construct Binary Tree from String is the inverse problem of LeetCode 606: given a string like "4(2(3)(1))(6(5))", you need to reconstruct the corresponding binary tree.
A clean and efficient Java solution uses recursive parsing with an index pointer (passed by reference via an array or helper class). Here's a well-structured Java implementation:
class Solution {
    public TreeNode str2tree(String s) {
        if (s == null || s.isEmpty()) return null;
        int[] index = {0}; // mutable index to track current parsing position
        return buildTree(s, index);
    }

    private TreeNode buildTree(String s, int[] idx) {
        if (idx[0] >= s.length()) return null;

        // Parse the number (could be negative)
        int start = idx[0];
        if (s.charAt(idx[0]) == '-') {
            idx[0]++;
        }
        while (idx[0] < s.length() && Character.isDigit(s.charAt(idx[0]))) {
            idx[0]++;
        }
        int val = Integer.parseInt(s.substring(start, idx[0]));
        TreeNode node = new TreeNode(val);

        // Check for left subtree
        if (idx[0] < s.length() && s.charAt(idx[0]) == '(') {
            idx[0]++; // skip '('
            node.left = buildTree(s, idx);
            idx[0]++; // skip ')'
        }

        // Check for right subtree
        if (idx[0] < s.length() && s.charAt(idx[0]) == '(') {
            idx[0]++; // skip '('
            node.right = buildTree(s, idx);
            idx[0]++; // skip ')'
        }

        return node;
    }
}
How it works:
1.Parse the current node's value: Read digits (including optional leading -) to form the integer.
2.Check for left child: If next char is '(', recursively parse the left subtree.
3.Check for right child: After left subtree (if any), if next char is '(', parse the right subtree.
4.Use int[] index: This acts as a mutable reference so all recursive calls share the same parsing position.
This approach correctly handles:
- Negative numbers ("-4(2)")
- Missing children (e.g., "4(2)" means 4 has only left child 2)
- Nested structures ("4(2(3))(6(5))")
The algorithm runs in O(n) time and O(h) space (recursion stack), where n is string length and h is tree height .

Refer to Deepseek
Here's a Java solution for LeetCode 536 "Construct Binary Tree from String":
class Solution {
    private int index = 0;
    
    public TreeNode str2tree(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return helper(s);
    }
    
    private TreeNode helper(String s) {
        if (index >= s.length()) {
            return null;
        }
        
        // Parse the number (could be negative)
        StringBuilder numStr = new StringBuilder();
        while (index < s.length() && (Character.isDigit(s.charAt(index)) || s.charAt(index) == '-')) {
            numStr.append(s.charAt(index));
            index++;
        }
        
        TreeNode root = new TreeNode(Integer.parseInt(numStr.toString()));
        
        // Check for left child
        if (index < s.length() && s.charAt(index) == '(') {
            index++; // skip '('
            root.left = helper(s);
            index++; // skip ')'
        }
        
        // Check for right child
        if (index < s.length() && s.charAt(index) == '(') {
            index++; // skip '('
            root.right = helper(s);
            index++; // skip ')'
        }
        
        return root;
    }
}
Alternative Solution without Global Variable:
class Solution {
    public TreeNode str2tree(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        
        int firstParen = s.indexOf('(');
        
        // If no parentheses, just a single node
        if (firstParen == -1) {
            return new TreeNode(Integer.parseInt(s));
        }
        
        // Parse root value
        TreeNode root = new TreeNode(Integer.parseInt(s.substring(0, firstParen)));
        
        // Find the matching closing parenthesis for left child
        int start = firstParen;
        int leftParenCount = 0;
        
        for (int i = start; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                leftParenCount++;
            } else if (s.charAt(i) == ')') {
                leftParenCount--;
            }
            
            // Found the matching closing parenthesis for left child
            if (leftParenCount == 0) {
                // Parse left child
                String leftSub = s.substring(start + 1, i);
                if (!leftSub.isEmpty()) {
                    root.left = str2tree(leftSub);
                }
                
                // Check if there's a right child
                if (i + 1 < s.length() && s.charAt(i + 1) == '(') {
                    // Parse right child
                    String rightSub = s.substring(i + 2, s.length() - 1);
                    if (!rightSub.isEmpty()) {
                        root.right = str2tree(rightSub);
                    }
                }
                break;
            }
        }
        
        return root;
    }
}
Stack-based Solution:
class Solution {
    public TreeNode str2tree(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '-' || Character.isDigit(c)) {
                // Parse the number
                int start = i;
                while (i < s.length() && (s.charAt(i) == '-' || Character.isDigit(s.charAt(i)))) {
                    i++;
                }
                int val = Integer.parseInt(s.substring(start, i));
                TreeNode node = new TreeNode(val);
                
                // Add to parent
                if (!stack.isEmpty()) {
                    TreeNode parent = stack.peek();
                    if (parent.left == null) {
                        parent.left = node;
                    } else {
                        parent.right = node;
                    }
                }
                stack.push(node);
                i--; // Adjust index
            } else if (c == ')') {
                stack.pop();
            }
            // '(' is skipped as it just indicates start of a child
        }
        
        return stack.isEmpty() ? null : stack.peek();
    }
}
Explanation of the first solution:
1.Approach: Recursive parsing with an index pointer
2.Key steps:
- Parse the root value (handles negative numbers)
- Check for '(' to find left child
- Check for another '(' to find right child
- Recursively build subtrees
3.Time Complexity: O(n) where n is the length of the string
4.Space Complexity: O(h) for recursion stack, where h is the height of the tree
Example walkthrough:
For input "4(2(3)(1))(6(5))":
1.Parse 4 as root
2.Find '(' → parse left child "2(3)(1)":
- Parse 2 as root of left subtree
- Parse 3 as left child of 2
- Parse 1 as right child of 2
3.Find another '(' → parse right child "6(5)":
- Parse 6 as root of right subtree
- Parse 5 as left child of 6
Result:
    4
   / \
  2   6
 / \ /
3  1 5
The solution handles:
- Negative numbers
- Empty strings
- Trees with only left or right children
- Nested parentheses

Q1: Is your Leetcode 536 "Alternative Solution without Global Variable" solution a DFS solution ? And what's the major difference against "DFS + global variable as 'index' solution" ? Any theoretical difference ?
Excellent question! Let me analyze the theoretical differences between these two DFS approaches.
Both Are DFS, But Different Types
Yes, both are DFS solutions, but they use different DFS traversal strategies:
1.Global variable solution: Pre-order DFS with stateful traversal
2.Alternative solution: Divide-and-conquer DFS with substring processing
Theoretical Differences
1. Traversal Strategy
- Global variable: Uses pre-order DFS (root → left → right) with a shared index pointer
- Alternative: Uses divide-and-conquer DFS with string partitioning
2. State Management
- Global variable: Uses shared mutable state (index) across recursive calls
- This is stateful recursion
- All recursive calls modify the same index
- Similar to how a parser/lexer works
- Alternative: Uses immutable substrings
- Each recursive call works on its own substring
- No shared state between calls
- Pure functional recursion style
3. Time Complexity Analysis
Global variable solution: O(n)
Alternative solution: O(n²) in worst case
Why?
- Global variable: Each character is processed exactly once
- Alternative:
- indexOf('(') and parenthesis counting: O(n) per node
- substring() creates new strings: O(k) where k is substring length
- In worst-case (skewed tree like "1(2(3(4)))"):
- Total work: n + (n-1) + (n-2) + ... = O(n²)
4. Space Complexity
Both: O(h) for recursion stack, where h = tree height
But:
- Global variable: No extra string copies
- Alternative: Creates O(h) substring copies
5. Memory Usage Pattern
- Global variable: Reads original string, no duplication
- Alternative: Creates multiple string objects (garbage collection overhead)
6. Algorithmic Paradigm
- Global variable: Iterative DFS with manual stack (the index acts like a stack pointer)
- Alternative: Recursive DFS with problem decomposition
Code Comparison
Global Variable Approach:
private TreeNode helper(String s) {
    // 1. Parse number at current index
    // 2. Move index forward
    // 3. Process left if '(' found
    // 4. Process right if another '(' found
    // All calls share and update the SAME index
}
Characteristics:
- Linear scan through string
- Single pass
- Side effects (index mutation)
Alternative Approach:
public TreeNode str2tree(String s) {
    // 1. Find first '(' to split string
    // 2. Extract left substring by counting parentheses
    // 3. Extract right substring if exists
    // 4. Recursively process substrings
}
Characteristics:
- Recursively partitions string
- Multiple string scans
- Stateless recursion
Visual Example for "4(2(3)(1))(6(5))"
Global Variable (Stateful):
Start: index=0, parse "4"
  → '(' at index=1, move to index=2
  → Recursive call: parse "2" at index=2
    → '(' at index=3, move to index=4
    → Parse "3", index moves...
  → Return, index now at position for right child
  → Parse right child...
Alternative (Divide-and-Conquer):
Root: "4(2(3)(1))(6(5))"
  → Split: rootVal="4", rest="(2(3)(1))(6(5))"
  → Find matching ')': leftSub="2(3)(1)", rightRest="(6(5))"
  → Recursive left: str2tree("2(3)(1)")
  → Recursive right: str2tree("6(5)")
When to Use Which?
Global Variable is Better When:
- Input strings can be large (better time complexity)
- Memory efficiency is important
- You need single-pass parsing
Alternative is Better When:
- Code clarity is priority (easier to understand)
- String length is small
- You want to avoid mutable state (functional programming)
Theoretical Summary
AspectGlobal VariableAlternativeDFS TypeStateful pre-orderStateless divide-and-conquerTime ComplexityO(n) (optimal)O(n²) worst-caseSpace OverheadLow (no string copies)High (substring copies)MutabilityMutable stateImmutableEleganceLess intuitiveMore intuitivePerformanceBetter for large nSlower for large n
Conclusion
Both are valid DFS solutions, but:
- The global variable approach is algorithmically superior (O(n) vs O(n²))
- The alternative approach is conceptually cleaner but less efficient
- Theoretical difference: One uses stateful single-pass traversal, the other uses stateless multi-pass partitioning
For LeetCode, the global variable solution would be preferred due to better performance, though some interviewers might prefer the cleaner alternative solution for smaller inputs or to avoid global state.

Q2: Below is what i observed during debugging for input String = "4(2(3)(1))(6(5))" if no 'break' statement, what will happen:
Critical statement, without it if multiple 'openBracketCount = 0' condition exist, the for loop will continue during certain recursion level (no need at initial level, may happen when it go till child subtree level, e.g if input as "4(2(3)(1))(6(5))", without 'break', the issue will happen at 2nd level as during process the "2(3)(1)", the logic will first go into 3rd level to process "(3)" and since "(1)" at the same level, it will continue on "(1)" as right subtree as well, after procesing "(1)" it will come back to 2nd level, and index still hold on first ')' as index = 3, since no 'break', based on for loop it will continue with remain substring(index = 4 to 6 as "(1)") and find another 'openBracketCount = 0', and find the leftTree = "3)(1" which is wrong, so we have to break out the for loop when we finish the same level right subtree

Excellent observation! You've caught the exact nuance that many miss. Let me validate and elaborate on your debugging insight.
Your Analysis is Spot-On!
You're absolutely correct: The issue occurs at deeper recursion levels, not necessarily at the root level. Let me trace through exactly what happens:
Detailed Trace for "4(2(3)(1))(6(5))"
Step 1: Root Level ("4(2(3)(1))(6(5))")
firstParen = 1 (position of '(' before 2)
Loop through:
  i=1: '(' -> count=1
  i=2: '2' -> count=1
  i=3: '(' -> count=2  # Start of "(3)"
  i=4: '3' -> count=2
  i=5: ')' -> count=1  # End of "(3)"
  i=6: '(' -> count=2  # Start of "(1)"
  i=7: '1' -> count=2
  i=8: ')' -> count=1  # End of "(1)"
  i=9: ')' -> count=0  # BREAK should happen here!
  
Without break, continues:
  i=10: '(' -> count=1  # Start of "(6(5))"
  i=11: '6' -> count=1
  i=12: '(' -> count=2
  i=13: '5' -> count=2
  i=14: ')' -> count=1
  i=15: ')' -> count=0  # End of string
Step 2: The Real Problem - Processing "2(3)(1)"
When we recursively process "2(3)(1)" (left child of root):
// Inside helper("2(3)(1)")
firstParen = 1 (position of '(' before 3)

i=1: '(' -> count=1
i=2: '3' -> count=1
i=3: ')' -> count=0  # First time count=0!
# Without break, loop continues!
i=4: '(' -> count=1  # Start of "(1)"
i=5: '1' -> count=1
i=6: ')' -> count=0  # Second time count=0

// Now leftEnd = 6 (instead of 3)
// leftSub = substring(2, 6) = "3)(1"  ← WRONG!
Why This Happens Only at Certain Levels
Root Level Actually Works (Coincidentally)
For the root, after processing both children, there's nothing left in the string. So even without break, the left substring might still be correct at root level for certain inputs.
But at deeper levels like processing "2(3)(1)", we have siblings at the same level:
2(3)(1)
  ↑  ↑
 left right children of node 2
The Core Issue: Multiple Siblings at Same Level
The problem occurs when a node has both left and right children:
- First child ends at count=0
- Second child starts immediately after
- Without break, the loop continues and sees another count=0
Visual Representation
For "2(3)(1)":
Positions: 0 1 2 3 4 5 6
String:    2 ( 3 ) ( 1 )
           ↑ ↑     ↑     ↑
          root     |     end of right
               end of left
Without break:
- First count=0 at position 3 (after processing (3))
- Continues to position 6 (after processing (1))
- Takes substring 2-6 = "3)(1" (incorrect!)
With break:
- First count=0 at position 3
- Breaks immediately
- Takes substring 2-3 = "3" (correct!)
- Then finds right child at position 4-6 = "(1)"
The Recursive Pattern
What you observed is exactly right:
1.Root level: May work by coincidence (no following siblings at higher level)
2.Child level with siblings: Definitely fails without break
3.Leaf level: No parentheses, so no issue
Complete Example with Your Input
Let's trace "4(2(3)(1))(6(5))" fully:
Root: "4(2(3)(1))(6(5))"
  → Left child string: "2(3)(1)"
    → Process "2(3)(1)":
      * Without break: leftEnd = 6, leftSub = "3)(1" (WRONG!)
      * With break: leftEnd = 3, leftSub = "3" (CORRECT!)
      * Then right child: "(1)"
      
  → Right child string: "6(5)"
The Critical Insight
You're exactly right: When processing a node with both left and right children, we need to stop after the left child's closing parenthesis. Otherwise, we'll continue into the right child and get a malformed substring.
What About Single Child?
For "4(2)":
- count becomes 0 only once
- Works with or without break
For "4(2(3))":
- count becomes 0 only once (after processing the entire left subtree)
- Works with or without break
Summary of Your Observation
You've correctly identified that:
1.The break is critical for nodes with both left and right children
2.The issue manifests at child levels where we have sibling nodes
3.Without break, the algorithm incorrectly merges left and right children into one substring
4.With break, we properly isolate each child's subtree string
This is a perfect example of how debugging reveals subtle algorithmic requirements that aren't obvious from just reading the problem statement!

Also refer to Kimi
You have already nailed the exact mechanism—the missing break lets the loop re-scan the same physical string after the right sibling has been processed, so the second time openBracketCount == 0 is met the slice indices straddle both subtrees and you get the garbage string "3)(1".
Below are the memory values while we are inside the call
helper("2(3)(1)")
when no break is present.
Snapshot without break (second-level call)
s = "2(3)(1)"
firstOpenBracketIndex = 1
start = 1
openBracketCount = 0
Loop iteration (no early exit):
icharopenBracketCountleftEnd updated?remark1'('1–2'3'1–3')'0leftEnd = 31st zero—would be correct spot4'('1–loop keeps going5'1'1–6')'0leftEnd = 62nd zero—overwrites previous
After the loop finishes:
leftTree = s.substring(start + 1, leftEnd)
         = s.substring(2, 6)        // characters at indices 2 … 5
         = "3)(1"
That substring is not balanced (starts with digit, ends inside parentheses) and does not represent any valid subtree, so every later recursive call on it is doomed.
Why the break is mandatory
The first time openBracketCount == 0 we have consumed the entire left child (including its own right subtree if it had one).
Everything that follows that closing parenthesis belongs to the parent call (in this case the right child of node 2).
If we continue the loop we are re-scanning the sibling, and the next zero-balance event will span both children, giving the malformed cut you observed.
The break is therefore not optional syntactic sugar—it is the formal delimiter that freezes the boundary of the current child’s substring and guarantees the divide-and-conquer invariant:
“Each recursive call owns exactly one balanced parenthesis block.”

Q3: How about the time and space complexity for Divide and Conquer solution ?
Time Complexity Analysis
Worst Case: O(n²)
Why O(n²)? Let's break it down:
1.String.indexOf(): O(n) per call
2.For-loop with parenthesis counting: O(n) per call
3.String.substring(): O(k) where k is substring length
4.Recursive calls: O(h) levels, where h is tree height
Worst-case Example: Skewed Tree
For a left-skewed tree like "1(2(3(4)))":
Recursion level 1: Process "1(2(3(4)))"
  - indexOf('('): O(n)
  - For loop: O(n)
  - substring(): O(n-1)
  Total: ~3n

Recursion level 2: Process "2(3(4))"
  - indexOf('('): O(n-1)
  - For loop: O(n-1)
  - substring(): O(n-2)
  Total: ~3(n-1)

Recursion level 3: Process "3(4)"
  - indexOf('('): O(n-2)
  - For loop: O(n-2)
  - substring(): O(n-3)
  Total: ~3(n-2)

Total work: 3n + 3(n-1) + 3(n-2) + ... = O(3 * n²/2) = O(n²)

Average Case (Balanced Tree): O(n log n)
For a balanced tree:
- Each level processes ~n characters
- log n levels
- Total: O(n log n)
Space Complexity Analysis
Worst Case: O(n²)
Why O(n²) in worst case?
1.Recursion Stack: O(h) where h = tree height
- Best case (balanced): O(log n)
- Worst case (skewed): O(n)
2.String Substrings: This is the bigger issue!
- Each recursive call creates new substring objects
- In worst case (skewed tree), total substring length:
Level 1: substring of length n-1
Level 2: substring of length n-2
Level 3: substring of length n-3
...
Total substring characters: (n-1) + (n-2) + ... + 1 = O(n²)
Memory Breakdown:
Recursion call 1: "1(2(3(4)))"  -> creates substring "2(3(4))" (length n-1)
Recursion call 2: "2(3(4))"     -> creates substring "3(4)"    (length n-2)
Recursion call 3: "3(4)"        -> creates substring "4"       (length n-3)
...
Total characters stored: O(n²)
Comparison with Global Index Solution
AspectThis SolutionGlobal Index SolutionTime ComplexityO(n²) worst, O(n log n) averageO(n) alwaysSpace ComplexityO(n²) worst (string copies)O(n) worstMemory UsageHigh (substring duplication)Low (no duplication)Code ClarityMore intuitiveLess intuitiveMutabilityImmutable (functional style)Mutable state
Optimization Opportunities
1. Avoid substring() duplication
Could use index ranges instead of creating new strings:
private TreeNode helper(String s, int start, int end) {
    // Process s.substring(start, end) without creating new string
}
2. Cache indexOf results
But still limited by the nested scanning pattern.
Example Walkthrough for "4(2(3)(1))(6(5))"
Let's trace operations (n = 15):
Level 1 ("4(2(3)(1))(6(5))"):
- indexOf('('): scans 15 chars → 15 ops
- For loop: scans 15 chars → 15 ops
- substring(2, 9): copies 7 chars → 7 ops
- substring(11, 14): copies 3 chars → 3 opsTotal: ~40 ops
Level 2 ("2(3)(1)"):
- indexOf('('): scans 7 chars → 7 ops
- For loop: scans 7 chars → 7 ops
- substring(2, 3): copies 1 char → 1 op
- substring(4, 7): copies 3 chars → 3 opsTotal: ~18 ops
Level 3 ("3"), "1", "6(5)":
- Each similar pattern, smaller sizes
Total: Approximately O(n²) in skewed case
Summary
Time Complexity:
- Worst case (skewed tree): O(n²)
- Average case (balanced tree): O(n log n)
- Best case (no children): O(n)
Space Complexity:
- Worst case: O(n²) due to substring duplication
- Average case: O(n log n)
- Best case: O(n)
This solution is clear but inefficient for large inputs. The global index solution with O(n) time and O(n) space is much better for performance-critical applications.

Q4: Do we have a stack based solution ?
Refer to QWEN
Yes! LeetCode 536 can be elegantly solved using a stack-based iterative approach. This method simulates the recursive parsing by tracking parent nodes as we encounter parentheses.
Here's a clean stack solution:
class Solution {
    public TreeNode str2tree(String s) {
        if (s == null || s.isEmpty()) return null;
        
        Stack<TreeNode> stack = new Stack<>();
        int i = 0;
        
        while (i < s.length()) {
            char c = s.charAt(i);
            
            if (c == ')') {
                // Close current node - pop from stack
                stack.pop();
                i++;
            } 
            else if (c == '(') {
                // Open new child - will be handled when we parse the number
                i++;
            } 
            else {
                // Parse number (including negative)
                int start = i;
                if (c == '-') i++;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    i++;
                }
                int val = Integer.parseInt(s.substring(start, i));
                TreeNode node = new TreeNode(val);
                
                // If stack is not empty, this node is a child of the top node
                if (!stack.isEmpty()) {
                    TreeNode parent = stack.peek();
                    if (parent.left == null) {
                        parent.left = node;
                    } else {
                        parent.right = node;
                    }
                }
                
                // Push current node to stack
                stack.push(node);
            }
        }
        
        // The last remaining node in stack is the root
        return stack.peek();
    }
}
How it works:
Key Insight:
- '(' indicates we're about to process a child of the current top node
- Number creates a new node and attaches it to the current parent (top of stack)
- ')' indicates we've finished processing the current node, so we pop it from stack
Step-by-step with "4(2(3)(1))(6(5))":
1.Parse 4 → create node(4), stack = [4]
2.Encounter ( → skip, next is 2
3.Parse 2 → create node(2), attach as left child of 4, stack = [4, 2]
4.Encounter ( → skip, next is 3
5.Parse 3 → create node(3), attach as left child of 2, stack = [4, 2, 3]
6.Encounter ) → pop 3, stack = [4, 2]
7.Encounter ( → skip, next is 1
8.Parse 1 → create node(1), attach as right child of 2, stack = [4, 2, 1]
9.Encounter ) → pop 1, stack = [4, 2]
10.Encounter ) → pop 2, stack = [4]
11.Encounter ( → skip, next is 6
12.Parse 6 → create node(6), attach as right child of 4, stack = [4, 6]
13.Continue similarly for 5...
Advantages of Stack Approach:
- Iterative (no recursion stack overflow risk)
- Single pass O(n) time complexity
- Intuitive - directly mirrors the tree construction process
- Handles all edge cases naturally (negative numbers, missing children, etc.)
Time/Space Complexity:
- Time: O(n) - each character processed once
- Space: O(h) - stack depth equals tree height (O(n) worst case, O(log n) balanced)
This stack solution is often preferred in interviews because it demonstrates understanding of both tree traversal patterns and iterative problem-solving techniques.

Q5: What's the time and space complexity for below solution ?
Time Complexity Analysis
O(n) - Linear Time
Breakdown:
1.Single pass through string: Each character is processed exactly once
- For loop: for(int i = 0; i < s.length(); i++) → O(n)
2.Number parsing while loop: O(m) for each number, but total across all numbers is O(n)
while(i < s.length() && (s.charAt(i) == '-' || Character.isDigit(s.charAt(i)))) {
    i++;
}
- Each character belongs to exactly one number
- Total iterations across all numbers = n (minus parentheses)
3.Integer.parseInt(): O(k) for each number, but sum of all k = O(n)
- Each digit is processed exactly once
4.Stack operations: O(1) per node
- Each node is pushed once and popped once
- Total: O(n) operations
Total: O(n) + O(n) + O(n) = O(n)
Space Complexity Analysis
O(n) - Linear Space
Breakdown:
1.Stack space: O(h) where h = height of tree
- Worst case (skewed tree): h = n → O(n)
- Best case (balanced tree): h = log n → O(log n)
2.Tree nodes storage: O(n) for all TreeNode objects
3.String parsing overhead: O(1) additional space
- No substring creation (unlike previous solution)
- Only parsing current number in place
Total: O(n) for tree nodes + O(h) for stack = O(n)
Detailed Walkthrough for "4(2(3)(1))(6(5))"
Let's trace operations (n = 15 characters):
String: "4(2(3)(1))(6(5))"
       i:0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5
           (numbers simplified for clarity)
Step-by-step:
1.i=0: '4' (digit)
- Parse "4" (i moves to 1)
- Create node(4), stack: [4]
2.i=1: '(' → skip
3.i=2: '2' (digit)
- Parse "2" (i moves to 3)
- Create node(2), attach to stack.peek().left
- stack: [4, 2]
4.i=3: '(' → skip
5.i=4: '3' (digit)
- Parse "3" (i moves to 5)
- Create node(3), attach to stack.peek().left
- stack: [4, 2, 3]
6.i=5: ')'
- stack.pop() → stack: [4, 2]
7.i=6: '(' → skip
8.i=7: '1' (digit)
- Parse "1" (i moves to 8)
- Create node(1), attach to stack.peek().right (since left exists)
- stack: [4, 2, 1]
9.i=8: ')'
- stack.pop() → stack: [4, 2]
10.i=9: ')'
- stack.pop() → stack: [4]
11.i=10: '(' → skip
12.i=11: '6' (digit)
- Parse "6" (i moves to 12)
- Create node(6), attach to stack.peek().right
- stack: [4, 6]
13.i=12: '(' → skip
14.i=13: '5' (digit)
- Parse "5" (i moves to 14)
- Create node(5), attach to stack.peek().left
- stack: [4, 6, 5]
15.i=14: ')'
- stack.pop() → stack: [4, 6]
16.i=15: ')'
- stack.pop() → stack: [4]
Total character visits: 15 (exactly once each)
Total stack operations: 8 pushes + 7 pops = 15 operations
Algorithm Efficiency
Key Optimizations:
1.Single pass: No backtracking or rescanning
2.No substring duplication: Uses indices only
3.Incremental parsing: Numbers parsed as encountered
4.Stack tracks parent relationship: Eliminates need for parenthesis matching logic
Comparison with Previous Solutions:
OperationStack SolutionSubstring RecursiveGlobal IndexTimeO(n)O(n²) worstO(n)SpaceO(n)O(n²) worstO(n)String CopiesNoneMany substringsNoneCode ComplexityMediumHighLow
Worst-Case Analysis
Time Complexity:
- String traversal: O(n) guaranteed
- Number parsing: Each digit processed once in while loop
- Stack operations: O(1) per node, O(n) total
- No nested loops: Constant time per character
Space Complexity Worst Cases:
1.Skewed tree (e.g., "1(2(3(4)))"):
- Stack depth = n → O(n)
- Tree nodes = n → O(n)
- Total: O(2n) = O(n)
2.Balanced tree:
- Stack depth = log n → O(log n)
- Tree nodes = n → O(n)
- Total: O(n + log n) = O(n)
Edge Cases Performance
1.Empty string: O(1) - immediate return
2.Single number: O(1) - parse once
3.All right children: O(n) - stack depth = n
4.Large numbers: O(k) to parse each number, but sum of k = n
Summary
Time Complexity: O(n) - Optimal, cannot be better than linear since we must read entire input.
Space Complexity: O(n) - Optimal for storing the tree, plus O(h) for stack.
This stack-based solution is the most efficient among all approaches:
- Optimal time: O(n)
- Minimal memory: No string duplication
- Clean implementation: Single pass with clear state management
The stack elegantly handles the nested structure without needing complex parenthesis matching or recursion overhead.

Refer to
L297.Serialize and Deserialize Binary Tree (Ref.L449,L536)
L449.Serialize and Deserialize BST (Ref.L297,L536)
L606.Construct String from Binary Tree (Ref.L297,L449,L536,L652)
L761.Special Binary String (Ref.L678,L2116)
