/**
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between 
 * two nodes v and w as the lowest node in T that has both v and w as descendants (where we allow 
 * a node to be a descendant of itself).”

        _______3______
       /              \
    ___5__          ___1__
   /      \        /      \
   6      _2       0       8
         /  \
         7   4
 * For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3. Another example is LCA 
 * of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
*/
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // If either p or q matches with root's value, report
        // the presence by returning root (Note that if a key is
        // ancestor of other, then the ancestor root becomes LCA)
        if(root == null || root.val == p.val || root.val == q.val) {
            return root;
        }
        
        // The real situation of how LCA exist is only three cases after recursively
        // search left and right subtree and record matching LCA node if exists
        // by return the LCA node as left_lca and right_lca.
        
        // Case 1: left_lca != null && right_lca == null
        // this express LCA in left subtree, and we only need to look for LCA in left 
        // subtree and return node left_lca or null if not found
        // Case 2: left_lca == null && right_lca != null
        // this express LCA in right subtree, and we only need to look for LCA in right
        // subtree and return node right_lca or null if not found
        // Case 3: left_lca != null && right_lca != null
        // this express current node is LCA as p and q separately exist on left and
        // right subtree.
        // Now we need to check Case 3 first, and if not satisfy, then use trinary
        // express to return either node left_lca or right_lca as Case 1 or Case 2
        TreeNode left_lca = lowestCommonAncestor(root.left, p, q);
        TreeNode right_lca = lowestCommonAncestor(root.right, p, q);
        
        // First check Case 3
        if(left_lca != null && right_lca != null) {
            return root;
        }
        
        // Then check Case 1 and 2
        return left_lca != null ? left_lca : right_lca;
    }
}
