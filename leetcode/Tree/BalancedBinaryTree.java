/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public boolean isBalanced(TreeNode root) {
        // If tree is empty(no root node), return true
        if(root == null) {
            return true;
        }
        
        // Computer the left and right subtree height
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        
        // Return true if difference between heights is not more than 1 and left 
        // and right subtrees are balanced, otherwise return false.
        if(Math.abs(leftHeight - rightHeight) <= 1 && isBalanced(root.left) && isBalanced(root.right)) {
            return true;
        }
        
        return false;
    }

    // The depth of a node is the number of edges from the root to the node
    // E.g Only root node bst height is 0, if have a child, then 1 edge represent height
    // of this child is 1
    public int height(TreeNode x) {
        // Base case tree is empty, the height should set to -1
        // http://stackoverflow.com/questions/2209777/what-is-the-definition-for-the-height-of-a-tree
        if(x == null) {
            return -1;
        }
        
        // If tree is not empty(at least contain a root node), then the tree height is maximum     
        return Math.max(height(x.left), height(x.right)) + 1;
    }
}




/**
 * Full example with test cases
 * 
 * For this problem, a height-balanced binary tree is defined as a binary tree 
 * in which the depth of the two subtrees of every node never differ by more than 1.
 * 
 * https://segmentfault.com/a/1190000003509063
 * The easiest way is computer both heights of left child tree and right child tree,
 * if difference larger than 1, then not a height-balanced binary tree, in recursively
 * calculate child tree height need to start from leaves which have no more children
 * nodes, and trace back level after level.
 * E.g in current example, the left subtree start from 2, right subtree start from 3.
 * The height of left subtree is 2, as 2--4--5 number of edges is 2, height of right
 * subtree is 0, as 3 is the only node as leave node and no edge.
 * 
 * Note: The height of a node
 * https://www.cs.cmu.edu/~adamchik/15-121/lectures/Trees/trees.html
 */
public class BlancedBinrayTree {
	private TreeNode root;
	
	private class TreeNode {
		private int val;
		private TreeNode left;
		private TreeNode right;
		
		public TreeNode(int x) {
			val = x;
		}
	}
	
	public boolean isBalanced(TreeNode root) {
		if(root == null) {
			return true;
		}
		
		int leftHeight = height(root.left);
		int rightHeight = height(root.right);
		
		if(Math.abs(leftHeight - rightHeight) <= 1 && isBalanced(root.left) && isBalanced(root.right)) {
			return true;
		}
		
		return false;
	} 
	
	public int height(TreeNode x) {
		if(x == null) {
			return -1;
		}
		
		return Math.max(height(x.left), height(x.right)) + 1;
	}
	
	public static void main(String[] args) {
		BlancedBinrayTree tree = new BlancedBinrayTree();
		
		/**
		 * Construct a binary tree
		 *              1
		 *             / \
		 *            2   3
		 *             \
		 *              4
		 *             /
		 *            5   
		 */
		
		tree.root = tree.new TreeNode(1);
		tree.root.left = tree.new TreeNode(2);
		tree.root.right = tree.new TreeNode(3);
		tree.root.left.right = tree.new TreeNode(4);
		tree.root.left.right.left = tree.new TreeNode(5);
		
		boolean isBalanced = tree.isBalanced(tree.root);
		System.out.println(isBalanced);
	}
	
}





