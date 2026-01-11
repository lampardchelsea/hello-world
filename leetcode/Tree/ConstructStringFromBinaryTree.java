/**
 Refer to
 https://leetcode.com/problems/construct-string-from-binary-tree/
 You need to construct a string consists of parenthesis and integers from a binary tree with the preorder traversing way.

The null node needs to be represented by empty parenthesis pair "()". And you need to omit all the empty parenthesis 
pairs that don't affect the one-to-one mapping relationship between the string and the original binary tree.

Example 1:
Input: Binary tree: [1,2,3,4]
       1
     /   \
    2     3
   /    
  4     

Output: "1(2(4))(3)"
Explanation: Originallay it needs to be "1(2(4)())(3()())", 
but you need to omit all the unnecessary empty parenthesis pairs. 
And it will be "1(2(4))(3)".

Example 2:
Input: Binary tree: [1,2,3,null,4]
       1
     /   \
    2     3
     \  
      4 

Output: "1(2()(4))(3)"
Explanation: Almost the same as the first example, 
except we can't omit the first parenthesis pair to break the one-to-one mapping relationship between the input and the output.
*/

// Solution 1: Recursive
// Refer to
// https://leetcode.com/problems/construct-string-from-binary-tree/solution/
/**
 * Solution 1: Approach #1 Using Recursion [Accepted]
 * https://leetcode.com/articles/construct-string-from-binary-tree/
 * This solution is very simple. We simply need to do the preorder traversal of the given Binary Tree. 
 * But, along with this, we need to make use of braces at appropriate positions. But, we also need to 
 * make sure that we omit the unnecessary braces. To do the preorder traversal, we make use of recursion. 
 * We print the current node and call the same given function for the left and the right children of the 
 * node in that order(if they exist). For every node encountered, the following cases are possible.
 * 
 * Case 1: Both the left child and the right child exist for the current node. In this case, we need to 
 * put the braces () around both the left child's preorder traversal output and the right child's 
 * preorder traversal output.
 * 
 * Case 2: None of the left or the right child exist for the current node. In this case, as shown in 
 * the figure below, considering empty braces for the null left and right children is redundant. 
 * Hence, we need not put braces for any of them.
 *                   1
 *                  / \
 *                 2   3
 *                / \   
 *               4   5
 * output: 1(2(4)(5))(3()())
 * refine: 1(2(4)(5))(3)
 *                   
 * Case 3: Only the left child exists for the current node. As the figure below shows, putting empty 
 * braces for the right child in this case is unnecessary while considering the preorder traversal. 
 * This is because the right child will always come after the left child in the preorder traversal. 
 * Thus, omitting the empty braces for the right child also leads to same mapping between the string 
 * and the binary tree.                        
 *                   1
 *                  / \
 *                 2   3
 *                /   / \   
 *               4   5   6
 * output: 1(2(4)())(3(5)(6))
 * refine: 1(2(4))(3(5)(6))
 * 
 * Case 4: Only the right child exists for the current node. In this case, we need to consider the 
 * empty braces for the left child. This is because, during the preorder traversal, the left child needs 
 * to be considered first. Thus, to indicate that the child following the current node is a right child 
 * we need to put a pair of empty braces for the left child. 
 *                     1
 *                  /    \
 *                 2      3
 *                  \    / \   
 *                   4  5   6
 * output: 1(2()(4))(3(5)(6))
 * refine: cannot remove the () before (4)
 * 
 * Just by taking care of the cases, mentioned above, we can obtain the required output string.
 * 
 * Complexity Analysis
    Time complexity : O(n). The preorder traversal is done over the n nodes of the given Binary Tree.
    Space complexity : O(n). The depth of the recursion tree can go upto n in case of a skewed tree.
*/
class Solution {
    public String tree2str(TreeNode t) {
        if(t == null) {
            return "";
        }
        String result = t.val + "";
        String left = tree2str(t.left);
        String right = tree2str(t.right);
        if(left == "" && right == "") {
            return result;
        }
        if(left == "") {
            return result + "()" + "(" + right + ")";
        }
        if(right == "") {
            return result + "(" + left + ")";
        }
        return result + "(" + left + ")" + "(" + right + ")";
    }
}


// Solution 2: Recursive with StringBuilder
// https://leetcode.com/problems/construct-string-from-binary-tree/discuss/103992/Java-Solution-Tree-Traversal/106929
class Solution {
    public String tree2str(TreeNode t) {
        if(t == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        helper(t, sb);
        return sb.toString();
    }
    
    private void helper(TreeNode node, StringBuilder sb) {
        sb.append(node.val);
        if(node.left != null) {
            sb.append("(");
            helper(node.left, sb);
            sb.append(")");
        }
        if(node.right != null) {
            if(node.left == null) {
                sb.append("()");
            }
            sb.append("(");
            helper(node.right, sb);
            sb.append(")");
        }
    }
}

// Solution 3: Iterative
// Refer to
// https://leetcode.com/problems/construct-string-from-binary-tree/discuss/103992/Java-Solution-Tree-Traversal/106935
class Solution {
    public String tree2str(TreeNode t) {
        if(t == null) {
            return "";
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(t);
        Set<TreeNode> visited = new HashSet<TreeNode>();
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            TreeNode node = stack.peek();
            // Vistied before, pop and put a close ) into res
            if(visited.contains(node)) {
                stack.pop();
                sb.append(")");
            // Not visited before
            } else {
                visited.add(node);
                sb.append("(" + node.val);
                // If left is null, right is not null, need to put a pair of brakcet
                if(node.left == null && node.right != null) {
                    sb.append("()");
                }
                // Push its children into stack first right child then left child
                // since when pop out we need left first then right
                if(node.right != null) {
                    stack.push(node.right);
                }
                if(node.left != null) {
                    stack.push(node.left);
                }
            }
        }
        // Don't forget to trim the most outside bracket
        return sb.substring(1, sb.length() - 1);
    }
}

































































https://leetcode.com/problems/construct-string-from-binary-tree/description/
Given the root node of a binary tree, your task is to create a string representation of the tree following a specific set of formatting rules. The representation should be based on a preorder traversal of the binary tree and must adhere to the following guidelines:
- Node Representation: Each node in the tree should be represented by its integer value.
- Parentheses for Children: If a node has at least one child (either left or right), its children should be represented inside parentheses. Specifically:
- If a node has a left child, the value of the left child should be enclosed in parentheses immediately following the node's value.
- If a node has a right child, the value of the right child should also be enclosed in parentheses. The parentheses for the right child should follow those of the left child.
- Omitting Empty Parentheses: Any empty parentheses pairs (i.e., ()) should be omitted from the final string representation of the tree, with one specific exception: when a node has a right child but no left child. In such cases, you must include an empty pair of parentheses to indicate the absence of the left child. This ensures that the one-to-one mapping between the string representation and the original binary tree structure is maintained.
In summary, empty parentheses pairs should be omitted when a node has only a left child or no children. However, when a node has a right child but no left child, an empty pair of parentheses must precede the representation of the right child to reflect the tree's structure accurately.
 
Example 1:

Input: root = [1,2,3,4]
Output: "1(2(4))(3)"
Explanation: Originally, it needs to be "1(2(4)())(3()())", but you need to omit all the empty parenthesis pairs. And it will be "1(2(4))(3)".

Example 2:


Input: root = [1,2,3,null,4]
Output: "1(2()(4))(3)"
Explanation: Almost the same as the first example, except the () after 2 is necessary to indicate the absence of a left child for 2 and the presence of a right child.
 
Constraints:
- The number of nodes in the tree is in the range [1, 104].
- -1000 <= Node.val <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2026-01-10
Solution 1: DFS (30 min)
The working pattern on constructing String from tree problems can refer to L297.Serialize and Deserialize Binary Tree (Ref.L449,L536), L449.Serialize and Deserialize BST (Ref.L297,L536):
1st which return type for recursion call (e.g usually Void return + StringBuilder -> play as a global varaible to penetrate recursion call to record since its an object able to maintain status)
2nd is using which traversal order to build the String (e.g usually pre-order, first get root node print, then get its left and right child node print)
3rd find the terminate condition as base case for recursion call (e.g encounter leaf node)
4th is hardest part as how to print the node value, and also find different condition to print root and child node (e.g different strategy for left or right), and consider if we need global variable (already consider in 1st step)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public String tree2str(TreeNode t) {
        if (t == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        helper(t, sb);
        return sb.toString();
    }
    
    // 1st which return type for recursion call (e.g usually 
    // Void return + StringBuilder -> play as a global varaible 
    // to penetrate recursion call to record since its an object 
    // able to maintain status)
    private void helper(TreeNode root, StringBuilder sb) {
        // 2nd is using which traversal order to build the String 
        // (e.g usually pre-order, first get root node print, then 
        // get its left and right child node print)

        // To record node value must before base return condition
        // The critical issue is that leaf nodes (nodes with no children) 
        // never get their values appended if 'sb.append(root.val)' after
        // 'if (root.left == null && root.right == null) { return;}' 
        // because:
        // - The base case 'if (root.left == null && root.right == null) { return;}' 
        //   executes before 'sb.append(root.val)'
        // - This means leaf nodes return immediately without adding their values 
        //   to the string
        // - Only non-leaf nodes get their values appended
        sb.append(root.val);

        // 3rd find the terminate condition as base case for recursion 
        // call (e.g encounter leaf node)
        if (root.left == null && root.right == null) {
            return;
        }
        
        // 4th is hardest part as how to print the node value, and also 
        // find different condition to print root and child node (e.g 
        // different strategy for left or right), and consider if we 
        // need global variable (already consider in 1st step)

        // Handle left child node
        if (root.left != null) {
            sb.append("(");
            helper(root.left, sb);
            sb.append(")");
        } else {
            sb.append("(");
            sb.append(")");            
        }

        // Handle right child node
        if (root.right != null) {
            sb.append("(");
            helper(root.right, sb);
            sb.append(")");
        }
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/construct-string-from-binary-tree/solutions/103992/java-solution-tree-traversal-by-shawngao-7nd3/comments/106929/
class Solution {
    public String tree2str(TreeNode t) {
        if (t == null) {
        return "";
        }
        StringBuilder sb = new StringBuilder();
        helper(t, sb);
        return sb.toString();
    }
    
    private void helper(TreeNode root, StringBuilder sb) {
        sb.append(root.val);
        if (root.left == null && root.right == null) {
            return;
        }
        if (root.left != null) {
            sb.append("(");
            helper(root.left, sb);
            sb.append(")");
        }
        if (root.right != null) {
            if (root.left == null) {
                sb.append("()");
            }
            sb.append("(");
            helper(root.right, sb);
            sb.append(")");
        }
    }
}

Refer to QWEN
For Leetcode 606. Construct String from Binary Tree i have below pair of solution:
Correction solution pass all 160 test cases:
class Solution {
    public String tree2str(TreeNode t) {
        if (t == null) {
        return "";
        }
        StringBuilder sb = new StringBuilder();
        helper(t, sb);
        return sb.toString();
    }

    private void helper(TreeNode root, StringBuilder sb) {
        sb.append(root.val);
        if (root.left == null && root.right == null) {
            return;
        }
        if (root.left != null) {
            sb.append("(");
            helper(root.left, sb);
            sb.append(")");
        } else {
            sb.append("(");
            sb.append(")");            
        }
        if (root.right != null) {
            sb.append("(");
            helper(root.right, sb);
            sb.append(")");
        }
    }
}
Wrong solution pass 0 test cases:
class Solution {
    public String tree2str(TreeNode t) {
        if (t == null) {
        return "";
        }
        StringBuilder sb = new StringBuilder();
        helper(t, sb);
        return sb.toString();
    }

    private void helper(TreeNode root, StringBuilder sb) {
        //sb.append(root.val);
        if (root.left == null && root.right == null) {
            return;
        }
        sb.append(root.val);
        if (root.left != null) {
            sb.append("(");
            helper(root.left, sb);
            sb.append(")");
        } else {
            sb.append("(");
            sb.append(")");            
        }
        if (root.right != null) {
            sb.append("(");
            helper(root.right, sb);
            sb.append(")");
        }
    }
}
Great question! The difference between these two solutions comes down to when the node's value is appended to the StringBuilder, which affects the order of operations in the recursive traversal.
Let me trace through both solutions with the input [1,2,3,4] (which represents a tree where 1 is root, 2 and 3 are children of 1, and 4 is left child of 2).
Tree Structure:
    1
   / \
  2   3
 /
4
Correct Solution Analysis
In the correct solution, sb.append(root.val) happens first:
private void helper(TreeNode root, StringBuilder sb) {
    sb.append(root.val);  // ← Append value FIRST
    if (root.left == null && root.right == null) {
        return;
    }
    // ... process children
}
Execution trace:
1.helper(1, sb): append "1", then process children
2.Process left child (2): append "(", call helper(2, sb)
3.helper(2, sb): append "2", then process children
4.Process left child (4): append "(", call helper(4, sb)
5.helper(4, sb): append "4", both children null → return
6.Back to node 2: append ")" to close left subtree
7.Node 2 has no right child → skip right processing
8.Back to node 1: append ")" to close left subtree
9.Process right child (3): append "(", call helper(3, sb)
10.helper(3, sb): append "3", both children null → return
11.Back to node 1: append ")" to close right subtree
Result: "1(2(4))(3)" ✓
Wrong Solution Analysis
In the wrong solution, sb.append(root.val) happens after the base case check:
private void helper(TreeNode root, StringBuilder sb) {
    // sb.append(root.val); ← MOVED DOWN
    if (root.left == null && root.right == null) {
        return;  // ← Returns BEFORE appending value!
    }
    sb.append(root.val);  // ← Append value AFTER base case
    // ... process children
}
Execution trace:
1.helper(1, sb): children exist, so append "1", process children
2.Process left child (2): append "(", call helper(2, sb)
3.helper(2, sb): has left child (4), so doesn't return, append "2"
4.Process left child (4): append "(", call helper(4, sb)
5.helper(4, sb): both children null → returns immediately WITHOUT appending "4"!
6.Back to node 2: append ")" → we have "2()"
7.Node 2 has no right child → skip
8.Back to node 1: append ")" → we have "1(2())"
9.Process right child (3): append "(", call helper(3, sb)
10.helper(3, sb): both children null → returns immediately WITHOUT appending "3"!
11.Back to node 1: append ")" → we have "1(2())()"
Result: "1(2())()" ✗
Key Insight
The critical issue is that leaf nodes (nodes with no children) never get their values appended in the wrong solution because:
- The base case if (root.left == null && root.right == null) { return; } executes before sb.append(root.val)
- This means leaf nodes like 4 and 3 return immediately without adding their values to the string
- Only non-leaf nodes get their values appended
In the correct solution, every node gets its value appended first, then we decide whether to process children based on whether they exist.
Visual Comparison
Correct order for each node:
1.Append current node's value
2.If leaf → return
3.Otherwise → process children
Wrong order for each node:
1.If leaf → return (without appending value!)
2.Otherwise → append value and process children
This subtle change in order completely breaks the algorithm because leaf values are essential parts of the output string.

Time and Space Complexity:
The time complexity of this solution for LeetCode 606. Construct String from Binary Tree is O(n), where n is the number of nodes in the binary tree.
Detailed Analysis:
Time Complexity: O(n)
- Each node is visited exactly once during the recursive traversal
- For each node, we perform constant-time operations:
- Append the node's value to StringBuilder: O(1) amortized
- Check if children are null: O(1)
- Append parentheses characters: O(1) amortized
However, there's an important nuance regarding the string building cost:
String Length Consideration
The output string can be significantly longer than the number of nodes due to parentheses. In the worst case (a completely unbalanced tree like a linked list), the output string length is O(n) because:
- Each node contributes its value (assuming single-digit values for simplicity)
- Each node except the root gets wrapped in parentheses
- Total characters ≈ n (values) + 2(n-1) (parentheses) = O(n)
If we consider multi-digit numbers, the total length becomes O(n × d) where d is the average number of digits per node value. However, in algorithmic analysis for this problem, we typically assume single-digit values or consider the number of nodes as the primary input size.
Space Complexity: O(n)
- Recursion stack: O(h) where h is the height of the tree (O(n) worst case for skewed tree, O(log n) for balanced tree)
- StringBuilder: O(n) to store the output string
- Total auxiliary space: O(n)
Why it's O(n) and not worse:
Even though we're building a string with parentheses, the recursive function itself only does constant work per node. The StringBuilder efficiently handles the string construction with amortized O(1) append operations.
Conclusion: The solution has O(n) time complexity and O(n) space complexity, which is optimal since we need to visit every node at least once and produce an output string that contains information about every node.



Refer to
L297.Serialize and Deserialize Binary Tree (Ref.L449,L536)
L449.Serialize and Deserialize BST (Ref.L297,L536)
L536.Lint880.Construct Binary Tree from String (Ref.L297,L449,L606)
L652.Find Duplicate Subtrees (Ref.L1948)
DFS return logic
