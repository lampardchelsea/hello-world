/**
 Refer to
 https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/
 Given a binary tree, each node has value 0 or 1.  Each root-to-leaf path represents a binary number starting with the most 
 significant bit.  For example, if the path is 0 -> 1 -> 1 -> 0 -> 1, then this could represent 01101 in binary, which is 13.
 For all leaves in the tree, consider the numbers represented by the path from the root to that leaf.
 Return the sum of these numbers.
 
 Example 1:
           1
         0   1
       0  1 0  1

 Input: [1,0,1,0,1,0,1]
 Output: 22
 Explanation: (100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22

 Note:
 The number of nodes in the tree is between 1 and 1000.
 node.val is 0 or 1.
 The answer will not exceed 2^31 - 1.
*/

// Solution 1: Recursive Solution (Pre-order traversal)
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/SumRootToLeafNumbers.java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int sumRootToLeaf(TreeNode root) {
        return helper(root, 0);   
    }
    
    private int helper(TreeNode node, int currSum) {
        if(node == null) {
            return 0;
        }
        currSum = currSum * 2 + node.val;
        if(node.left == null && node.right == null) {
            return currSum;
        }
        int leftSum = helper(node.left, currSum);
        int rightSum = helper(node.right, currSum);
        return leftSum + rightSum;
    }
}

// Solution 2: 
