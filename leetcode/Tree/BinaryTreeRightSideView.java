/**
 * Refer to
 * https://leetcode.com/problems/binary-tree-right-side-view/description/
 * Given a binary tree, imagine yourself standing on the right side of it, 
   return the values of the nodes you can see ordered from top to bottom.

    For example:
    Given the following binary tree,
       1            <---
     /   \
    2     3         <---
     \     \
      5     4       <---
    You should return [1, 3, 4].
 *
 * Solution
 * https://www.youtube.com/watch?v=obLedSdUSow
 * http://www.cnblogs.com/grandyang/p/4392254.html
 * https://discuss.leetcode.com/topic/11768/my-simple-accepted-solution-java
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

// Solution 1: DFS
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if(root == null) {
            return result;
        }
        helper(root, result, 0);
        return result;
    }
    
    private void helper(TreeNode node, List<Integer> result, int level) {
        if(node == null) {
            return;
        }
        // Each level we only select 1 node (the most-right one),
        // so when result size equal to level number, based on right 
        // node first traverse, we add it into result
        if(result.size() == level) {
            result.add(node.val);
        }
        // Right node first traverse, then left
        if(node.right != null) {
            helper(node.right, result, level + 1);
        }
        if(node.left != null) {
            helper(node.left, result, level + 1);
        }
    }
}



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
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if(root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            // level traverse
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                // Style 1: right -> left
                // i == 0 means the most right node at that level
                if(i == 0) {
                    result.add(node.val);
                }
                // First add right node of next level to queue, then left
                if(node.right != null) {
                    queue.offer(node.right);
                }
                if(node.left != null) {
                    queue.offer(node.left);
                }
                // Style 2: left -> right
                // i == size - 1 means the most right node at that level
                // if(i == size - 1) {
                //     result.add(node.val);
                // }
                // // First add left node of next level to queue, then right
                // if(node.left != null) {
                //     queue.offer(node.left);
                // }
                // if(node.right != null) {
                //     queue.offer(node.right);
                // }
            }
        }
        return result;
    }
}
