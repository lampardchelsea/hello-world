/**
 Refer to
 https://leetcode.com/problems/leaf-similar-trees/
 Consider all the leaves of a binary tree.  From left to right order, 
 the values of those leaves form a leaf value sequence.
                   3
              5         1
            6   2     9   8  
              7   4
 For example, in the given tree above, the leaf value sequence is (6, 7, 4, 9, 8).

Two binary trees are considered leaf-similar if their leaf value sequence is the same.

Return true if and only if the two given trees with head nodes root1 and root2 are leaf-similar.

Note:
Both of the given trees will have between 1 and 100 nodes.
*/
// Solution 1: Traverse tree (DFS)
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
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> leaves1 = new ArrayList();
        List<Integer> leaves2 = new ArrayList();
        dfs(root1, leaves1);
        dfs(root2, leaves2);
        return leaves1.equals(leaves2);
    }
    
    public void dfs(TreeNode node, List<Integer> leafValues) {
        if(node == null) {
            return;
        }
        if(node.left == null && node.right == null) {
            leafValues.add(node.val);
        }
        dfs(node.left, leafValues);
        dfs(node.right, leafValues);
    }
}
