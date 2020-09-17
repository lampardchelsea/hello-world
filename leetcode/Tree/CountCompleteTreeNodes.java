/**
 Refer to
 https://leetcode.com/problems/count-complete-tree-nodes/
 Given a complete binary tree, count the number of nodes.
 
 Note:
 Definition of a complete binary tree from Wikipedia:
 In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level 
 are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.

 Example:
 Input: 
     1
    / \
   2   3
  / \  /
 4  5 6

 Output: 6
*/

// Solution 1: BFS
// Runtime: 7 ms, faster than 5.25% of Java online submissions for Count Complete Tree Nodes.
// Memory Usage: 49.2 MB, less than 5.00% of Java online submissions for Count Complete Tree Nodes.
class Solution {
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        int count = 1;
        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            if(node.left != null) {
                count++;
                q.offer(node.left);
            }
            if(node.right != null) {
                count++;
                q.offer(node.right);
            }
        }
        return count;
    }
}

// Solution 2:
// Refer to
// 
