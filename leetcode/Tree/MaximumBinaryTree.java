/**
 Refer to
 https://leetcode.com/problems/maximum-binary-tree/
 Given an integer array with no duplicates. A maximum tree building on this array is defined as follow:

The root is the maximum number in the array.
The left subtree is the maximum tree constructed from left part subarray divided by the maximum number.
The right subtree is the maximum tree constructed from right part subarray divided by the maximum number.
Construct the maximum tree by the given array and output the root node of this tree.

Example 1:
Input: [3,2,1,6,0,5]
Output: return the tree root node representing the following tree:

      6
    /   \
   3     5
    \    / 
     2  0   
       \
        1
Note:
The size of the given array will be in the range [1,1000].
*/
// Solution 1:
// Refer to
// https://leetcode.com/problems/maximum-binary-tree/solution/
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
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return helper(nums, 0, nums.length);
    }
    
    private TreeNode helper(int[] nums, int i, int j) {
        if(i == j) {
            return null;
        }
        int index = findMaxNumIndexInGivenRange(nums, i, j);
        TreeNode root = new TreeNode(nums[index]);
        root.left = helper(nums, i, index);
        root.right = helper(nums, index + 1, j);
        return root;
    }
    
    private int findMaxNumIndexInGivenRange(int[] nums, int i, int j) {
        int max_i = i;
        for(int m = i; m < j; m++) {
            if(nums[max_i] < nums[m]) {
                max_i = m;
            }
        }
        return max_i;
    }
}
