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

































https://leetcode.com/problems/validate-binary-search-tree/
Given the root of a binary tree, determine if it is a valid binary search tree (BST).

A valid BST is defined as follows:
- The left subtree of a node contains only nodes with keys less than the node's key.
- The right subtree of a node contains only nodes with keys greater than the node's key.
- Both the left and right subtrees must also be binary search trees.

Example 1:


Input: root = [2,1,3]
Output: true

Example 2:


Input: root = [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.
 
Constraints:
- The number of nodes in the tree is in the range [1, 10^4].
- -2^31 <= Node.val <= 2^31 - 1
--------------------------------------------------------------------------------
Attempt 1: 2022-11-17
Solution 1:  Divide and Conquer (10min)
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
    public boolean isValidBST(TreeNode root) {
        // Refer to
        // https://leetcode.com/problems/validate-binary-search-tree/discuss/1389569/Input-2147483647-ouput%3A-true-why
        // Must use Long to process, as using Integer.MIN_VALUE or 
        // Integer.MAX_VALUE will not able to handle corner case 
        // as root.val = 2147483647 (Its a single node with val = 2147483647  
        // ,so a bst also), which expected return true, but wrongly return false 
        return helper(root, Long.MIN_VALUE, Long.MAX_VALUE); 
    } 
     
    private boolean helper(TreeNode root, long min, long max) { 
        // Base case 
        if(root == null) { 
            return true; 
        } 
        if(root.val <= min || root.val >= max) { 
            return false; 
        } 
        // Divide 
        boolean left = helper(root.left, min, root.val); 
        boolean right = helper(root.right, root.val, max); 
        // Conquer 
        return left && right; 
    } 
}

Time Complexity: O(n)   
Space Complexity: O(n)
Did everyone who use a long tried int before and failed by the test case 2147483647？

Refer to
https://leetcode.com/problems/validate-binary-search-tree/discuss/32109/My-simple-Java-solution-in-3-lines/656786
2147483647 is the maximum integer value. The code is written to say the node must have a value less than whatever is passed as the max of the range, which is good because we want to be sure the same value isn't in the tree twice. However, any possible int value should be allowed, so setting the max to 2147483647 (Integer.MAX_VALUE) at the start doesn't work because then we get a failure for a tree that has the value 2147483647.
If you use a long for the max and min that are passed around, you can use Long.MAX_VALUE instead (which will be larger) as well as Long.MIN_VALUE. Alternatively, you can use an Integer instead of int and set the initial value to null, then write some conditional logic that if max is set to null you don't need to do the comparison.

Solution 2:  Inorder Iterative traversal (10min)
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
    public boolean isValidBST(TreeNode root) { 
        if(root == null) { 
            return true; 
        } 
        TreeNode prev = null; 
        Stack<TreeNode> stack = new Stack<TreeNode>(); 
        while(root != null || !stack.isEmpty()) { 
            while(root != null) { 
                stack.push(root); 
                root = root.left; 
            } 
            root = stack.pop(); 
            if(prev != null && root.val <= prev.val) { 
                return false; 
            } 
            prev = root; 
            root = root.right; 
        } 
        return true; 
    } 
}

Time Complexity: O(n)   
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/validate-binary-search-tree/discuss/32112/Learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-(Java-Solution) 


Refer to
L94.Binary Tree Inorder Traversal (Ref.L98,L230,L144,L145)
L333.Largest BST Subtree (Ref.L98)
L230.Kth Smallest Element in a BST (Ref.L98)
