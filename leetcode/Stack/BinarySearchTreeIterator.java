/**
 * Refer to
 * https://leetcode.com/problems/binary-search-tree-iterator/description/
 * Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
    Calling next() will return the next smallest number in the BST.
    Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
 * 
 * Solution
 * https://www.youtube.com/watch?v=jTx2FZb1Who
 * http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion/
*/
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

public class BSTIterator {
    // Because require as iterator, must use inorder stack,
    // don't use DFS
    Stack<TreeNode> stack;
    TreeNode cur;
    public BSTIterator(TreeNode root) {
        cur = root;
        stack = new Stack<TreeNode>();
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        if(cur != null || !stack.isEmpty()) {
            return true;
        }
        return false;
    }

    /** @return the next smallest number */
    public int next() {
        while(cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        cur = stack.pop();
        int val = cur.val;
        cur = cur.right;
        return val;
    }
}

/**
 * Your BSTIterator will be called like this:
 * BSTIterator i = new BSTIterator(root);
 * while (i.hasNext()) v[f()] = i.next();
 */
