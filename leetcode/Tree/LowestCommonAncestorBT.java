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
 *
 * Solution:
 * https://segmentfault.com/a/1190000003509399
 * 深度优先标记
 * 复杂度
 * 时间 O(h) 空间 O(h) 递归栈空间
 * 思路
 * 我们可以用深度优先搜索，从叶子节点向上，标记子树中出现目标节点的情况。如果子树中有目标节点，标记为那个目标节点，
 * 如果没有，标记为null。显然，如果左子树、右子树都有标记，说明就已经找到最小公共祖先了。如果在根节点为p的左右子树
 * 中找p、q的公共祖先，则必定是p本身。
 * 换个角度，可以这么想：如果一个节点左子树有两个目标节点中的一个，右子树没有，那这个节点肯定不是最小公共祖先。
 * 如果一个节点右子树有两个目标节点中的一个，左子树没有，那这个节点肯定也不是最小公共祖先。只有一个节点正好左子树有，
 * 右子树也有的时候，才是最小公共祖先。
 *
 * http://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/
 * Note: This solution contain a wrong condition as :
        // If either n1 or n2 matches with root's key, report
        // the presence by returning root (Note that if a key is
        // ancestor of other, then the ancestor key becomes LCA
        if (node.data == n1 || node.data == n2)
            return node;
 * The correct version should be 
        if (node == n1 || node == n2)
            return node;
 * Method 2 (Using Single Traversal)
 * The method 1 finds LCA in O(n) time, but requires three tree traversals plus extra spaces for path arrays. 
 * If we assume that the keys n1 and n2 are present in Binary Tree, we can find LCA using single traversal of 
 * Binary Tree and without extra storage for path arrays.
 * The idea is to traverse the tree starting from root. If any of the given keys (n1 and n2) matches with root, 
 * then root is LCA (assuming that both keys are present). If root doesn’t match with any of the keys, we recur 
 * for left and right subtree. The node which has one key present in its left subtree and the other key present 
 * in right subtree is the LCA. If both keys lie in left subtree, then left subtree has LCA also, otherwise LCA 
 * lies in right subtree.
 */
public class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // If either p or q matches with root(NOT root's val), report
        // the presence by returning root (Note that if a key is
        // ancestor of other, then the ancestor root becomes LCA)
        // The wrong condition is below:
        // if(root == null || root.val == p.val || root.val == q.val) {
        //    return root;
        // }
        if(root == null || root == p || root == q) {
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
        // this express current node is LCA because p and q separately exist on left and
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
