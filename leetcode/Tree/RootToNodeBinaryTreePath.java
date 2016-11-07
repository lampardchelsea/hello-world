/**
 * Objective: Given a Binary tree (Not binary Search Tree ), Print a path from root to a given node.
 * Input: A binary tree, a node x
 * Output: Path from root to a given node
 * 
 * Approach : 
 * since it’s not a binary search tree, we cannot use binary search technique to reach to the node. 
 * we need to travel all the nodes in order to find the node
 * 
 * Start from the root and compare it with x, if matched then we have found the node.
 * Else go left and right.
 * Recursively do step 2 and 3 till you find the node x.
 * Now when you have found the node, stop the recursion.
 * Now while going back to the root while back tracking, store the node values in the ArrayList.
 * Reverse the ArrayList and print it.
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Solution {
	public TreeNode root;
	
	private static class TreeNode {
		private String val;
		private TreeNode left, right;
		public TreeNode(String x) {
			this.val = x;
		}
	}
	
	public static List<String> result = new ArrayList<String>();

	public static boolean rootToNodePath(TreeNode root, TreeNode dest) {
		if(root == null) {
			return false;
		}
		if(root == dest || rootToNodePath(root.left, dest) || rootToNodePath(root.right, dest)) {
			result.add(root.val);
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		Solution s = new Solution();
		s.root = new TreeNode("1");
		s.root.left = new TreeNode("2");
		s.root.right = new TreeNode("3");
		s.root.left.left = new TreeNode("6");
		s.root.left.right = new TreeNode("5");
		s.root.left.left.left = new TreeNode("8");
		s.root.right.left = new TreeNode("9");

		rootToNodePath(s.root, s.root.right.left);
		
		// When add into the result the order is from leaf to root,
		// if not reverse, the result path string is leaf to root,
		// e.g 9->3->1, but what we need is root to leaf as 1->3->9
		Collections.reverse(result);
		StringBuilder sb = new StringBuilder();
		
		// Add the arrow between each node
		int size = result.size();
		for(int i = 0; i < size - 1; i++) {
			sb.append(result.get(i)).append("->");
		}
		// Add the last node
		sb.append(result.get(size - 1));
		
		System.out.println(sb.toString());
	}
}
