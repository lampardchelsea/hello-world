/**
 * Refer to
 * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/description/
 * Given a binary tree, return the zigzag level order traversal of its nodes' values. 
   (ie, from left to right, then right to left for the next level and alternate between).

    For example:
    Given binary tree [3,9,20,null,null,15,7],
        3
       / \
      9  20
        /  \
       15   7
    return its zigzag level order traversal as:
    [
      [3],
      [20,9],
      [15,7]
    ]
 *
 * Solution
 * https://discuss.leetcode.com/topic/10157/c-5ms-version-one-queue-and-without-reverse-operation-by-using-size-of-each-level/9
*/
// Solution 1: BFS
// Refer to
// https://discuss.leetcode.com/topic/10157/c-5ms-version-one-queue-and-without-reverse-operation-by-using-size-of-each-level/9
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
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<Integer>();
            for(int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                // For even number rows add from left to right,
                // for odd number rows ad from right to left;
                if(level % 2 == 0) {
                    list.add(node.val);
                } else {
                    list.add(0, node.val);
                }
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
            level += 1;
            result.add(list);
        }
        return result;
    }
}
