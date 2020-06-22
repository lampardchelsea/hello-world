/**
 Refer to
 https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
 Given inorder and postorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

For example, given
inorder = [9,3,15,20,7]
postorder = [9,15,7,20,3]
Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7
*/

// Solution 1: Recursive
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/ConstructBinaryTreeFromPreorderAndInorderTraversal.java
// https://discuss.leetcode.com/topic/3296/my-recursive-java-code-with-o-n-time-and-o-n-space
// https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/discuss/34782/My-recursive-Java-code-with-O(n)-time-and-O(n)-space/154363
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return helper(postorder.length - 1, 0, inorder.length - 1, inorder, postorder);
    }
    
    private TreeNode helper(int rootIndexInPostorder, int inStart, int inEnd, int[] inorder, int[] postorder) {
        if(inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[rootIndexInPostorder]);
        int rootIndexInInorder = 0;
        for(int i = inStart; i <= inEnd; i++) {
            if(inorder[i] == root.val) {
                rootIndexInInorder = i;
            }
        }
        int rightChildNum = inEnd - rootIndexInInorder;
        root.left = helper(rootIndexInPostorder - rightChildNum - 1, inStart, rootIndexInInorder - 1, inorder, postorder);
        root.right = helper(rootIndexInPostorder - 1, rootIndexInInorder + 1, inEnd, inorder, postorder);
        return root;
    }
}

// Solution 2: Recursive with HashMap
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/ConstructBinaryTreeFromPreorderAndInorderTraversal.java
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if(postorder == null || postorder.length == 0) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return helper(postorder.length - 1, 0, inorder.length - 1, postorder, map);
    }
    
    private TreeNode helper(int rootIndexInPostorder, int inStart, int inEnd, int[] postorder, Map<Integer, Integer> map) {
        if(inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[rootIndexInPostorder]);
        int rootIndexInInorder = map.get(root.val);
        int rightChildNum = inEnd - rootIndexInInorder;
        root.left = helper(rootIndexInPostorder - rightChildNum - 1, inStart, rootIndexInInorder - 1, postorder, map);
        root.right = helper(rootIndexInPostorder - 1, rootIndexInInorder + 1, inEnd, postorder, map);
        return root;
    }
}
