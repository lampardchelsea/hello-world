/**
 Refer to
 https://leetcode.com/problems/subtree-of-another-tree/
 Given two non-empty binary trees s and t, check whether tree t has exactly the same 
 structure and node values with a subtree of s. A subtree of s is a tree consists of 
 a node in s and all of this node's descendants. The tree s could also be considered 
 as a subtree of itself.

Example 1:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
Given tree t:
   4 
  / \
 1   2
Return true, because t has the same structure and node values with a subtree of s.

Example 2:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
    /
   0
Given tree t:
   4
  / \
 1   2
Return false.
*/
// Wrong solution: looks like should not find the same value node in S ?
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
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null) {
            return false;
        }
        TreeNode node = findTRootInS(s, t.val); // -> logic is wrong here
        if(node != null) {
            return compare(node, t);
        }
        return false;
    }
    
    private TreeNode findTRootInS(TreeNode sRoot, int tRootVal) {
        if(sRoot == null) {
            return null;
        }
        if(sRoot.val == tRootVal) {
            return sRoot;
        } else {
            if(sRoot.val > tRootVal) {
                sRoot = findTRootInS(sRoot.left, tRootVal);
            } else {
                sRoot = findTRootInS(sRoot.right, tRootVal);
            }
        }
        return sRoot;
    }
    
    private boolean compare(TreeNode a, TreeNode b) {
        if(a == null || b == null) {
            return a == b;
        }
        return a.val == b.val 
            && compare(a.left, b.left) 
            && compare(a.right, b.right);
    }
}

// Correct Solution:
// Refer to
// https://leetcode.com/problems/subtree-of-another-tree/discuss/102724/Java-Solution-tree-traversal
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// Refer to
// 700.Search in a Binary Search Tree and 100. Same Tree two problems
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null && t == null) {
            return true;
        }
        if(s == null || t == null) {
            return false;
        }
        if(s.val == t.val && compare(s.left, t.left) && compare(s.right, t.right)) {
            return true;
        }
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
    
    private boolean compare(TreeNode a, TreeNode b) {
        if(a == null || b == null) {
            return a == b;
        }
        return a.val == b.val 
            && compare(a.left, b.left) 
            && compare(a.right, b.right);
    }
}
