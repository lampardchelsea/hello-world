import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/validate-binary-search-tree/description/
 *  Given a binary tree, determine if it is a valid binary search tree (BST).
 *  Assume a BST is defined as follows:
    (1) The left subtree of a node contains only nodes with keys less than the node's key.
    (2) The right subtree of a node contains only nodes with keys greater than the node's key.
    (3) Both the left and right subtrees must also be binary search trees.

	Example 1:
	
	    2
	   / \
	  1   3
	
	Binary tree [2,1,3], return true.
	
	Example 2:
	
	    1
	   / \
	  2   3
	
	Binary tree [1,2,3], return false. 
 * 
 * 
 * Solution
 * http://www.jiuzhang.com/solution/validate-binary-search-tree/
 * https://discuss.leetcode.com/topic/7179/my-simple-java-solution-in-3-lines
 * https://discuss.leetcode.com/topic/46016/learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-java-solution
 *
 */
public class ValidateBinarySearchTree {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
    // Solution 1: Purely Divide and Conquer
    public boolean isValidBST(TreeNode root) {
    	// Must use Long to process, as using Integer.MIN_VALUE or
    	// Integer.MAX_VALUE will not able to handle corner case
    	// as root.val = 2147483647, which expected return true,
    	// but return false wrongly
        return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    public boolean helper(TreeNode root, long min, long max) {
        // Base case
    	if(root == null) {
            return true;
        }
    	// BST require current root value satisfy: min < root.val < max
    	// which defined on title as strictly large than and less than
    	// given boundary [min, max]
        if(root.val <= min || root.val >= max) {
            return false;
        }
        // Based on BST definition, for left subtree, min value keeps,
        // max value change to current root value, same for right
        // subtree, max value keeps, min value change to current root value
        return helper(root.left, min, root.val) && helper(root.right, root.val, max);
    } 
    
    
    // Solution 2: Iterative with same thought of Inorder Traverse
    public boolean isValidBST2(TreeNode root) {
        if(root == null) {
        	return true;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode prev = null;
        while(root != null || !stack.isEmpty()) {
            while(root != null) {
            	stack.push(root);
            	root = root.left;
            }
            root = stack.pop();
            // This section a little different than iterative
            // inorder traverse template
            if(prev != null && root.val <= prev.val) {
            	return false;
            }
            prev = root;
            root = root.right;
        }
        return true;
    }
    
    
    
}

