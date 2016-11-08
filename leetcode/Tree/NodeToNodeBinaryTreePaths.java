/**
 * https://segmentfault.com/a/1190000003465753
 * 给定一棵二叉树的根节点和两个任意节点，返回这两个节点之间的最短路径
 * 深度优先标记
 * 复杂度
 * 时间 O(h) 空间 O(h) 递归栈空间
 * 思路
 * 两个节点之间的最短路径一定会经过两个节点的最小公共祖先，所以我们可以用LCA的解法(参考LowestCommonAncestorBT.java, 
 * 两者的原理是一致的)。不同于LCA的是，我们返回不只是标记，而要返回从目标结点递归回当前节点的路径。当遇到最小公共祖先的时
 * 候便合并路径。需要注意的是，我们要单独处理目标节点自身是最小公共祖先的情况。
 */

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
	
	// The final paths should return
	public static List<TreeNode> finalPath = new LinkedList<TreeNode>();

	public static LinkedList<TreeNode> helper(TreeNode n, TreeNode p, TreeNode q) {
		if(n == null) {
			return null;
		}
		
		// Same as LowestCommonAncestorBT.java solution, recursively mark and report
		// the node on left or right subtree that matches given p or q. The only
		// difference is we not return the mark, but need to return the path from 
		// target node(p or q) to current node(left or right), use LinkedList to record
		// and return.
		// Because recursively call, we first reach to the leave nodes of left subtree
		// in test tree, the node is 8.
		LinkedList<TreeNode> left = helper(n.left, p, q);
		LinkedList<TreeNode> right = helper(n.right, p, q);
		
		// If current node's right and left path both null has 2 situations
		if(left == null && right == null) {
			// If current node equals to target node p or q which means we find the target 
			// node and as both left, right path are null, it is leave node, need to record 
			// into path, then create a new path, e.g as "l" create here will return and
			// assign value to previous level recursion object LinkedList "left"(represent 
			// left sub-path, like node 8 will add to "l" first and return into "left" as
			// previous LinkedList<TreeNode> left = helper(n.left, p, q) operation)
			if(n == p || n == q) {
				LinkedList<TreeNode> l = new LinkedList<TreeNode>();
				l.add(n);
				return l;
			} else {
				// Or it will be null as no match target node find
				return null;
			}
		} else if(left != null && right != null) {
			// If left and right both not null, it means current node
			// n is LCA(because recursively check node from bottom-up,
			// e.g first check node 8, then check node 6... so, if a
			// node has both left and right path not null, it must be
			// LCA), we can merge both paths
			finalPath.addAll(left);
			finalPath.add(n);
			Collections.reverse(right);
			finalPath.addAll(right);
			return left;
		} else if(left != null) {
			// If current node is target node and one of its subtree is
			// not null, which means LCA is current node itself.
			left.add(n);
			// As debug mode shows actually we walk through the full tree,
			// e.g we want to find path from node 2 to node 8, these two nodes both
			// on the left subtree of node 1, but we still add node 1 into LinkedList
			// left because we actually walk through the full tree, the same thing
			// happen for the right subtree, which contain node 1, 3, 9.
			// But the tricky part is we don't add node 1 into final path as
			// set up a precondition as either n == p or q. As node 1 not equal
			// to node 2 or 8, we don't add it into final path.
			// Another case not add node to final path is left or right sub-path
			// not fully constructed yet, e.g when we recursively move back from
			// node 8 to node 6, as node 6 not equal to node 2 or node 8, left
			// sub-path will not add into final path as it is not fully constructed,
			// node 6 is only a intermediate point between required node 2 and 8.
			if(n == p || n == q) {
				finalPath.addAll(left);
			}
			return left;
		} else {
			// If current node is target node and one of its subtree is
			// not null, which means LCA is current node itself.
			right.add(n);
			if(n == p || n == q) {
				finalPath.addAll(right);
			}
			return right;
		}
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
		
		// Test with node 2 to node 9 path
//		helper(s.root, s.root.left, s.root.right.left);
		// Test with node 2 to node 8 path
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
