import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * http://www.lintcode.com/en/problem/binary-tree-paths/
 * Given a binary tree, return all root-to-leaf paths.
 * Have you met this question in a real interview?
	Example
	Given the following binary tree:
	   1
	 /   \
	2     3
	 \
	  5
	
	All root-to-leaf paths are:
	[
	  "1->2->5",
	  "1->3"
	]
 *
 * Solution
 * http://www.jiuzhang.com/solutions/binary-tree-paths/
 */
public class BinaryTreePaths {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<String>();
        if(root == null) {
            return paths;
        }
        helper(root, String.valueOf(root.val), paths);
        return paths;
    }
    
    
    /**
     * Start with path = "1"
     * "1"
     * "1 -> 2" (root = 1, add root.left.val = 2)
     * "1 -> 2 -> 4" (root = 2, add root.left.val = 4)
     * "1 -> 2 -> 5" (root = 2, add root.right.val = 5)
     * "1 -> 3" (root = 1, add root.right.val = 3)
     */
    public void helper(TreeNode root, String path, List<String> paths) {
        // Base case
        if(root == null) {
            return;
        }
        if(root.left == null && root.right == null) {
            paths.add("" + path);
        }
        // Divide and merge
        // Note: The order here must be 'path' before 'root.left.val' or 'root.right.val'
        // which exactly reverse than the order in Divide and Conquer way, which is
        // 'path' after 'root.left.val' or 'root.right.val'
        if(root.left != null) {
            helper(root.left, path + "->" + root.left.val, paths);            
        }
        if(root.right != null) {
            helper(root.right, path + "->" + root.right.val, paths);   
        }
    }
    
    public static void main(String[] args) {
    	/**
    	 *       1
    	 *      / \
    	 *     2   3
    	 *    / \
    	 *   4   5
    	 */
    	BinaryTreePaths b = new BinaryTreePaths();
    	TreeNode one = b.new TreeNode(1);
    	TreeNode two = b.new TreeNode(2);
    	TreeNode three = b.new TreeNode(3);
    	TreeNode four = b.new TreeNode(4);
    	TreeNode five = b.new TreeNode(5);
    	one.left = two;
    	one.right = three;
    	two.left = four;
    	two.right = five;
    	List<String> result = b.binaryTreePaths(one);
    	for(String s : result) {
    		System.out.println(s);
    	}
    }
    
}
