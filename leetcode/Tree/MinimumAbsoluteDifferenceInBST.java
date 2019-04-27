/**
 Refer to
 https://leetcode.com/problems/minimum-absolute-difference-in-bst/
 Given a binary search tree with non-negative values, find the minimum absolute difference between values of any two nodes.

Example:

Input:

   1
    \
     3
    /
   2

Output:
1

Explanation:
The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).

Note: There are at least two nodes in this BST.
*/
// Solution 1: Store previous node and in-order traverse on BST since it gurantee the ascending sorted order already
// Refer to
// https://leetcode.com/problems/minimum-absolute-difference-in-bst/discuss/99905/Two-Solutions-in-order-traversal-and-a-more-general-way-using-TreeSet
// The most common idea is to first inOrder traverse the tree and compare the delta between each of the adjacent values. 
// It's guaranteed to have the correct answer because it is a BST thus inOrder traversal values are sorted.
// Time complexity O(N), space complexity O(1).
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
    int min = Integer.MAX_VALUE;
    TreeNode prev = null;
    public int getMinimumDifference(TreeNode root) {
        if(root == null) {
            return min;
        }
        getMinimumDifference(root.left);
        //prev = root.left;
        if(prev != null && min > root.val - prev.val) {
            min = root.val - prev.val;
        }
        prev = root;
        getMinimumDifference(root.right);
        return min;
    }
}

// Solution 2: 
