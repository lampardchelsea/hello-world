/**
 Refer to
 https://leetcode.com/problems/find-a-corresponding-node-of-a-binary-tree-in-a-clone-of-that-tree/
 Given two binary trees original and cloned and given a reference to a node target in the original tree.
 The cloned tree is a copy of the original tree.
 Return a reference to the same node in the cloned tree.
 Note that you are not allowed to change any of the two trees or the target node and the answer must be 
 a reference to a node in the cloned tree.
 Follow up: Solve the problem if repeated values on the tree are allowed.
 
 Example 1:
            7                      7
          4   3 (target)         4   3
            6   19                 6   19
          original               cloned
          
 Input: tree = [7,4,3,null,null,6,19], target = 3
 Output: 3
 Explanation: In all examples the original and cloned trees are shown. The target node is a green node from 
 the original tree. The answer is the yellow node from the cloned tree.
 
 Example 2:
           7 (target)              7
          original               cloned
 Input: tree = [7], target =  7
 Output: 7
 
 Example 3:
           8                        8
             6                        6
               5                        5
                 4 (target)               4
                   3                        3
                     2                        2
                       1                         1
          original               cloned
 Input: tree = [8,null,6,null,5,null,4,null,3,null,2,null,1], target = 4
 Output: 4
 
 Constraints:
 The number of nodes in the tree is in the range [1, 10^4].
 The values of the nodes of the tree are unique.
 target node is a node from the original tree and is not null.
*/

// Solution 1: DFS + Pre-order traversal
// Refer to
// https://leetcode.com/problems/find-a-corresponding-node-of-a-binary-tree-in-a-clone-of-that-tree/discuss/537728/Java-Simple-Solution/477497\
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
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        return helper(original, cloned, target);
    }
    
    private TreeNode helper(final TreeNode a, final TreeNode b, final TreeNode target) {
        if(a == null) {
            return null;
        }
        // Compare reference instead of value in case of follow up:
        // Solve the problem if repeated values on the tree are allowed.
        if(a == target) {
            return b;
        }
        TreeNode left = helper(a.left, b.left, target);
        if(left != null) {
            return left;
        }
        TreeNode right = helper(a.right, b.right, target);
        if(right != null) {
            return right;
        }
        return null;
    }
}
