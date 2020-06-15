/**
 * Given a binary tree, determine if it is height-balanced.
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth 
 * of the two subtrees of every node never differ by more than 1.
 * 
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


// Re-work
// Solution 1: Two Pass DFS (Time Complexity O(NlogN))
// Refer to
// https://leetcode.com/problems/balanced-binary-tree/discuss/35691/The-bottom-up-O(N)-solution-would-be-better
/**
 For the current node root, calling depth() for its left and right children actually has to access all of its children, 
 thus the complexity is O(N). We do this for each node in the tree, so the overall complexity of isBalanced will be O(N^2). 
 This is a top down approach.
*/
// https://leetcode.com/problems/balanced-binary-tree/discuss/35691/The-bottom-up-O(N)-solution-would-be-better/33913
/**
 O(NlogN) is only true for the best case. The first approach is O(N^2) for the worst case,
 that is the tree has N nodes and N levels, it take O(N^2) to return false.
*/
class Solution {
    public boolean isBalanced(TreeNode root) {
        if(root == null) {
            return true;
        }
        int lh = height(root.left);
        int rh = height(root.right);
        if(Math.abs(lh - rh) <= 1 && isBalanced(root.left) && isBalanced(root.right)) {
            return true;
        }
        return false;
    }
    
    private int height(TreeNode node) {
        if(node == null) {
            return -1;
        }
        return Math.max(height(node.left), height(node.right)) + 1;
    }
}

// Solution 2: One Pass DFS (Time Complexity O(N))
// Refer to
// https://leetcode.com/problems/balanced-binary-tree/discuss/35691/The-bottom-up-O(N)-solution-would-be-better
/**
 The second method is based on DFS. Instead of calling depth() explicitly for each child node, we return 
 the height of the current node in DFS recursion. When the sub tree of the current node (inclusive) is 
 balanced, the function dfsHeight() returns a non-negative value as the height. Otherwise -1 is returned. 
 According to the leftHeight and rightHeight of the two children, the parent node could check if the sub tree
 is balanced, and decides its return value.
*/
// https://leetcode.com/problems/balanced-binary-tree/discuss/35691/The-bottom-up-O(N)-solution-would-be-better/198436
class Solution {
    public boolean isBalanced(TreeNode root) {
        if(root == null) {
            return true;
        }
        return helper(root) != -1;
    }
    
    private int helper(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        if(left == -1 || right == -1 || Math.abs(left - right) > 1) {
            return -1;
        }
        return Math.max(left, right) + 1;
    }
}




