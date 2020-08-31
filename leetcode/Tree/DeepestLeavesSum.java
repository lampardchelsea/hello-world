/**
 Refer to
 https://leetcode.com/problems/deepest-leaves-sum/
 Given a binary tree, return the sum of values of its deepest leaves.
 
Example 1:
             1
           2   3
         4  5    6
       7           8

Input: root = [1,2,3,4,5,null,6,7,null,null,null,null,8]
Output: 15
 
Constraints:
The number of nodes in the tree is between 1 and 10^4.
The value of nodes is between 1 and 100.
*/

// Solution 1: Level order traversal
// Refer to
// Maximum Depth Of Binary Tree
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/MaximumDepthOfBinaryTree.java
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
    public int deepestLeavesSum(TreeNode root) {
        int maxDepth = findMaxDepth(root);
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        int level = 0;
        List<TreeNode> list = new ArrayList<TreeNode>();
        while(!q.isEmpty()) {
            level++;
            int size = q.size();
            for(int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if(level == maxDepth) {
                    list.add(node);
                }
                if(node.left != null) {
                    q.offer(node.left);
                }
                if(node.right != null) {
                    q.offer(node.right);
                }
            }
        }
        int result = 0;
        for(TreeNode a : list) {
            result += a.val;
        }
        return result;
    }
    
    int depth = 0;
    private int findMaxDepth(TreeNode node) {
        helper(node, 1);
        return depth;
    }
    
    private void helper(TreeNode node, int curDepth) {
        if(node == null) {
            return;
        }
        if(curDepth > depth) {
            depth = curDepth;
        }
        helper(node.left, curDepth + 1);
        helper(node.right, curDepth + 1);
    }
}
