/**
 * Refer to
 * www.lintcode.com/en/problem/invert-binary-tree/
 * 
    Invert a binary tree.
    Have you met this question in a real interview ?
    Example

      1         1
     / \       / \
    2   3  => 3   2
       /       \
      4         4 
 *
 * Solution
 * http://www.jiuzhang.com/solutions/invert-binary-tree/?source=zhmhw
*/
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
     * @param root: a TreeNode, the root of the binary tree
     * @return: nothing
     */
    public void invertBinaryTree(TreeNode root) {
        // Base case
        if(root == null) {
            return;
        }
        // Divide
        invertBinaryTree(root.left);
        invertBinaryTree(root.right);
        // Conquer
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
    }
}
