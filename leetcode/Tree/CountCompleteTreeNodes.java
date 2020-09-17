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

// Solution 1: Brutal force BFS
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

// Solution 2: Brutal force DFS
// Refer to
// https://www.cnblogs.com/grandyang/p/4567827.html
/**
 其实这道题的最暴力的解法就是直接用递归来统计结点的个数，根本不需要考虑什么完全二叉树还是完美二叉树，递归在手，遇 tree 不愁。
 直接一行搞定碉堡了，这可能是我见过最简洁的 brute force 的解法了吧
 Runtime: 0 ms, faster than 100.00% of Java online submissions for Count Complete Tree Nodes.
 Memory Usage: 42 MB, less than 68.99% of Java online submissions for Count Complete Tree Nodes.
*/
class Solution {
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return helper(root);
    }
    
    private int helper(TreeNode node) {
        if(node == null) {
            return 0;
        }
        int left = helper(node.left);
        int right = helper(node.right);
        return 1 + left + right;
    }
}

// Solution 3:
