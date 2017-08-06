/**
 * Refer to
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/description/
 *  Given a binary tree, find the maximum path sum.
 *  For this problem, a path is defined as any sequence of nodes from some starting node to any node 
 *  in the tree along the parent-child connections. The path must contain at least one node and does 
 *  not need to go through the root.

	For example:
	Given the below binary tree,
	
	       1
	      / \
	     2   3
	
	Return 6. 
 * 
 * 
 * Solution 1: Purely Divide and Conquer
 * http://www.jiuzhang.com/solutions/binary-tree-maximum-path-sum/
 * 
 * Solution 2: Traverse + Divide and Conquer
 * https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java/8
 */
public class BinaryTreeMaximumPathSum {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
	
	// Solution 1: Purely Divide and Conquer
	// Basic idea very like 'Binary Tree Longest Consecutive Sequence'
	// Set up ResultType for Divide and Conquer
    private class ResultType {
        int singlePath;
        int maxPath;
        public ResultType(int singlePath, int maxPath) {
            this.singlePath = singlePath;
            this.maxPath = maxPath;
        }
    }

    public int maxPathSum(TreeNode root) {
        return helper(root).maxPath;
    }
    
    private ResultType helper(TreeNode root) {
        // Base case
        if(root == null) {
            return new ResultType(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        // No leaf case, divide directly
        ResultType left = helper(root.left);
        ResultType right = helper(root.right);
        // Conquer
        // Find value of singlePath
        // Choose 0 means not include current singlePath value into next recursion as both
        // left and right result < 0, and make it + root.val smaller than root.val itself,
        // also, not include means calculate current singlePath from current root and get
        // rid of previous ones
        int singlePath = Math.max(0, Math.max(left.singlePath, right.singlePath)) + root.val;
        // Update maxPath
        int maxPath = Math.max(left.maxPath, right.maxPath);
        // Use 0 the same way as singlePath calculating
        // Refer to
        // https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java/8
        // The most tricky point is the global variable maxValue in the following sentence:
        // maxValue = Math.max(maxValue, left + right + node.val);
        // The second maxValue contains the bigger between the left sub-tree and right sub-tree.
        // if (left + right + node.val < maxValue ) then the result will not include the parent 
        // node which means the maximum path is in the left branch or right branch.
        maxPath = Math.max(maxPath, Math.max(0, left.singlePath) + Math.max(0, right.singlePath) + root.val);
        return new ResultType(singlePath, maxPath);
    }
    
    
    // Solution 2: Traverse + Divide and Conquer
    /**
     * Refer to
     * https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java
     * A path from start to end, goes up on the tree for 0 or more steps, then goes down for 0 or more steps. 
     * Once it goes down, it can't go up. Each path has a highest node, which is also the lowest common 
     * ancestor of all other nodes on the path.
     * A recursive method helper(TreeNode node) (1) computes the maximum path sum with highest node is 
     * the input node, update maximum if necessary (2) returns the maximum sum of the path that can be extended 
     * to input node's parent.
     * 
     * Refer to
     * https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java/8
     * The most tricky point is the global variable maxValue in the following sentence:
     * maxValue = Math.max(maxValue, left + right + node.val);
     * The second maxValue contains the bigger between the left sub-tree and right sub-tree.
     * if (left + right + node.val < maxValue ) then the result will not include the parent node which means 
     * the maximum path is in the left branch or right branch.
     */
    // Set up global variable for Traverse
    // Don't initialize as 0 because the maxValue may < 0
    int maxValue = Integer.MIN_VALUE;
    public int maxPathSum2(TreeNode root) {
        helper2(root);
        return maxValue;
    }

    // As Divide and Conquer way we return int
    public int helper2(TreeNode root) {
        // Base case
        if(root == null) {
            return 0;
        }
        // Divide
        int left = Math.max(0, helper2(root.left));
        int right = Math.max(0, helper2(root.right));
        // Conquer
        // Update maxValue (But not used for return)
        // Same meaning as update 'maxPath'
        maxValue = Math.max(maxValue, left + right + root.val);
        // Returns the maximum sum of the path that can be extended to input node's parent.
        // Same meaning as update 'singlePath'
        return Math.max(left, right) + root.val;
    }
    
    
}
