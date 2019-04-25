/**
 Refer to
 https://leetcode.com/problems/convert-bst-to-greater-tree/
 Given a Binary Search Tree (BST), convert it to a Greater Tree such 
 that every key of the original BST is changed to the original key plus 
 sum of all keys greater than the original key in BST.

Example:
Input: The root of a Binary Search Tree like this:
              5
            /   \
           2     13
           
Output: The root of a Greater Tree like this:
             18
            /   \
          20     13
*/
// Solution 1: Reverse in-order traversal
// Refer to
// https://leetcode.com/problems/convert-bst-to-greater-tree/solution/
// https://leetcode.com/problems/convert-bst-to-greater-tree/discuss/100506/Java-Recursive-O(n)-time/152561
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
    int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        if(root == null) {
            return null;
        }
        convertBST(root.right);
        root.val += sum;
        sum = root.val;
        convertBST(root.left);
        return root;
    }
}
