/**
 Refer to
 https://leetcode.com/problems/check-completeness-of-a-binary-tree/
 Given a binary tree, determine if it is a complete binary tree.

Definition of a complete binary tree from Wikipedia:
In a complete binary tree every level, except possibly the last, is completely filled, 
and all nodes in the last level are as far left as possible. It can have between 1 and 
2h nodes inclusive at the last level h.

Example 1:
           1
       2       3
     4   5   6      

Input: [1,2,3,4,5,6]
Output: true
Explanation: Every level before the last is full (ie. levels with node-values {1} and {2, 3}), 
and all nodes in the last level ({4, 5, 6}) are as far left as possible.

Example 2:
           1
       2       3 
     4   5       7

Input: [1,2,3,4,5,null,7]
Output: false
Explanation: The node with value 7 isn't as far left as possible.
 
Note:
The tree will have between 1 and 100 nodes.
*/
// Solution 1: Java easy Level-Order Traversal, one while-loop
// Refer to
// https://leetcode.com/problems/check-completeness-of-a-binary-tree/discuss/205768/Java-easy-Level-Order-Traversal-one-while-loop
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
    // When level-order traversal in a complete tree, after the last node, 
    // all nodes in the queue should be null. Otherwise, the tree is not complete.
    public boolean isCompleteTree(TreeNode root) {
        if(root == null) {
            return true;
        }
        boolean lastNode = false;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            // Now as we encounter a node as null, we can recognize its the first null
            // after the last node
            if(node == null) {
                lastNode = true;
            } else {
                // If after last node we still able to find node not null, the tree is
                // not complete
                if(lastNode) {
                    return false;
                }
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        return true;
    }
}

// Solution 2: Java easy Level-Order Traversal, one while-loop
// Refer to
// https://www.cnblogs.com/Dylan-Java-NYC/p/11144993.html
/**
 Perform level order traversal on the tree, if popped node is not null, then add its left and right child, no matter if they are null or not.
 If the tree is complete, when first met null in the queue, then the rest should all be null. Otherwise, it is not complete.
 Time Complexity: O(n).
 Space: O(n).
 类似 Complete Binary Tree Inserter.
*/
class Solution {
    public boolean isCompleteTree(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<TreeNode>();
        que.add(root);
        while(que.peek() != null){
            TreeNode cur = que.poll();
            que.add(cur.left);
            que.add(cur.right);
        }
        while(!que.isEmpty() && que.peek() == null){
            que.poll();
        }
        return que.isEmpty();
    }
}
