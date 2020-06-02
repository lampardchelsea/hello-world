/**
 Refer to
 https://leetcode.com/problems/binary-tree-preorder-traversal/
 Given a binary tree, return the preorder traversal of its nodes' values.

 Example:
 Input: [1,null,2,3]
   1
    \
     2
    /
   3
 Output: [1,2,3]
 Follow up: Recursive solution is trivial, could you do it iteratively?
*/

// Solution 1: Preorder, Inorder and Postorder Traversal Iterative Java Solution
// Refer to
// https://leetcode.com/problems/binary-tree-postorder-traversal/discuss/45621/preorder-inorder-and-postorder-traversal-iterative-java-solution
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if(root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode curr = stack.pop(); 
            result.add(curr.val); // a bit different than post order -> result.add(0, curr.val);
            if(curr.right != null) { // a bit different than post order -> post order first push left then right
                stack.push(curr.right); 
            }
            if(curr.left != null) {
                stack.push(curr.left);
            }
        }
        return result;
    }
}
