/**
 * Refer to 
 * http://www.lintcode.com/en/problem/binary-tree-preorder-traversal/
 * Given a binary tree, return the preorder traversal of its nodes' values.
 * Have you met this question in a real interview?
   Example
   Given:
        1
       / \
      2   3
     / \
    4   5
   return [1,2,4,5,3].
 *
 * Solution
 * http://www.jiuzhang.com/solutions/binary-tree-preorder-traversal/
*/
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
// Solution 1: Traverse
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Preorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        // Traverse
        ArrayList<Integer> result = new ArrayList<Integer>();
        traverse(result, root);
        return result;
    }
    
    public void traverse(ArrayList<Integer> list, TreeNode node) {
        // Base case
        if(node == null) {
            return;
        }
        list.add(node.val);
        // Call helper method itself recursively
        traverse(list, node.left);
        traverse(list, node.right);
    }
}
