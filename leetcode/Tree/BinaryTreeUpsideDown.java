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


// Re-work
// Link two explain together to find the discipline
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/Document/Binary_Tree_Upside_Down.pdf
// http://rainykat.blogspot.com/2017/01/leetcodelinkedin-156-binary-tree-upside.html
// Solution 1: Recursive


// Solution 2: Iterative
/**
 Best explain example:
 Suppose that we are given a binary tree:
    
    1
   / \
  2   3
 / \
4   5
-------------------------------------------------------------------------
Initialization:     prev = NULL, cur = 1, next = NULL, lastRight = NULL

      NULL (prev)
     /       \
    1 (curr)  NULL (lastRight)
   /       \
  2 (next)  3
 / \
4   5
-------------------------------------------------------------------------
1st Iteration:      store 2 in next
		    let 1 left point to NULL (lastRight)
		    store 3 in lastRight
		    let 1 right point to NULL (prev)
		    move prev to 1 (cur)
		    move cur to 2 (next)
Binary Tree:            

    1

  2   3
 / \
4   5
Data Structure:     prev = 1, cur = 2, next = 2, lastRight = 3
-------------------------------------------------------------------------
2nd Iteration:      store 4 in next
		    let 2 left point to 3 (lastRight)
		    store 5 in lastRight
		    let 2 right point to 1 (prev)
		    move prev to 2 (cur)
		    move cur to 4 (next)
Binary Tree:            

    1
   /
  2 - 3

4   5
Data Structure:     prev = 2, cur = 4, next = 4, lastRight = 5
-------------------------------------------------------------------------
3rd Iteration:      store NULL in next
		    let 4 left point to 5 (lastRight)
		    store NULL in lastRight
		    let 4 right point to 2 (prev)
		    move prev to 4 (cur)
		    move cur to NULL (next)
Binary Tree:            

    1
   /
  2 - 3
 / 
4 - 5
Data Structure:     prev = 4, cur = NULL, next = NULL, lastRight = NULL
return prev = 4
*/
public class Solution {
    /**
     * @param root: the root of binary tree
     * @return: new root
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if(root == null) {
            return null;
        }
        TreeNode prev = null;
        TreeNode curr = root;
        TreeNode next = null;
        TreeNode lastRight = null;
        while(curr != null) {
            next = curr.left;
            curr.left = lastRight;
            lastRight = curr.right;
            curr.right = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}

