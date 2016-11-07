import java.util.Collections;
import java.util.LinkedList;
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
	
	public static List<TreeNode> finalPath = new LinkedList<TreeNode>();
	
	public static LinkedList<TreeNode> helper(TreeNode n, TreeNode p, TreeNode q) {
		if(n == null) {
			return null;
		}
		
		LinkedList<TreeNode> left = helper(n.left, p, q);
		LinkedList<TreeNode> right = helper(n.right, p, q);
		
		if(left == null && right == null) {
			if(n == p || n == q) {
				LinkedList<TreeNode> l = new LinkedList<TreeNode>();
				l.add(n);
				return l;
			} else {
				return null;
			}
		} else if(left != null && right != null) {
			finalPath.addAll(left);
			finalPath.add(n);
			Collections.reverse(right);
			finalPath.addAll(right);
			return left;
		} else if(left != null) {
			left.add(n);
			if(n == p || n == q) {
				finalPath.addAll(left);
			}
			return left;
		} else {
			right.add(n);
			if(n == p || n == q) {
				finalPath.addAll(right);
			}
			return right;
		}
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
		
//		helper(s.root, s.root.left, s.root.right.left);
		helper(s.root, s.root.left, s.root.left.left.left);
		
		StringBuilder sb = new StringBuilder();
		
		// Add the arrow between each node
		int size = finalPath.size();
		for(int i = 0; i < size - 1; i++) {
			sb.append(finalPath.get(i).val).append("->");
		}
		// Add the last node
		sb.append(finalPath.get(size - 1).val);
		
		System.out.println(sb.toString());
	}
}

