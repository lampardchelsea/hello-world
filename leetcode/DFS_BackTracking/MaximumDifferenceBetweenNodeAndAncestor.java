/**
 Refer to
 https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/
 Given the root of a binary tree, find the maximum value V for which there exists different 
 nodes A and B where V = |A.val - B.val| and A is an ancestor of B.

(A node A is an ancestor of B if either: any child of A is equal to B, or any child of A is 
an ancestor of B.)

Example 1:
                  8
            3          10
          1   6           14
            4   7      13
Input: [8,3,10,1,6,null,14,null,null,4,7,13]
Output: 7
Explanation: 
We have various ancestor-node differences, some of which are given below :
|8 - 3| = 5
|3 - 7| = 4
|8 - 1| = 7
|10 - 13| = 3
Among all possible differences, the maximum value of 7 is obtained by |8 - 1| = 7.
 
Note:
The number of nodes in the tree is between 2 and 5000.
Each node will have value between 0 and 100000.
*/
// Solution 1:
// Refer to
// https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/discuss/274610/JavaC%2B%2BPython-Top-Down
// We pass the minimum and maximum values to the children,
// At the leaf node, we return max - min through the path from the root to the leaf.
// And no need to worry about order required by (ancestor - current) because in the final calculation format
// every difference is calculated by |ancestor - current|, which means if 3 is ancestor and 8 is current,
// the difference can by found by |3 - 8| as |current - ancestor|, no order required since absolute
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
    public int maxAncestorDiff(TreeNode root) {
        return helper(root, root.val, root.val);
    }
    
    private int helper(TreeNode root, int min, int max) {
        if(root == null) {
            return max - min;
        }
        min = Math.min(min, root.val);
        max = Math.max(max, root.val);
        return Math.max(helper(root.left, min, max), helper(root.right, min, max));
    }
}

// Solution 2:
// Refer to
// https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/discuss/278183/Java-DFS
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
    int result = 0;
    public int maxAncestorDiff(TreeNode root) {
        helper(root, root.val, root.val);
        return result;
    }
    
    private void helper(TreeNode root, int min, int max) {
        if(root == null) {
            return;
        }
        min = Math.min(min, root.val);
        max = Math.max(max, root.val);
        result = Math.max(result, max - min);
        helper(root.left, min, max);
        helper(root.right, min, max);
    }
}
