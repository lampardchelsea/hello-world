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
