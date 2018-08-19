/**
 * Refer to
 * https://leetcode.com/problems/second-minimum-node-in-a-binary-tree/description/
 * Given a non-empty special binary tree consisting of nodes with the non-negative value, 
   where each node in this tree has exactly two or zero sub-node. If the node has two 
   sub-nodes, then this node's value is the smaller value among its two sub-nodes.

    Given such a binary tree, you need to output the second minimum value in the set made 
    of all the nodes' value in the whole tree.

    If no such second minimum value exists, output -1 instead.

    Example 1:
    Input: 
        2
       / \
      2   5
         / \
        5   7

    Output: 5
    Explanation: The smallest value is 2, the second smallest value is 5.
    Example 2:
    Input: 
        2
       / \
      2   2

    Output: -1
    Explanation: The smallest value is 2, but there isn't any second smallest value.
 *  
 * leetcode.com/problems/second-minimum-node-in-a-binary-tree/discuss/107158/Java-divide-and-conquer-solution/109376
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
    public int findSecondMinimumValue(TreeNode root) {
        return helper(root, root.val);
    }
    
    // Caution: NOT BST, just binary tree, cannot apply same way as 'Kth Smallest Element in a BST'
    private int helper(TreeNode root, int min) {
        if(root == null) {
            return -1;
        }
        if(root.val > min) {
            return root.val;
        }
        int leftMin = helper(root.left, min);
        int rightMin = helper(root.right, min);
        // If the node has two sub-nodes, then this node's value is the smaller value among its two sub-nodes
        // the test case as root = 2, left = 1, right = 1 not applicable here
        return (leftMin == -1 || rightMin == -1) ? Math.max(leftMin,rightMin) : Math.min(leftMin,rightMin);
    }
}
