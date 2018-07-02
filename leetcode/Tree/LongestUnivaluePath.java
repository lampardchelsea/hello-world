/**
 * Refer to
 * https://leetcode.com/problems/longest-univalue-path/description/
 * Given a binary tree, find the length of the longest path where each node in the path has the same value. 
   This path may or may not pass through the root.

    Note: The length of path between two nodes is represented by the number of edges between them.

    Example 1:

    Input:

                  5
                 / \
                4   5
               / \   \
              1   1   5
    Output:

    2
    Example 2:

    Input:

                  1
                 / \
                4   5
               / \   \
              4   4   5
    Output:

    2
    Note: The given binary tree has not more than 10000 nodes. The height of the tree is not more than 1000.


 *
 * Solution
 * https://leetcode.com/problems/longest-univalue-path/discuss/108160/Clean-Java-Recursive-solution
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
    int maxLen = 0;
    public int longestUnivaluePath(TreeNode root) {
        if(root == null) {
            return 0;
        }
        helper(root);
        // Remove the duplicate 1 more node as both left / right side plus current
        // root once
        return maxLen - 1;
    }
    
    private int helper(TreeNode root) {
        if(root == null) {
            return 0;
        }
        // The order of get (left / right) value and reset them to 0
        // under certain condition is important, first get then reset
        // when match the condition
        // if(root.left == null || root.val != root.left.val) {
        //     left = 0;
        // }
        // if(root.right == null || root.val != root.right.val) {
        //     right = 0;
        // }
        int left = helper(root.left);
        int right = helper(root.right);
        // Cancel left / right full child path which connect to current
        // root node when value not match
        if(root.left == null || root.val != root.left.val) {
            left = 0;
        }
        if(root.right == null || root.val != root.right.val) {
            right = 0;
        }
        // Path should consider both left, current node, right as 3 parts
        maxLen = Math.max(maxLen, 1 + left + right);
        return Math.max(left, right) + 1;
    } 
}
