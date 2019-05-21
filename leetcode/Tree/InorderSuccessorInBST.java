/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5306162.html
 * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
 * Note: If the given node has no in-order successor in the tree, return null.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/33228/java-5ms-short-code-with-explanations
 * https://www.youtube.com/watch?v=PyK4nl-MfNs
 * https://discuss.leetcode.com/topic/25076/share-my-java-recursive-solution
 * https://discuss.leetcode.com/topic/25076/share-my-java-recursive-solution/14
*/

// Solution 1: Iterative
// Refer to
// https://discuss.leetcode.com/topic/33228/java-5ms-short-code-with-explanations
/**
 * The idea is to compare root's value with p's value if root is not null, and consider the 
   following two cases:
   root.val > p.val. In this case, root can be a possible answer, so we store the root node first 
   and call it res. However, we don't know if there is anymore node on root's left that is larger 
   than p.val. So we move root to its left and check again.
   root.val <= p.val. In this case, root cannot be p's inorder successor, neither can root's left 
   child. So we only need to consider root's right child, thus we move root to its right and check 
   again.
   We continuously move root until exhausted. To this point, we only need to return the res in case 1.
*/
public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
    TreeNode result = null;
    while(root != null) {
        if(root.val > p.val) {
            result = root;
            root = root.left;
        } else {
            root = root.right;
        }
    }
    return result;
}

// Solution 2: Recursive
// Refer to
// https://discuss.leetcode.com/topic/25076/share-my-java-recursive-solution
// https://discuss.leetcode.com/topic/25076/share-my-java-recursive-solution/14
public TreeNode inorderSuccessor2(TreeNode root, TreeNode p) {
    	private class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
	/**
	 *        4
         *      /   \
	 *     2     6
	 *    / \   /
	 *   1   3 5
	 * Explanation: return (left != null) ? left : root;
	 * let's take the successor for example, basically we always want to find p's closest node 
	   (in inorder traversal) and the node's value is larger than p's value, right? That node 
	   can either be p's parent or the smallest node in p' right branch.
	   When the code runs into the else block (root.val > p.val), that means the current 
	   root is either p's parent or a node in p's right branch.
	   If it's p's parent node, there are two scenarios: 
	   1. p doesn't have right child, in this case, the recursion will eventually return null, 
	      so p's parent is the successor; (e.g find root = 4 as parent of p = 3)
	   2. p has right child, then the recursion will return the smallest node in the right 
	      sub tree, and that will be the answer. (e.g find root = 4 as parent of p = 2)
	   If it's p's right child, there are two scenarios: 
	   1. the right child has left sub tree, eventually the smallest node from the left sub 
	      tree will be the answer; (e.g find root = 6 as right child of p = 4)
	   2. the right child has no left sub tree, the recursion will return null, then the right 
	      child (root) is our answer. (e.g find root = 3 as right child of p = 2)
	*/
	public TreeNode successor(TreeNode root, TreeNode p) {
		if(root == null) {
			return null;
		}
		if(root.val <= p.val) {
			return successor(root.right, p);
		} else {
			TreeNode left = successor(root.left, p);
			return (left != null) ? left : root;
		}
	}
	
	public static void main(String[] args) {
		InorderSuccessorInBST i = new InorderSuccessorInBST();
		/**
		 *        4
		 *      /   \
		 *     2     6
		 *    / \   /
		 *   1   3 5
		 */
		TreeNode one = i.new TreeNode(1);
		TreeNode two = i.new TreeNode(2);
		TreeNode three = i.new TreeNode(3);
		TreeNode four = i.new TreeNode(4);
		TreeNode five = i.new TreeNode(5);
		TreeNode six = i.new TreeNode(6);
		four.left = two;
		four.right = six;
		two.left = one;
		two.right = three;
		six.left = five;
		TreeNode result = i.successor(four, three);
		System.out.println(result.val);
   }    
}



