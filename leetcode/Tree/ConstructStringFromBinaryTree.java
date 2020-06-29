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




