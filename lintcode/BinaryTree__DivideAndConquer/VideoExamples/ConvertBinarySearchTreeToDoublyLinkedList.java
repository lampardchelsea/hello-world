import java.util.Stack;

/**
 * Refer to
 * http://www.lintcode.com/en/problem/convert-binary-search-tree-to-doubly-linked-list/
 * Convert a binary search tree to doubly linked list with in-order traversal.
 * Have you met this question in a real interview?
	Example	
	Given a binary search tree:
	
	    4
	   / \
	  2   5
	 / \
	1   3
	
	return 1<->2<->3<->4<->5
 * 
 * 
 * Solution
 * http://demo.aosustudio.com/96F7C9DA60ED8D595C0BEACE369D5364.AHtml
 *
 */
public class ConvertBinarySearchTreeToDoublyLinkedList {
    /**
     * @param root: The root of tree
     * @return: the head of doubly list node
     */
	
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
	// Definition for Doubly-ListNode
	private class DoublyListNode {
		int val;
		DoublyListNode next, prev;
		DoublyListNode(int val) {
		    this.val = val;
		    this.next = this.prev = null;
		}
    }
	
	
	public DoublyListNode bstToDoublyList(TreeNode root) {
		if(root == null) {
			return null;
		}
		
		Stack<TreeNode> stack = new Stack<TreeNode>();
		DoublyListNode head = null;
		DoublyListNode prev = null;
		while(!stack.isEmpty() || root != null) {
			while(root != null) {
				stack.push(root);
				root = root.left;
			}
			root = stack.pop();
			
			// Build doubly linked list with current given 'root',
			// which is from inorder traverse
			DoublyListNode node = new DoublyListNode(root.val);
			if(head == null) {
				head = node;
			}
			node.prev = prev;
			if(prev != null) {
				prev.next = node;
			}
		    prev = node;
			
			root = root.right;
		}
		return head;
	}  
	
	
}
