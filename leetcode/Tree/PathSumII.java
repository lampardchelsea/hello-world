import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/path-sum-ii/description/
 *  Given a binary tree and a sum, find all root-to-leaf paths where each path's 
 *  sum equals the given sum.
	For example:
	Given the below binary tree and sum = 22,
	
	              5
	             / \
	            4   8
	           /   / \
	          11  13  4
	         /  \    / \
	        7    2  5   1
	
	return
	[
	   [5,4,11,2],
	   [5,8,4,5]
	]
 * 
 * Solution
 * https://discuss.leetcode.com/topic/5414/dfs-with-one-linkedlist-accepted-java-solution
 * This question is best practice for Traverse way, as a template contains many critical
 * elements which need to be careful when using Traverse
 * There are 10 points here need to care about
 */
public class PathSumII {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
    // 1.Traverse
    // 2.When we given problem as find all solutions must be DFS
    // 3.Global variable work as Traverse way for helper method
    // 4.Choose LinkedList because of frequent add or remove item on list
	List<List<Integer>> result = new LinkedList<List<Integer>>();
	List<Integer> currResult = new LinkedList<Integer>();
	public List<List<Integer>> pathSum(TreeNode root, int sum) {
		if(root == null) {
			return result;
		}
		helper(result, currResult, root, sum);
		return result;
	}
	
	// 5.As basic idea of Traverse way, helper method return void
	public void helper(List<List<Integer>> result, List<Integer> currResult, TreeNode root, int sum) {
		// 6.Base case
		if(root == null) {
			return;
		}
		// 7.Leaf case
		if(root.left == null && root.right == null && sum == root.val) {
			// Add current combination onto final result
			// 8. Deep copy
			result.add(new LinkedList<Integer>(currResult));
			// 9. Don't forget to remove the last item on currResult for next recursion
			currResult.remove(currResult.size() - 1);
			return;
		}
		// 10.Divide
		helper(result, currResult, root.left, sum - root.val);
		helper(result, currResult, root.right, sum - root.val);
		// Don't forget to remove the last item on currResult for next recursion
        // no matter what whether we add to the final result or not
		currResult.remove(currResult.size() - 1);
	}
}
