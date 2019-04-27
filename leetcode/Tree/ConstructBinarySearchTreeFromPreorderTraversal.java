/**
 Refer to
 https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
 Return the root node of a binary search tree that matches the given preorder traversal.

(Recall that a binary search tree is a binary tree where for every node, any descendant of 
node.left has a value < node.val, and any descendant of node.right has a value > node.val.  
Also recall that a preorder traversal displays the value of the node first, then traverses 
node.left, then traverses node.right.)

Example 1:
Input: [8,5,1,7,10,12]
Output: [8,5,10,1,7,null,12]
                8
           5         10
        1     7          12

Note:
1 <= preorder.length <= 100
The values of preorder are distinct.
*/
// Solution:
// Refer to
// https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/252232/JavaC++Python-O(N)-Solution/248260
// https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/252232/JavaC%2B%2BPython-O(N)-Solution
/**
 Give the function a bound the maximum number it will handle.
The left recursion will take the elements smaller than node.val
The right recursion will take the remaining elements smaller than bound
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
    int nodeIdx = 0;
    public TreeNode bstFromPreorder(int[] preorder) {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        return helper(preorder, min, max);
    }
    
    private TreeNode helper(int[] preorder, int min, int max) {
        if(nodeIdx == preorder.length || preorder[nodeIdx] < min || preorder[nodeIdx] > max) {
            return null;
        }
        int val = preorder[nodeIdx++];
        TreeNode root = new TreeNode(val);
        root.left = helper(preorder, min, val);
        root.right = helper(preorder, val, max);
        return root;
    }
}
