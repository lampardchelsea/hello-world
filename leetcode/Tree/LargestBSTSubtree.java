/**
 * Refer to
 * www.cnblogs.com/grandyang/p/5188938.html
 * Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means 
   subtree with largest number of nodes in it.

    Note:
    A subtree must include all of its descendants.
    Here's an example:

        10
        / \
       5  15
      / \   \ 
     1   8   7
    The Largest BST Subtree in this case is the highlighted one. 
    The return value is the subtree's (1,5,8) size, which is 3.
    
    Hint:
    You can recursively use algorithm similar to 98. Validate Binary Search Tree at each node of the tree, 
    which will result in O(nlogn) time complexity.
    
    Follow up:
    Can you figure out ways to solve it with O(n) time complexity?
 *
 *
 * Solution
 * www.cnblogs.com/grandyang/p/5188938.html
 * https://discuss.leetcode.com/topic/36995/share-my-o-n-java-code-with-brief-explanation-and-comments
*/
import java.util.Stack;

public class LargestBSTSubtree {
private class TreeNode {
		TreeNode left;
		TreeNode right;
		int val;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
	// Solution 1: Base on Validate Binary Search Tree (Stack)
	// Time Complexity: O(n^2) = O(n)(helper()) * O(n)(isValidBST()/countNodes()) 
	int max = 0;
	public int largestBSTSubtree(TreeNode root) {
		// Start count from child of root if exist
		// because we calculate subtree
		if(root.left != null) {
			helper(root.left);
		}
		if(root.right != null) {
			helper(root.right);
		}
	    return max;
	}
	
	private void helper(TreeNode node) {
		if(node == null) {
			return;
		}
		
		if(isValidBST(node)) {
			int tmp = countNodes(node);
			if(tmp > max) {
				max = tmp;
			}
		}
		
		if(node.left != null) {
			helper(node.left);
		}
		
		if(node.right != null) {
			helper(node.right);
		}
	}
	
	private int countNodes(TreeNode node) {
		if(node == null) {
			return 0;
		}
	    return 1 + countNodes(node.left) + countNodes(node.right);
	}
	
	private boolean isValidBST(TreeNode node) {
		if(node == null) {
			return true;
		}
		Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode prev = null;
		while(node != null || !stack.isEmpty()) {
            while(node != null) {
    			stack.push(node);
    			node = node.left;
            }
    		node = stack.pop();
    		if(prev != null && prev.val >= node.val) {
    			return false;
    		}
    		prev = node;
    		node = node.right;
		}
		return true;
	}

	// Solution 2: Postorder traverse (because need to find tree, start
	// from leaves is better, so use postorder) + build class for convienent
	// decision whether its BST or not
	int result = 0;
	public int largestBSTSubtree2(TreeNode root) {
		if(root == null) {
			return result;
		}
		traverse(root);
		return result;
	}
	
	/**
	 * Postorder traverse
	 *    	    10
		        / \
		       5  15
		      / \   \ 
		     1   8   7
		        / \
		       6   9
     * SearchNode:[size, lower, upper]
     *   1: [1,1,1]
     *   6: [1,6,6]
     *   9: [1,9,9]
     *   8: [3,8,9]
     *   5: [5,1,9]
     *   7: [1,7,7]
     *   15:[-1,0,0] -> because left child = null -> (0,Integer.MAX_VALUE, Integer.MIN_VALUE)
     *               -> 15 < Integer.MAX_VALUE
     *   10:[-1,0,0] -> because right child (15) size is -1
	 */
	public SearchNode traverse(TreeNode root) {
	    if(root == null) {
	    	return new SearchNode(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
	    }
	    SearchNode left = traverse(root.left);
	    SearchNode right = traverse(root.right);
	    // Refer to
	    // https://www.youtube.com/watch?v=iqQ9td7OpiM
	    // left.size == -1 or right.size == -1 means as postorder traverse start
	    // from lower levels, if find any child size already mark as -1 means
	    // start from that level up, not able to construct BST, so create new SearchNode
	    // with same size = -1
	    // root.val <= left.upper or root.val >= right.lower means for BST, the root
	    // node's val must between its left node's largest value and right node's smallest value,
	    // otherwise not able to construct BST
	    if(left.size == -1 || right.size == -1 || root.val <= left.upper || root.val >= right.lower) {
	    	return new SearchNode(-1, 0, 0);
	    }
	    int size = 1 + left.size + right.size;
	    result = Math.max(result, size);
	    return new SearchNode(size, Math.min(root.val, left.lower), Math.max(root.val, right.upper));
	}
	
	private class SearchNode {
		// size of current tree
		int size;
		// range of current tree (lower + upper)
		int lower;
		int upper;
		public SearchNode(int size, int lower, int upper) {
			this.size = size;
			this.lower = lower;
			this.upper = upper;
		}
	}
	
	
	public static void main(String[] args) {
		/**
		 *  Given below Binary Tree (Not BST)
		 *  
			    10
		        / \
		       5  15
		      / \   \ 
		     1   8   7
		        / \
		       6   9
	       
	     * Largest BST start root = 5 (5,1,8,6,9), 
	     * size = 5 
		 */
		LargestBSTSubtree l = new LargestBSTSubtree();
		TreeNode ten = l.new TreeNode(10);
		TreeNode five = l.new TreeNode(5);
		TreeNode fifteen = l.new TreeNode(15);
		TreeNode one = l.new TreeNode(1);
		TreeNode eight = l.new TreeNode(8);
		TreeNode seven = l.new TreeNode(7);
		TreeNode six = l.new TreeNode(6);
		TreeNode nine = l.new TreeNode(9);
		ten.left = five;
		ten.right = fifteen;
		five.left = one;
		five.right = eight;
		fifteen.right = seven;
		eight.left = six;
		eight.right = nine;
		
        int result = l.largestBSTSubtree2(ten);
		System.out.print(result);
	}


}

