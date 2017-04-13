// Refer to
// https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/#/description
// Given an array where elements are sorted in ascending order, convert it to a height balanced BST.

// Solution 1:
// Refer to
// https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/#/description
// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/#/description
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
    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums == null || nums.length == 0) {
            return null;
        }
        return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
    }
    
    public TreeNode sortedArrayToBSTHelper(int[] nums, int indexStart, int indexEnd) {
        if(indexStart > indexEnd) {
            return null;
        }
        int indexMid = indexStart + (indexEnd - indexStart)/2;
        TreeNode root = new TreeNode(nums[indexStart + (indexEnd - indexStart)/2]);
        TreeNode leftChild = sortedArrayToBSTHelper(nums, indexStart, indexMid - 1);
        TreeNode rightChild = sortedArrayToBSTHelper(nums, indexMid + 1, indexEnd);
        root.left = leftChild;
        root.right = rightChild;
        return root;
    }
}
