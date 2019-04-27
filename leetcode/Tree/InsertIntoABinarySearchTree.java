/**
 Refer to
 https://leetcode.com/problems/insert-into-a-binary-search-tree/
 Given the root node of a binary search tree (BST) and a value to be inserted into the tree, 
 insert the value into the BST. Return the root node of the BST after the insertion. It is 
 guaranteed that the new value does not exist in the original BST.

Note that there may exist multiple valid ways for the insertion, as long as the tree remains 
a BST after insertion. You can return any of them.

For example, 
Given the tree:
        4
       / \
      2   7
     / \
    1   3
And the value to insert: 5
You can return this binary search tree:

         4
       /   \
      2     7
     / \   /
    1   3 5
This tree is also valid:

         5
       /   \
      2     7
     / \   
    1   3
         \
          4
*/
// Solution 1: Create a new node and return in base condition
// Refer to
// https://leetcode.com/problems/insert-into-a-binary-search-tree/solution/
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
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // The base condition is not directly return null as normal
        // but create a new node and return it back
        if(root == null) {
            return new TreeNode(val); // Tricky point !
        }
        if(val > root.val) {
            root.right = insertIntoBST(root.right, val);
        } else if(val < root.val) {
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }
}
