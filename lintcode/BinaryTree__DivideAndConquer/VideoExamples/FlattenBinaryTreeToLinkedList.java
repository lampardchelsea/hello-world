/**
 * Refer to
 * http://www.lintcode.com/en/problem/flatten-binary-tree-to-linked-list/
 * Flatten a binary tree to a fake "linked list" in pre-order traversal.
 * Here we use the right pointer in TreeNode as the next pointer in ListNode.
 * Notice
 * Don't forget to mark the left child of each node to null. Or you will get Time 
 * Limit Exceeded or Memory Limit Exceeded.
 * Have you met this question in a real interview?
		Example
		
		              1
		               \
		     1          2
		    / \          \
		   2   5    =>    3
		  / \   \          \
		 3   4   6          4
		                     \
		                      5
		                       \
		                        6
 * 
 * Solution
 * https://www.jiuzhang.com/solutions/flatten-binary-tree-to-linked-list/
 * https://discuss.leetcode.com/topic/11444/my-short-post-order-traversal-java-solution-for-share?page=1
 *
 */
public class FlattenBinaryTreeToLinkedList {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
    public void flatten(TreeNode root) {
        helper(root);
    }
    
    // This problem can be treat as a variation of pre-order
    // e.g 
    // left subtree, travel from 2 -> 3 -> 4 is pre-order traverse
    //      2          2
    //     / \   ==>    \
    //    3   4          3
    //                    \
    //                     4
    // But we still can use thought as Divide and Conquer 
    // Concatenate order:
    // (1) Process left subtree and change to right child format (2 -> 3 -> 4)
    // (2) Process right subtree and cange to right child format (5 -> 6)
    // (3) Finally concatenate 1 with (2 -> 3 -> 4) and (5 -> 6)
    // The imporatnt difference than Traverse method is we don't move node
    // to target position one by one, we move as block as left/right subtree
    // The key point to concatenate left subtree and right subtree
    // is to know last node on left subtree, that's why we need to
    // return last node under pre-order traverse
    public TreeNode helper(TreeNode root) {
        // Base case
        if(root == null) {
            return root;
        }
        // Divide
        TreeNode leftLast = helper(root.left);
        TreeNode rightLast = helper(root.right);
        // Merge
        if(leftLast != null) {
            // Connect leftLast with original root.right
            // e.g 4 connect to 5 
            leftLast.right = root.right;
            // Connect root with original root.left but on right child
            // e.g 1 connect to 2 on right
            root.right = root.left;
            // Clean left child
            root.left = null;
        }
        // Note: Must return 'rightLast' before 'leftLast', e.g
        // when we start helper method, it will first process
        // left subtree, travel from 2 -> 3 -> 4 is pre-order traverse
        //      2          2
        //     / \   ==>    \
        //    3   4          3
        //                    \
        //                     4
        // What we want to return is rightLast as 4 after we insert
        // 3 between 2 and 4. If we return leftLast first, for example
        // here will return 3 instead of 4, as 3 is not real rightLast
        // of original subtree, this is wrong, and final result will
        // be 1 -> 2 -> 3 -> 5 -> 6, the 4 is gone. To return real
        // rightLast must return rightLast first.
        if(rightLast != null) {
            return rightLast;
        }
        if(leftLast != null) {
            return leftLast;
        }
        return root;
    }
    
	
     /**
     * Refer to
     * https://discuss.leetcode.com/topic/11444/my-short-post-order-traversal-java-solution-for-share?page=1
     * 
     * Tips:
     * 1. Base on solution of lintcode 'BinaryTreePaths', the Divide and Conquer way always bottom-up,
     *    but since the traverse way here, we need to handle it top-down.
     * 2. The traverse way use global variable to store 'previous' node and recursively update it,
     * 3. Traverse order as 6, 5, 4, 3, 2, 1   
     */
    private TreeNode prev = null;
    public void flatten2(TreeNode root) {
        // Base case
        if(root == null) {
            return;
        }
        // Divide
        flatten2(root.right);
        flatten2(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }	
	
	
    public static void main(String[] args) {
    	/**
	    	  		      1
			               \
			     1          2
			    / \          \
			   2   5    =>    3
			  / \   \          \
			 3   4   6          4
			                     \
			                      5
			                       \
			                        6
    	 */
    	FlattenBinaryTreeToLinkedList f = new FlattenBinaryTreeToLinkedList();
    	TreeNode one = f.new TreeNode(1);
    	TreeNode two = f.new TreeNode(2);
    	TreeNode three = f.new TreeNode(3);
    	TreeNode four = f.new TreeNode(4);
    	TreeNode five = f.new TreeNode(5);
    	TreeNode six = f.new TreeNode(6);
    	one.left = two;
    	one.right = five;
    	two.left = three;
    	two.right = four;
    	five.right = six;
    	//f.flatten(one);
        f.flatten2(one);
    }
    
}
