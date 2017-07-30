/**
 * Refer to
 * http://www.lintcode.com/en/problem/binary-tree-paths/
 * Given a binary tree, return all root-to-leaf paths.
 * Have you met this question in a real interview?
 * Example
 * Given the following binary tree:
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


// Solution 1: Traverse
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param root the root of the binary tree
     * @return all root-to-leaf paths
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<String>();
        if(root == null) {
            return result;
        }
        helper(root, String.valueOf(root.val), result);
        return result;
    }
    
    // Use 'path' to record each step as basic idea from Traverse method,
    // also update result
    private void helper(TreeNode root, String path, List<String> result) {
        // Base case 1
        if(root == null) {
            return;
        }
        // Base case 2
        // Handle only 'root' but no 'child' case, still
        // need to add root's val onto path, otherwise
        // as 'root' still exist but not add onto path
        // issue
        if(root.left == null && root.right == null) {
            result.add(path);
            return;
        }
        // Divide and merge(for 'path' and result already merge here)
        // Note: The order here must be 'path' before 'root.left.val' or 'root.right.val'
        // which exactly reverse than the order in Divide and Conquer way, which is
        // 'path' after 'root.left.val' or 'root.right.val'
        // Refer to
        // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/BinaryTreePaths.java
        if(root.left != null) {
            helper(root.left, path + "->" + String.valueOf(root.left.val), result);            
        }
        if(root.right != null) {
            helper(root.right, path + "->" + String.valueOf(root.right.val), result);            
        }
    }
}



// Solution 2: Divide And Conquer
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param root the root of the binary tree
     * @return all root-to-leaf paths
     */
    public List<String> binaryTreePaths(TreeNode root) {
        // Base case
        List<String> paths = new ArrayList<String>();
        if(root == null) {
            return paths;
        }
        // Base case, root is leaf
        if(root.left == null && root.right == null) {
            paths.add("" + root.val);
        }
        
        // Divide
        List<String> leftPaths = binaryTreePaths(root.left);
        List<String> rightPaths = binaryTreePaths(root.right);
        // Merge
        for(String path : leftPaths) {
            // Why not (path + "->" + root.val) ?
            // Must think about the sequence of how the path is build up,
            // e.g start from '1', '1' as root value must put on path
            // as first item, then we add "->" and continue add '2' or '3'
            // which calculate by leftPaths and rightPaths
            paths.add(root.val + "->" + path);
        }
        for(String path : rightPaths) {
            paths.add(root.val + "->" + path);
        }
        return paths;
    }
}


