
/**
 * Refer to
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/description/
 * Follow up for problem "Populating Next Right Pointers in Each Node".

    What if the given tree could be any binary tree? Would your previous solution still work?

    Note:

    You may only use constant extra space.
    For example,
    Given the following binary tree,
             1
           /  \
          2    3
         / \    \
        4   5    7
    After calling your function, the tree should look like:
             1 -> NULL
           /  \
          2 -> 3 -> NULL
         / \    \
        4-> 5 -> 7 -> NULL

 *
 * Solution
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/discuss/37828/O(1)-space-O(n)-complexity-Iterative-Solution
 * https://www.youtube.com/watch?v=4IRLLPnxc_Q
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
        TreeLinkNode prev = null;  // The leading node on the next level
        TreeLinkNode head = null;  // Head of next level
        TreeLinkNode cur = root;   // Current node of current level 
        while(cur != null) {
            // Iterate on current level
            while(cur != null) {
                if(cur.left != null) {
                    if(prev != null) {
                        prev.next = cur.left;
                    } else {
                        head = cur.left;
                    }
                    prev = cur.left;
                }
                if(cur.right != null) {
                    if(prev != null) {
                        prev.next = cur.right;
                    } else {
                        head = cur.right;
                    }
                    prev = cur.right;
                }
                // Move to next node
                cur = cur.next;
            }
            // Move to next level and must reset next level two pointers
            // otherwise will cause dead loop
            cur = head;
            prev = null;
            head = null;
        }
    }
}
