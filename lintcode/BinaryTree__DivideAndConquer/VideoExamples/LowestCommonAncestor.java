/**
 * Refer to
 * http://www.lintcode.com/en/problem/lowest-common-ancestor/
 * Given the root and two nodes in a Binary Tree. Find the lowest common ancestor(LCA) of the two nodes.
 * The lowest common ancestor is the node with largest depth which is the ancestor of both nodes.
 * Notice
    Assume two nodes are exist in tree.
    Have you met this question in a real interview?
    Example

    For the following binary tree:

      4
     / \
    3   7
       / \
      5   6

    LCA(3, 5) = 4
    LCA(5, 6) = 7
    LCA(6, 7) = 7
 *
 * Solution
 * http://www.jiuzhang.com/solutions/lowest-common-ancestor/
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
public class Solution {
    /**
     * @param root: The root of the binary search tree.
     * @param A and B: two nodes in a Binary.
     * @return: Return the least common ancestor(LCA) of the two nodes.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode A, TreeNode B) {
        // There are 4 cases
        // 1. A & B = LCA
        // 2. !A & !B = null
        // 3. A & !B = A
        // 4. !A & B = B
        // Base case
        if(root == null || root == A || root == B) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, A, B);
        TreeNode right = lowestCommonAncestor(root.right, A, B);
        // Each subtree has one node (A or B)        
        if(left != null && right != null) {
            return root;  // got lca
        }
        // Both A, B in same subtree
        if(left != null) {
            return left;
        }
        if(right != null) {
            return right;
        }
        // The remind case is left == null && right == null
        return null;
    }
}
