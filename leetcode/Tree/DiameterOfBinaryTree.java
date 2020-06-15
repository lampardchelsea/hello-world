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

// Re-work
// Solution 1:O(N^2) solution
// Refer to
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101120/Java-easy-to-understand-solution
/**
 
*/


// Solution 2: O(N) DFS solution
// Refer to
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101132/Java-Solution-MaxDepth
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101132/Java-Solution-MaxDepth/193803
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101132/Java-Solution-MaxDepth/309240
/**
 So although the longest path doesn't have to go through the root node, it has to pass the root node of 
 some subtree of the tree (because it has to be from one leaf node to another leaf node, otherwise we 
 can extend it for free). The longest path that passes a given node as the ROOT node is T = left_height+right_height. 
 So you just calculate T for all nodes and output the max T.
*/
class Solution {
    // Global veriable to track maximum diameter in each round
    int maxDiameter;
    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        if(root == null) {
            return 0;
        }
        helper(root);
        return maxDiameter;
    }
    
    private int helper(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        // The length of path between two nodes is represented by 
        // the number of edges between them, so no need 'left + right + 1'
        maxDiameter = Math.max(maxDiameter, left + right);
        return Math.max(left, right) + 1;
    }
}








