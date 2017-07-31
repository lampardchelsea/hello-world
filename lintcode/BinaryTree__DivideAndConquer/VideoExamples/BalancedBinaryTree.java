/**
 * Refer to
 * http://www.lintcode.com/en/problem/balanced-binary-tree/
 * Given a binary tree, determine if it is height-balanced.
 * For this problem, a height-balanced binary tree is defined as a binary tree in 
 * which the depth of the two subtrees of every node never differ by more than 1.
 * Have you met this question in a real interview?
    Example
    Given binary tree A = {3,9,20,#,#,15,7}, B = {3,#,20,15,7}

    A)  3            B)    3 
       / \                  \
      9  20                 20
        /  \                / \
       15   7              15  7
 * The binary tree A is a height-balanced binary tree, but B is not.
 * 
 * Solution
 * http://www.jiuzhang.com/solution/balanced-binary-tree/
*/


// Solution 1: Traverse + Divide and Conquer (without return real height)
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
     * @param root: The root of binary tree.
     * @return: True if this Binary tree is Balanced, or false.
     */
    public boolean isBalanced(TreeNode root) {
        return maxDepth(root) != -1;
    }
    
    // Traverse + Divide and Conquer (As purely traverse will
    // return void, but here create the style as helper method
    // but with return value like Divide and Conquer, so its
    // a combination of two ways)
    public int maxDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        // Divide
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        // Merge
        // We don't need to care real depth on either left or right
        // branches, just focus on if there depth difference is bigger
        // than 1, if not a balanced subtree, return -1 instead of
        // real height, if subtree height difference bigger than 1,
        // also return -1
        if(leftDepth == -1 || rightDepth == -1 || Math.abs(leftDepth - rightDepth) > 1) {
            return -1;
        }
        // If balanced subtree, return real height(we don't need to know its
        // real height value, just compare it with -1 in final step to determine
        // whether its balanced binary tree)
        // Don't forget '+1' which means root node itself(height 1) plus
        // either left subtree height or right subtree height to get
        // final tree height
        return Math.max(leftDepth, rightDepth) + 1;
    }
    
}
