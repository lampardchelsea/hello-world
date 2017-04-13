
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);
    }
    
    // inorder: left -> root -> right
    // preorder: root -> left -> right
    // E.g 
    // preorder = {1, 2, 4, 8, 5, 3, 6, 7}
    // inorder = {4, 8, 2, 5, 1, 6, 3, 7}
    public TreeNode buildTreeHelper(int[] preorder, int preorderStart, int preorderEnd, int[] inorder, int inorderStart, int inorderEnd, Map<Integer, Integer> map) {
        if(preorderStart > preorderEnd || inorderStart > inorderEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preorderStart]);
        int rootIndexOnInorder = map.get(preorder[preorderStart]);
        TreeNode leftChild = buildTreeHelper(preorder, preorderStart + 1, preorderStart + 1 + (rootIndexOnInorder - inorderStart) - 1, inorder, inorderStart, rootIndexOnInorder - 1, map);
        TreeNode rightChild = buildTreeHelper(preorder, preorderStart + 1 + (rootIndexOnInorder - inorderStart) - 1 + 1, preorderEnd, inorder, rootIndexOnInorder + 1, inorderEnd, map);
        root.left = leftChild;
        root.right = rightChild;
        return root;
    }
}
