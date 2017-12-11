/**
 * Refer to
 * 
 * Given a binary tree where all the right nodes are either leaf nodes with a sibling 
   (a left node that shares the same parent node) or empty, flip it upside down and turn 
   it into a tree where the original right nodes turned into left leaf nodes. Return the new root.

    For example:
    Given a binary tree {1,2,3,4,5},

        1

       / \

      2   3

     / \

    4   5

    return the root of the binary tree [4,5,2,#,#,3,1].

       4

      / \

     5   2

        / \

       3   1  
 *
 *
 * Solution
 * https://discuss.leetcode.com/topic/40924/java-recursive-o-logn-space-and-iterative-solutions-o-1-space-with-explanation-and-figure/2
 * http://blog.csdn.net/whuwangyi/article/details/43186045
 * http://www.cnblogs.com/grandyang/p/5172838.html
*/
package test;

public class BinaryTreeUpsideDown {
	private class TreeNode {
		TreeNode left;
		TreeNode right;
		int val;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
	// Recursive Solution
	// Style 1
	// 这题有一个重要的限制就是，整个数的任何一个右孩子都不会再生枝节，
	// 基本就是一个梳子的形状。对于树类型的题目，首先可以想到一种递归的思路: 
	// 把左子树继续颠倒，颠倒完后，原来的那个左孩子的左右孩子指针分别指向原来的根节点以及原来的右兄弟节点即可。
	public TreeNode upsideDownBinaryTree(TreeNode root) {
		if(root == null) {
			return null;
		}
		TreeNode left = root.left;
		TreeNode right = root.right;
		TreeNode parent = root;
		if(left != null) {
			TreeNode newRoot = upsideDownBinaryTree(left);
			left.left = right;
			left.right = parent;
			// Do we need to clear the left and right child of parent ?
			// Yes, otherwise, the stale connection remains.
			parent.left = null;
			parent.right = null;
			return newRoot;			
		}
        return root;
	}
	
	// Style 2
	public TreeNode upsideDownBinaryTree2(TreeNode root) {
		if(root == null || root.left == null) {
			return root;
		}
		TreeNode left = root.left;
		TreeNode right = root.right;
		TreeNode parent = root;
		//if(left != null) {
			TreeNode newRoot = upsideDownBinaryTree(left);
			left.left = right;
			left.right = parent;
			parent.left = null;
			parent.right = null;
			return newRoot;			
		//}
        //return root;
	}
	
	// Iterative Solution
	// Style 1
	// 第二个思路是直接用迭代代替递归，做起来也不麻烦，并且效率会更高，因为省去了递归所用的栈空间。
	// 迭代的方法，和递归方法相反的时，这个是从上往下开始翻转，直至翻转到最左子节点
	/**
	 *     prev ->    1
	 *              /   \
	 *   curr ->   2 --- 3  <- temp
	 *           /   \
	 *  next -> 4 --- 5      
	 */
	public TreeNode upsideDownBinaryTree3(TreeNode root) {
		TreeNode curr = root;
		TreeNode next = null;
		TreeNode temp = null;
		TreeNode prev = null;
		while(curr != null) {
			next = curr.left;
			// swapping nodes now, need temp to keep the previous right child
			curr.left = temp;
			temp = curr.right;
			curr.right = prev;
			// update for next iteration
			prev = curr;
			curr = next;
		}
		return prev;
	}
	
	public static void main(String[] args) {
		BinaryTreeUpsideDown b = new BinaryTreeUpsideDown();
		TreeNode one = b.new TreeNode(1);
		TreeNode two = b.new TreeNode(2);
		TreeNode three = b.new TreeNode(3);
		TreeNode four = b.new TreeNode(4);
		TreeNode five = b.new TreeNode(5);
		one.left = two;
		one.right = three;
		two.left = four;
		two.right = five;
		TreeNode result = b.upsideDownBinaryTree3(one);
		System.out.println(result.val);
	}
}
