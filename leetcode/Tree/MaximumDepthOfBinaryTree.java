/**
 * Given a binary tree, find its maximum depth.
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 * 
 * Refer to 
 * http://algs4.cs.princeton.edu/32bst/BST.java.html (height)
*/
// Solution 1:
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
    public int maxDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }
}

// Solution 2:
// Refer to http://www.jiuzhang.com/solutions/maximum-depth-of-binary-tree/
public class Solution {
    int depth = 0;
    public int maxDepth(TreeNode root) {
       helper(root, 1);
       return depth; 
    }
    
    public void helper(TreeNode x, int currentDepth) {
       if(x == null) {
          return;
       }
       
       if(currentDepth > depth) {
          depth = currentDepth;
       }
       
       helper(x.left, currentDepth + 1);
       helper(x.right, currentDepth + 1);
    }
}

// Solution 3:
// Same way as how we handle Minimum Depth Of Binary Tree
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
    public int maxDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return helper(root);
    }
    
    private int helper(TreeNode root) {
        if(root == null) {
            return Integer.MIN_VALUE;
        }
        if(root.left == null && root.right == null) {
            return 1;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        return Math.max(left, right) + 1;
    }
}
