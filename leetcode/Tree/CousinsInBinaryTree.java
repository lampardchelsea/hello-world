/**
 Refer to
 https://leetcode.com/problems/cousins-in-binary-tree/
 In a binary tree, the root node is at depth 0, and children of each depth k node are at depth k+1.
Two nodes of a binary tree are cousins if they have the same depth, but have different parents.
We are given the root of a binary tree with unique values, and the values x and y of two different nodes in the tree.
Return true if and only if the nodes corresponding to the values x and y are cousins.

Example 1:
        1
     2     3
   4
Input: root = [1,2,3,4], x = 4, y = 3
Output: false

Example 2:
        1
     2     3
       4     5
Input: root = [1,2,3,null,4,null,5], x = 5, y = 4
Output: true

Example 3:
        1
     2     3
       4
Input: root = [1,2,3,null,4], x = 2, y = 3
Output: false

Note:
The number of nodes in the tree will be between 2 and 100.
Each node has a unique integer value from 1 to 100.
*/
// Solution 1: BFS
// Refer to
// https://leetcode.com/problems/cousins-in-binary-tree/discuss/239376/Java-BFS-time-and-space-beat-100
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
    public boolean isCousins(TreeNode root, int x, int y) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                TreeNode parent = queue.poll();
                TreeNode leftChild = parent.left;
                TreeNode rightChild = parent.right;
                if(leftChild != null) {
                    queue.offer(leftChild);
                }
                if(rightChild != null) {
                    queue.offer(rightChild);
                }
                // If two children from same parent node contains both x and y, not cousin
                if(leftChild != null && rightChild != null) {
                    int leftVal = leftChild.val;
                    int rightVal = rightChild.val;
                    if((leftVal == x && rightVal == y) || (leftVal == y && rightVal == x)) {
                        return false;
                    }                    
                }
            }
            // Scan on all nodes (same level) on current queue to find if any pair contains
            // both x and y, if found a pair, then cousin found since already exclude same
            // parent case
            boolean found_x = false;
            boolean found_y = false;
            for(TreeNode node : queue) {
                if(node.val == x) {
                    found_x = true;
                }
                if(node.val == y) {
                    found_y = true;
                }
            }
            if(found_x && found_y) {
                return true;
            }
        }
        return false;
    }
}
