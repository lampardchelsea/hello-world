/**
 * Refer to
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/description/
 * Given a binary tree

    struct TreeLinkNode {
      TreeLinkNode *left;
      TreeLinkNode *right;
      TreeLinkNode *next;
    }

    Populate each next pointer to point to its next right node. If there is no next right node, 
    the next pointer should be set to NULL.

    Initially, all next pointers are set to NULL.
    Note:
    You may only use constant extra space.
    You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and 
    every parent has two children).
    
    For example,
    Given the following perfect binary tree,
             1
           /  \
          2    3
         / \  / \
        4  5  6  7
    
    After calling your function, the tree should look like:
             1 -> NULL
           /  \
          2 -> 3 -> NULL
         / \  / \
        4->5->6->7 -> NULL
 *
 * Solution
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/discuss/37461/Java-solution-with-O(1)-memory+-O(n)-time
 * https://www.youtube.com/watch?v=3MFL7L8HnUc&t=233s
*/
/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
// Solution 1: Recursive
/**
   Strategy as pre-order traverse
             1
           /  \
          2    3
         / \  / \
        4  5  6  7
   If 4 has left child, then after connect 5,6 will not connect 
   6,7 directly after that because it will go as pre-order traverse
*/
public class Solution {
    public void connect(TreeLinkNode root) {
        if(root == null) {
            return;
        }
        // No need to add condition as root.right != null
        // since given perfect binary tree
        // if(root.left != null && root.right != null) {
        // To handle connection between 2->3, 4->5, 6->7
        if(root.left != null) {
            root.left.next = root.right;
        }
        // To handle connection between 5->6
        // current root = 2, 2.next = 3, 3.left = 6,
        // 2.right = 5, to connect 5 and 6 use 5.next = 6
        if(root.next != null && root.right != null) {
            root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
    }
}

// Solution 2: Iterative
/**
   Strategy as level order traverse
*/
/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
public class Solution {
    public void connect(TreeLinkNode root) {
        if(root == null) {
            return;
        }
        // 'start' node will record each level's left most node
        TreeLinkNode start = root;
        // Condition based on level exist or not
        while(start != null) {
            // 'cur' node used for traverse current level,
            // start with 'start'
            TreeLinkNode cur = start;
            // Traverse on each level
            while(cur != null) {
                if(cur.left != null) {
                    cur.left.next = cur.right;
                }
                if(cur.next != null && cur.right != null) {
                    cur.right.next = cur.next.left;
                }
                // Move forward on current level
                cur = cur.next;
            }
            // Go to next level
            start = start.left;
        }
    }
}






