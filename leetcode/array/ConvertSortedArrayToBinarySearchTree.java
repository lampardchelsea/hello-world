// Refer to
// https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/#/description
// Given an array where elements are sorted in ascending order, convert it to a height balanced BST.

// Solution 1: Recursive
// Refer to
// https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/#/description
// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/#/description
// https://discuss.leetcode.com/topic/3158/my-accepted-java-solution/2
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

// Solution 2: Iterative
// Refer to
// https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/discuss/35218/Java-Iterative-Solution/241775
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums == null || nums.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(0);
        Stack<Object> stack = new Stack<Object>();
        stack.push(nums.length - 1);
        stack.push(0);
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode node = (TreeNode)stack.pop();
            int start = (int)stack.pop();
            int end = (int)stack.pop();
            int mid = start + (end - start) / 2;
            node.val = nums[mid];
            if(mid - 1 >= start) {
                node.left = new TreeNode(0);
                stack.push(mid - 1);
                stack.push(start);
                stack.push(node.left);
            }
            if(mid + 1 <= end) {
                node.right = new TreeNode(0);
                stack.push(end);
                stack.push(mid + 1);
                stack.push(node.right);
            }
        }
        return root;
    }
}



