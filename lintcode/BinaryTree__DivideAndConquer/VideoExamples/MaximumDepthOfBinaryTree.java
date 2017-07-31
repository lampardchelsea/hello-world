/**
 * Refer to
 * http://www.lintcode.com/en/problem/maximum-depth-of-binary-tree/
 * Given a binary tree, find its maximum depth.
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 * Have you met this question in a real interview?
 * Example
    Given a binary tree as follow:
      1
     / \ 
    2   3
       / \
      4   5
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/maximum-depth-of-binary-tree/
*/


// Solution 1: Traverse
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
     * @param root: The root of binary tree.
     * @return: An integer.
     */
    // Define global variable 
    private int depth;
    public int maxDepth(TreeNode root) {
        // initial global variable
        depth = 0;
        helper(root, 1);
        return depth;
    }
    
    // Use traverse method: The 'currDepth' is very
    // similar like we pass in a 'notebook' to record
    // each step, we only need to update result on
    // this 'notebook' instead of return related value
    // (sometimes return other types instead of same
    // result type required in main method) which 
    // Divide and Conquer ask for
    private void helper(TreeNode node, int currDepth) {
        if(node == null) {
            return;
        }
        if(currDepth > depth) {
            depth = currDepth;
        }
        helper(node.left, currDepth + 1);
        helper(node.right, currDepth + 1);
    }
}


// Solution 2: Divide And Conquer
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
     * @param root: The root of binary tree.
     * @return: An integer.
     */
    public int maxDepth(TreeNode root) {
        // Base case
        if(root == null) {
            return 0;
        }
        
        // Divide
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        
        // Merge
        return Math.max(left, right) + 1;
    }
}




