import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Refer to
 * https://segmentfault.com/a/1190000003797291
 * Given a non-empty binary search tree and a target value, find k values in 
 * the BST that are closest to the target.
 * 
 * Note: Given target value is a floating point. You may assume k is always valid, 
 * that is: k ≤ total nodes. You are guaranteed to have only one unique set of k 
 * values in the BST that are closest to the target. Follow up: Assume that the BST 
 * is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?
 * 
 * Hint:
 * Consider implement these two helper functions: 
 * getPredecessor(N), which returns the next smaller node to N. 
 * getSuccessor(N), which returns the next larger node to N.
 * 
 * 
 * Solution
 * http://www.cnblogs.com/grandyang/p/5247398.html
 * https://discuss.leetcode.com/topic/22940/ac-clean-java-solution-using-two-stacks
 * https://discuss.leetcode.com/topic/30081/java-in-order-traversal-1ms-solution
 */
public class ClosestBinarySearchTreeValueII {
private class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
	// Solution 1: Two Stacks + Inorder traverse
	/**
	 * Refer to
	 * https://discuss.leetcode.com/topic/22940/ac-clean-java-solution-using-two-stacks
	 * The idea is to compare the predecessors and successors of the closest node to the target, 
	 * we can use two stacks to track the predecessors and successors, then like what we do in 
	 * merge sort, we compare and pick the closest one to the target and put it to the result list.
	 * As we know, inorder traversal gives us sorted predecessors, whereas reverse-inorder 
	 * traversal gives us sorted successors.
	 * We can use iterative inorder traversal rather than recursion, but to keep the code clean, 
	 * here is the recursion version.
	 * 
	 * Time Complexity O(n + logn + k)
	 * A full inorder traversal takes O(n) time already, plus binary search and pick, 
	 * total time would be O(n + logn + k).
	 */
	public List<Integer> closestKValues(TreeNode root, double target, int k) {
		List<Integer> result = new ArrayList<Integer>();
		Stack<TreeNode> predecessor = new Stack<TreeNode>();
		Stack<TreeNode> successor = new Stack<TreeNode>();
		inorderHelper(false, root, target, predecessor);
		inorderHelper(true, root, target, successor);
		merge(k, predecessor, successor, target, result);
		return result;
	}
	
	private void inorderHelper(boolean reverse, TreeNode node, double target, Stack<TreeNode> stack) {
	    if(node == null) {
	    	return;
	    }
	    // Tricky flag as 'reverse' to control if reverse travel on inorder
	    // to create predecessor of target (not reverse) or create successor
	    // of target (reverse)
	    inorderHelper(reverse, reverse ? node.right : node.left, target, stack);
	    // Do stuff
	    // Terminate early to stop traverse full tree
	    // If reverse = true, try to find successor (node.right -> node -> node.left), if val < target, terminate
	    // If reverse = false, try to find predecessor (node.left -> node -> node.right), if val > target, terminate
	    if(reverse && node.val <= target || !reverse && node.val >= target) {
	    	return;
	    }
	    stack.push(node);
	    inorderHelper(reverse, reverse ? node.left : node.right, target, stack);
	}
	
	private void merge(int k, Stack<TreeNode> predecessor, Stack<TreeNode> successor, double target, List<Integer> result) {
		while(k-- > 0) {
			if(predecessor.isEmpty()) {
				result.add(successor.pop().val);
			} else if(successor.isEmpty()) {
				result.add(predecessor.pop().val);
			} else {
				if(Math.abs(predecessor.peek().val - target) < Math.abs(successor.peek().val - target)) {
					result.add(predecessor.pop().val);
				} else {
					result.add(successor.pop().val);
				}
			}
		}
	}
	
	// Solution 2: In-order traverse DFS
	// Refer to
	// https://discuss.leetcode.com/topic/30081/java-in-order-traversal-1ms-solution
	// http://www.cnblogs.com/grandyang/p/5247398.html
	/**
	 * 还有一种解法是直接在中序遍历的过程中完成比较，当遍历到一个节点时，如果此时结果数组不到k个，
	 * 我们直接将此节点值加入res中，如果该节点值和目标值的差值的绝对值小于res的首元素和目标值差值
	 * 的绝对值，说明当前值更靠近目标值，则将首元素删除，末尾加上当前节点值，反之的话说明当前值比res中
	 * 所有的值都更偏离目标值，由于中序遍历的特性，之后的值会更加的遍历，所以此时直接返回最终结果即可
	 */
	public List<Integer> closestKValues2(TreeNode root, double target, int k) {
	    LinkedList<Integer> result = new LinkedList<Integer>();
	    inorderHelper2(root, target, k, result);
	    return result;
	}
	
	private void inorderHelper2(TreeNode node, double target, int k, LinkedList<Integer> result) {
		if(node == null) {
			return;
		}
		inorderHelper2(node.left, target, k, result);
		// Do stuff			
		// If the result size equal to k but still new node.val coming,
		// need to update to the last position and remove the first one
		// based on comparison
		if(result.size() < k) {
		    result.add(node.val);	
		} else {
			if(Math.abs(result.getFirst() - target) > Math.abs(node.val - target)) {
				result.removeFirst();
				result.add(node.val);
			}
		}
		inorderHelper2(node.right, target, k, result);
	}
	
	// Solution 3: In-order traverse Iterative
	// Refer to
	// https://discuss.leetcode.com/topic/30081/java-in-order-traversal-1ms-solution/5
	// http://www.cnblogs.com/grandyang/p/5247398.html
	// 下面这种方法是上面那种方法的迭代写法，原理一模一样
	/**
	 * Refer to
	 * Inorder traverse template
	 * hello-world/lintcode/BinaryTree__DivideAndConquer/VideoExamples/BinaryTreeInorderTraversal.java
	 * public List<Integer> inorderTraversal(TreeNode root) {
	 *     List<Integer> list = new ArrayList<Integer>();
	 *     if(root == null) {
	 *         return result;
	 *     }
	 *     Stack<TreeNode> stack = new Stack<TreeNode>();
	 *     while(root != null || !stack.isEmpty()) {
	 *         while(root != null) {
	 *             stack.push(root);
	 *             root = root.left;
	 *         }
	 *         root = stack.pop();
	 *         list.add(root.val);
	 *         root = root.right;
	 *     }
	 *     return list;
	 * }
	 * 
	 */
	public List<Integer> closestKValues3(TreeNode root, double target, int k) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		Stack<TreeNode> stack = new Stack<TreeNode>();
		while(root != null || !stack.isEmpty()) {
			while(root != null) {
				stack.push(root);
				root = root.left;
			}
			root = stack.pop();
			// Modify add to result logic based on template
			if(result.size() < k) {
				result.add(root.val);
			} else {
				if(Math.abs(root.val - target) < Math.abs(result.getFirst() - target)) {
					result.removeFirst();
					result.add(root.val);
				}
			}
			root = root.right;
		}
		return result;
	}
	
	
	// Solution 4: Priority Queue
	
	
	
	public static void main(String[] args) {
        /**
         *           4
         *          / \
         *         2   5 
         *        / \
         *       1   3         
         */
		ClosestBinarySearchTreeValueII c = new ClosestBinarySearchTreeValueII();
		TreeNode one = c.new TreeNode(1);
		TreeNode two = c.new TreeNode(2);
		TreeNode three = c.new TreeNode(3);
		TreeNode four = c.new TreeNode(4);
		TreeNode five = c.new TreeNode(5);
		four.left = two;
		four.right = five;
		two.left = one;
		two.right = three;
		double target = 2.15;
		int k = 3;
		List<Integer> result = c.closestKValues3(four, target, k);
		for(Integer a : result) {
			System.out.print(a + " ");
		}
	}
}
