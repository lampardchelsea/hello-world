/**
 * Refer to
 * https://leetcode.com/problems/recover-binary-search-tree/description/
 * Two elements of a binary search tree (BST) are swapped by mistake.
    Recover the tree without changing its structure.

    Note:
    A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?
 * 
 * Solution
 * https://discuss.leetcode.com/topic/3988/no-fancy-algorithm-just-simple-and-powerful-in-order-traversal/2
 * https://www.youtube.com/watch?v=2rsGbHnIDV0
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/Document/bst_inorder_traverse.pdf
 * About the binary search tree inorder traverse
*/


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    TreeNode firstToExchange = null;
    TreeNode secondToExchange = null;
    //TreeNode prev = new TreeNode(Integer.MIN_VALUE);
    TreeNode prev = null;
    public void recoverTree(TreeNode root) {
        if(root == null) {
            return;
        }
        // Start inorder DFS traverse
        traverse(root);
        // Not exchange TreeNode but its value only
        int temp = firstToExchange.val;
        firstToExchange.val = secondToExchange.val;
        secondToExchange.val = temp;
    }
    
    private void traverse(TreeNode root) {
        if(root == null) {
            return;
        }
        traverse(root.left);
        if(prev != null && prev.val >= root.val) {
            if(firstToExchange == null) {
                firstToExchange = prev;
            }
            // Becareful, the exchanged node here should be 'root',
            // not 'prev', if given inorder traverse as 6, 3, 4, 5, 2
            // corresponding to 2
            secondToExchange = root;
        }
        prev = root;
        traverse(root.right);
    }
}
