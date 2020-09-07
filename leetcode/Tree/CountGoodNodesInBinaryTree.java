/**
 Refer to
 https://leetcode.com/problems/count-good-nodes-in-binary-tree/
 Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes with a value greater than X.

Return the number of good nodes in the binary tree.

Example 1:
                3
            1       4
         3     1       5
         
Input: root = [3,1,4,3,null,1,5]
Output: 4
Explanation: Nodes in blue are good.
Root Node (3) is always a good node.
Node 4 -> (3,4) is the maximum value in the path starting from the root.
Node 5 -> (3,4,5) is the maximum value in the path
Node 3 -> (3,1,3) is the maximum value in the path.

Example 2:
                3
            3
         4     2
         
Input: root = [3,3,null,4,2]
Output: 3
Explanation: Node 2 -> (3, 3, 2) is not good, because "3" is higher than it.

Example 3:
Input: root = [1]
Output: 1
Explanation: Root is considered as good.
 
Constraints:
The number of nodes in the binary tree is in the range [1, 10^5].
Each node's value is between [-10^4, 10^4].
*/

// Solution 1: Record the maximum value along the path from the root to the node.
// Refer to
// https://leetcode.com/problems/count-good-nodes-in-binary-tree/discuss/635258/JavaPython-3-Simple-recursion-w-brief-explanation-and-analysis.
/**
1.Update the maximum value found while recurse down to the paths from root to leaves;
2.If node value >= current maximum, count it in.
3.return the total number after the completion of all recursions.
Time: O(n), space: O(h), where n and h are the number and height of the binary tree, respectively.
*/

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
    public int goodNodes(TreeNode root) {
        return helper(root, root.val);
    }
    
    private int helper(TreeNode root, int val) {
        if(root == null) {
            return 0;
        }
        int result = 0;
        // Record current maximum value on the path
        int max = Math.max(root.val, val);
        // If current node value no less than maximum value record it
        if(root.val >= max) {
            result += 1;
        }
        // Recursion on left and right subtree
        return result + helper(root.left, max) + helper(root.right, max);
    }
}

