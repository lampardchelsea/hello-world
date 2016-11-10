/**
 * Invert a binary tree.

     4
   /   \
  2     7
 / \   / \
1   3 6   9
to
     4
   /   \
  7     2
 / \   / \
9   6 3   1

 * Trivia:
 * This problem was inspired by this original tweet by Max Howell:
 * Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a whiteboard so fuck off.
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

// Solution 1: Buttom-Top traverse invert
/**
		       1                  1                  1                 1
	             /   \              /   \              /   \             /   \
	  	    2     3            3     2            3     2           3     2
	 	   / \   /    -->     /     / \   -->      \   / \   -->     \   / \ 
		  6   5 9            9     6   5            9 5   6           9  5  6
	 	 /                        /                      /                   \
		8                        8                      8                     8
*/
public class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root == null) {
            return null;
        }
        
        if(root.left != null) {
            invertTree(root.left);
        }
        
        if(root.right != null) {
            invertTree(root.right);
        }
        
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        
        return root;
    }
}

// Solution 2: Top-Bottom traverse invert
public class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root == null) {
            return null;
        }
        
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        
        if(root.left != null) {
            invertTree(root.left);
        }
        
        if(root.right != null) {
            invertTree(root.right);
        }
        
        return root;
    }
}
