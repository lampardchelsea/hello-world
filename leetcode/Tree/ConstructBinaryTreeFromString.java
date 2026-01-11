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

Refer to
L297.Serialize and Deserialize Binary Tree (Ref.L449,L536)
L449.Serialize and Deserialize BST (Ref.L297,L536)
L606.Construct String from Binary Tree (Ref.L297,L449,L536,L652)
