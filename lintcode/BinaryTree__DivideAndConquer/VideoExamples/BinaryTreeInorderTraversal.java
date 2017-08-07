import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/binary-tree-inorder-traversal/description/
 * Given a binary tree, return the inorder traversal of its nodes' values.

	For example:
	Given binary tree [1,null,2,3],
	
	   1
	    \
	     2
	    /
	   3
	
	return [1,3,2].
	
	Note: Recursive solution is trivial, could you do it iteratively?
 * 
 * https://discuss.leetcode.com/topic/46016/learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-java-solution
 * http://www.jiuzhang.com/solutions/binary-tree-inorder-traversal/
 *
 */
public class BinaryTreeInorderTraversal {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
	// Solution 1: Iterative (This need to remember)
	public List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> list = new ArrayList<Integer>();
		if(root == null) {
			return list;
		}
		Stack<TreeNode> stack = new Stack<TreeNode>();
		while(root != null || !stack.isEmpty()) {
			while(root != null) {
				stack.push(root);
				root = root.left;
			}
			root = stack.pop();
			list.add(root.val);
			root = root.right;
		}
		return list;
	}
	
	
	// Solution 2: Recursive -> Traverse way
	List<Integer> result = new ArrayList<Integer>();
	public List<Integer> inorderTraversal2(TreeNode root) {
		if(root == null) {
			return result;
		}
		helper(root);
		return result;
	}
	
	public void helper(TreeNode root) {
		if(root == null) {
			return;
		}
		helper(root.left);
		result.add(root.val);
		helper(root.right);
	}
	
	
	// Solution 3: Iterative with stack
	public List<Integer> inorderTraversal3(TreeNode root) {
            List<Integer> result = new ArrayList<Integer>();
	    if(root == null) {
	        return result;
	    }
	    Stack<TreeNode> stack = new Stack<TreeNode>();
            while(root != null || !stack.isEmpty()) {
	       while(root != null) {
	           stack.push(root);
		   root = root.left;
	       }
               root = stack.pop();
	       result.add(root.val);	    
	       root = root.left;	    
	    }		
	    return result;	
	}
	
	
	
	public static void main(String[] args) {
		/**
		   1
            \
             3       ==> Expected: 1 2 3 4 5
            / \
           2   4
                \
                 5
		 */
		BinaryTreeInorderTraversal b = new BinaryTreeInorderTraversal();
    	TreeNode one = b.new TreeNode(1);
    	TreeNode two = b.new TreeNode(2);
    	TreeNode three = b.new TreeNode(3);
    	TreeNode four = b.new TreeNode(4);
    	TreeNode five = b.new TreeNode(5);
    	one.right = three;
    	three.left = two;
    	three.right = four;
    	four.right = five;
    	List<Integer> result = b.inorderTraversal(one);
    	System.out.println(result);
	}
	
}
