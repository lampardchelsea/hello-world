/**
 Refer to
 https://www.lintcode.com/problem/lowest-common-ancestor-iii/description
 Given the root and two nodes in a Binary Tree. Find the lowest common ancestor(LCA) of the two nodes.
 The lowest common ancestor is the node with largest depth which is the ancestor of both nodes.
 Return null if LCA does not exist.

 Example
 Example1
 Input: 
 {4, 3, 7, #, #, 5, 6}
 3 5
 5 6
 6 7 
 5 8
 Output: 
 4
 7
 7
 null
 Explanation:
   4
  / \
 3   7
    / \
   5   6

 LCA(3, 5) = 4
 LCA(5, 6) = 7
 LCA(6, 7) = 7
 LCA(5, 8) = null

 Example2
 Input:
 {1}
 1 1
 Output: 
 1
 Explanation:
 The tree is just a node, whose value is 1.
 Notice
 node A or node B may not exist in tree.
 Each node has a different value
 
 https://hjweds.gitbooks.io/leetcode/lowest-common-ancestor-iii.html
 https://gist.github.com/Lcjc/538709c0c1e15d70efb14d5d93220462
 Lowest Common Ancestor in Binary Tree (not guaranteed in tree)
 Given two nodes in a binary tree, find their lowest common ancestor (the given two nodes are not 
 guaranteed to be in the binary tree).
 Return null If any of the nodes is not in the tree.
 There is no parent pointer for the nodes in the binary tree
 The given two nodes are not guaranteed to be in the binary tree
 Example
        1
      /   \
     2     3
   /  \      \
  4    5      6
 The lowest common ancestor of 2 and 6 is 1
 The lowest common ancestor of 2 and 5 is 2
 The lowest common ancestor of 2 and 7 is null (7 is not in the tree)
*/

// Solution 1: Recursive
// Refer to
// https://gist.github.com/Lcjc/538709c0c1e15d70efb14d5d93220462
/**
 Approach: First handle as normal LCA for Binary Tree
 1. Normal LCA: 
    - case 1: if root is null, return null
    - case 2: if root is one of the nodes, return root
    - case 3: if root's left and right both return a node (means both node found), root is the lca
    - case 4: if only one of the child return a node, keep passing that node back up
 2. If result of Normal LCA is not null and other node than any of two nodes means both two nodes are given range
    and the other node is the lowest common ancestor of these two nodes.
    Else if result of Normal LCA is one of the two nodes:
    - case 1: the other node is under the result node, this case the result node is the lca
    - case 2: the other node is not under the result node, this case the other node is not in tree, no actual lca
*/

// https://hjweds.gitbooks.io/leetcode/lowest-common-ancestor-iii.html
/**
 Solution: 做法与Lowest Common Ancestor I相同，但当返回结果是one或two时，要检查two是否在one的子树里
*/
public class Solution {
    /*
     * @param root: The root of the binary tree.
     * @param A: A TreeNode
     * @param B: A TreeNode
     * @return: Return the LCA of the two nodes.
     */
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode A, TreeNode B) {
        TreeNode candidateLCA = lowestCommonAncestor(root, A, B);
        // If candidate LCA equals node 'A', check if 'A' in subtree as root as 'B'
        if(candidateLCA == A) {
            return find(A, B) ? A : null; 
        }
        // If candidate LCA equals node 'B', check if 'B' in subtree as root as 'A'
        if(candidateLCA == B) {
            return find(B, A) ? B : null;
        }
        // If candidate LCA not equal to 'A' or 'B', return directly
        return candidateLCA;
    }
    
    // Leverage same LCA base method
    private TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) {
            return null;
        }
        if(root.val == p.val || root.val == q.val) {
            return root;
        }
        TreeNode leftSearchResult = lowestCommonAncestor(root.left, p, q);
        TreeNode rightSearchResult = lowestCommonAncestor(root.right, p, q);
        if(leftSearchResult == null) {
            return rightSearchResult;
        }
        if(rightSearchResult == null) {
            return leftSearchResult;
        }
        return root;
    }
    
    // Check if node is under root
    private boolean find(TreeNode root, TreeNode node) {
       if(root == null) {
          return false;
       }
       if(root == node) {
          return true;
       }
       return find(root.left, node) || find(root.right, node);
    }
}
