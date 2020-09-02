/**
 Refer to
 https://www.hackingnote.com/en/interview/problems/clone-binary-tree
 Problem
 For the given binary tree, return a deep copy of it.
 Example
 Given a binary tree:

    1
   / \
  2   3
 / \
4   5
 return the new binary tree with same structure and same value:

    1
   / \
  2   3
 / \
4   5
*/

// Solution 1: DFS pre-order traversal
// Refer to
// https://www.hackingnote.com/en/interview/problems/clone-binary-tree
/**
 Recursively clone the current node, then left sub-tree, then right sub-tree.
*/
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *   public int val;
 *   public TreeNode left, right;
 *   public TreeNode(int val) {
 *     this.val = val;
 *     this.left = this.right = null;
 *   }
 * }
 */
public class Solution {
    /**
     * @param root: The root of binary tree
     * @return root of new tree
     */
    public TreeNode cloneTree(TreeNode root) {
        if (root == null) {
          return null;
        }
        TreeNode newRoot = new TreeNode(root.val);
        newRoot.left = cloneTree(root.left);
        newRoot.right = cloneTree(root.right);
        return newRoot;
    }
}
