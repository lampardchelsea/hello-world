/**
 Refer to
 https://leetcode.com/problems/binary-tree-inorder-traversal/
 Given a binary tree, return the inorder traversal of its nodes' values.
 
 Example:
 Input: [1,null,2,3]
   1
    \
     2
    /
   3
 Output: [1,3,2]
 Follow up: Recursive solution is trivial, could you do it iteratively?
*/

// Solution 1: Preorder, Inorder and Postorder Traversal Iterative Java Solution
// Refer to
// https://leetcode.com/problems/binary-tree-postorder-traversal/discuss/45621/preorder-inorder-and-postorder-traversal-iterative-java-solution
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if(root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while(!stack.isEmpty() || root != null) {
            while(root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            result.add(root.val);
            root = root.right;
        }
        return result;
    }
}

