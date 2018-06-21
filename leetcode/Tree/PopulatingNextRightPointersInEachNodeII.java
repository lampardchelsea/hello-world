
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

// Better version
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
        while(root != null) {
            TreeLinkNode cur = root;
            // Need to create dummy node since not sure cur.left exist or not
            TreeLinkNode dummy = new TreeLinkNode(-1);
            TreeLinkNode itr = dummy;
            while(cur != null) {
                if(cur.left != null) {
                    itr.next = cur.left;
                    itr = itr.next;
                }
                if(cur.right != null) {
                    itr.next = cur.right;
                    itr = itr.next;
                }
                cur = cur.next;
            }
            // Move to next level
            /**
             * Why it will move to next level by 'root = dummy.next' ?
             * Refer to
             * leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/discuss/37811/Simple-solution-using-constant-space/35829
             * leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/discuss/37811/Simple-solution-using-constant-space/35823
             * I think the explanation should be, when "TreeLinkNode currentChild = tempChild;" the currentChild is the address of the 
               object, so every change to currentChild is the change to tempChild. So in the following two if(){}..., because of  "currentChild.next = root.right;" or"currentChild.next = root.right;" the tempChild.next is change into the first node of this level.   However, because of "currentChild = currentChild.next;" the currentChild change to the address of another object. So the tempChild will not change with it anymore and it will stay on the first node of this level. I think that's why tmpNode.next is the first node of each level. Am I correct
            */
            root = dummy.next;
        }
    }
}




