/**
 * Refer to
 * https://leetcode.com/problems/find-mode-in-binary-search-tree/description/
 * Given a binary search tree (BST) with duplicates, find all the mode(s) (the most frequently occurred element) in the given BST.

    Assume a BST is defined as follows:

    The left subtree of a node contains only nodes with keys less than or equal to the node's key.
    The right subtree of a node contains only nodes with keys greater than or equal to the node's key.
    Both the left and right subtrees must also be binary search trees.
    For example:
    Given BST [1,null,2,2],
       1
        \
         2
        /
       2
    return [2].

    Note: If a tree has more than one mode, you can return them in any order.

    Follow up: Could you do that without using any extra space? (Assume that the implicit stack space incurred due to 
    recursion does not count).
 * 
 * Solution
 * https://leetcode.com/problems/find-mode-in-binary-search-tree/discuss/130034/Java-Clear-Code-using-Map-and-Inorder-Traversal
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
class Solution {
    Map<Integer, Integer> valToFreq;

    public int[] findMode(TreeNode root) {
        valToFreq = new HashMap<>();
        inorder(root);
        int maxFreq = 0;

        for (int k : valToFreq.keySet()) {
            if (valToFreq.get(k) > maxFreq) {
                maxFreq = valToFreq.get(k);
            }
        }
        List<Integer> modeList = new ArrayList<>();
        for (int k : valToFreq.keySet()) {
            if (valToFreq.get(k) == maxFreq) {
                modeList.add(k);
            }
        }
        int[] modes = new int[modeList.size()];
        int mi = 0;
        for (int m : modeList) {
            modes[mi++] = m;
        }
        return modes;
    }

    private void inorder(TreeNode root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        valToFreq.put(root.val, valToFreq.getOrDefault(root.val, 0) + 1);
        inorder(root.right);
    }
}
