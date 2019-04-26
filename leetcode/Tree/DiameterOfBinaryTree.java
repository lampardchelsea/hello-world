/**
 Refer to
 https://leetcode.com/problems/diameter-of-binary-tree/
 Given a binary tree, you need to compute the length of the diameter of the tree. 
 The diameter of a binary tree is the length of the longest path between any two 
 nodes in a tree. This path may or may not pass through the root.

Example:
Given a binary tree 
          1
         / \
        2   3
       / \     
      4   5    
Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

Note: The length of path between two nodes is represented by the number of edges between them.
*/
// Solution 1:
// Refer to (1st link a bit wrong with additional '+1' in calculating diameter = 1 + leftDepth + rightDepth,
// the 2nd link correct this by diameter = 1 + leftDepth + rightDepth)
// the 3rd link for get maximum depth of current root on binary tree
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101132/Java-Solution-MaxDepth
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101120/Java-easy-to-understand-solution
// https://www.programcreek.com/2014/05/leetcode-maximum-depth-of-binary-tree-java/
/**
  For every node, length of longest path which pass it = MaxDepth of its left subtree + MaxDepth of its right subtree,
  and we just need to scan every node by recursive method
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
    int maxDiameter = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        helper(root);
        return maxDiameter;
    }
    
    private int helper(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int leftDepth = helper(root.left);
        int rightDepth = helper(root.right);
        maxDiameter = Math.max(maxDiameter, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
