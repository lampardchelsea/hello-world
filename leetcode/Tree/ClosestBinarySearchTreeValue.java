/**
 * Refer to
 * https://segmentfault.com/a/1190000003797291
 * Given a non-empty binary search tree and a target value, find the value in 
 * the BST that is closest to the target.
 * Note: Given target value is a floating point. You are guaranteed to have 
 * only one unique value in the BST that is closest to the target
 * 
 * 
 * Solution
 * https://www.youtube.com/watch?v=s7QcJi1qGEM
 * https://segmentfault.com/a/1190000003797291
 * 
 */
public class ClosestBinarySearchTreeValue {
private class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
	// Solution 1: Iterative
	public int closetValue(TreeNode root, double target) {
		int result = root.val;
		while(root != null) {
			if(Math.abs(target - root.val) < Math.abs(target - result)) {
				result = root.val;
			}
			// BST property left < root < right
			root = root.val > target ? root.left : root.right;
		}
		return result;
	}
	
	// Solution 2: Recursive
	public int closetValue2(TreeNode root, double target) {
		return helper(root, target, root.val);
	}
	
	private int helper(TreeNode node, double target, int val) {
		if(node == null) {
			return val;
		}
		if(Math.abs(node.val - target) < Math.abs(val - target)) {
			val = node.val;
		}
		if(node.val < target) {
			val = helper(node.right, target, val);
		} else {
			val = helper(node.left, target, val);
		}
		return val;
	}
	
	
	public static void main(String[] args) {
		ClosestBinarySearchTreeValue c = new ClosestBinarySearchTreeValue();
        /**
         *           4
         *          / \
         *         2   5 
         *        / \
         *       1   3         
         */
		TreeNode one = c.new TreeNode(1);
		TreeNode two = c.new TreeNode(2);
		TreeNode three = c.new TreeNode(3);
		TreeNode four = c.new TreeNode(4);
		TreeNode five = c.new TreeNode(5);
		four.left = two;
		four.right = five;
		two.left = one;
		two.right = three;
		double target = 2.3;
		int result = c.closetValue2(four, target);
		System.out.print(result);
	}
}
