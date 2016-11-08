/**
 * Find the sum of all left leaves in a given binary tree.
 * Example:

    3
   / \
  9  20
    /  \
   15   7

 * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
*/
//Wrong way
public class Solution {
	public TreeNode root;
	
	private static class TreeNode {
		private String val;
		private TreeNode left, right;
		public TreeNode(String x) {
			this.val = x;
		}
	}
	
    public static int sum = 0;
    public static int sumOfLeftLeaves(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        sumOfLeftLeavesRec(root, false, sum);
        return sum;
    }
    
    public static void sumOfLeftLeavesRec(TreeNode x, boolean isLeft, int sum) {
        if(x == null) {
            return;
        }
        
        if(x.left == null && x.right == null && isLeft) {
            sum += Integer.valueOf(x.val);
        }
        
        sumOfLeftLeavesRec(x.left, true, sum);
        sumOfLeftLeavesRec(x.right, false, sum);
    }
	
	public static void main(String[] args) {
	/*
	 * The tree used for test
	 *	       1
	 *		 /   \
	 *		2     3
	 *	   / \   /
	 *	  6   5 9
	 *	 /
	 *	8
	*/
	Solution s = new Solution();
	s.root = new TreeNode("1");
	s.root.left = new TreeNode("2");
	s.root.right = new TreeNode("3");
	s.root.left.left = new TreeNode("6");
	s.root.left.right = new TreeNode("5");
	s.root.left.left.left = new TreeNode("8");
	s.root.right.left = new TreeNode("9");
	
	int result = sumOfLeftLeaves(s.root);
	System.out.println(result);
}
