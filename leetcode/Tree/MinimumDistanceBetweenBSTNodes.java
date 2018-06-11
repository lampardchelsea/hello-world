/**
 * Refer to
 * https://leetcode.com/problems/minimum-distance-between-bst-nodes/description/
 * Given a Binary Search Tree (BST) with the root node root, return the minimum difference between the values 
   of any two different nodes in the tree.

    Example :

    Input: root = [4,2,6,1,3,null,null]
    Output: 1
    Explanation:
    Note that root is a TreeNode object, not an array.

    The given tree [4,2,6,1,3,null,null] is represented by the following diagram:

              4
            /   \
          2      6
         / \    
        1   3  

    while the minimum difference in this tree is 1, it occurs between node 1 and node 2, also between node 3 and node 2.
    Note:

    The size of the BST will be between 2 and 100.
    The BST is always valid, each node's value is an integer, and each node's value is different.
*
* Solution
* leetcode.com/problems/minimum-distance-between-bst-nodes/discuss/114827/JAVA-in-order-one-pass-O(1)-extra-space/115671
* The pre is short for "previous". Since it's BST, inorder traversal will give us 
  the correct order. So we only need to compare the difference in every pair of 
  adjacent nodes' values in the inorder traversal.Since we can only use the pre 
  if it's not the first node we visit. If pre is null we know its value hasn't 
  been set so it's the first node we visit. That's the reason to use Integer here.
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
    /**
     Refer to
     leetcode.com/problems/minimum-distance-between-bst-nodes/discuss/114827/JAVA-in-order-one-pass-O(1)-extra-space/115671
     The pre is short for "previous". Since it's BST, inorder traversal will give us 
     the correct order. So we only need to compare the difference in every pair of 
     adjacent nodes' values in the inorder traversal.Since we can only use the pre 
     if it's not the first node we visit. If pre is null we know its value hasn't 
     been set so it's the first node we visit. That's the reason to use Integer here.
    */
    int min = Integer.MAX_VALUE;
    Integer pre = null;
    public int minDiffInBST(TreeNode root) {
        if(root == null) {
            return 0;
        }
        helper(root);
        return min;
    }
    
    private void helper(TreeNode node) {
        if(node == null) {
            return;
        }
        helper(node.left);
        if(pre != null) {
            min = Math.min(min, node.val - pre);
        }
        pre = node.val;
        helper(node.right);
    }
}
