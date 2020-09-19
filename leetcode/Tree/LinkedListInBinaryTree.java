/**
 Refer to
 https://leetcode.com/problems/linked-list-in-binary-tree/
 Given a binary tree root and a linked list with head as the first node. 
 Return True if all the elements in the linked list starting from the head correspond to some downward path connected 
 in the binary tree otherwise return False.
 
 In this context downward path means a path that starts at some node and goes downwards.
 
 Example 1:
 Input: head = [4,2,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
 Output: true
 Explanation: Nodes in blue form a subpath in the binary Tree.  
 
 Example 2:
 Input: head = [1,4,2,6], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
 Output: true
 
 Example 3:
 Input: head = [1,4,2,6,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
 Output: false
 Explanation: There is no path in the binary tree that contains all the elements of the linked list from head.
 
 Constraints:
 1 <= node.val <= 100 for each node in the linked list and binary tree.
 The given linked list will contain between 1 and 100 nodes.
 The given binary tree will contain between 1 and 2500 nodes.
*/

// Solution 1: Recursive, exactly the same as "572. Subtree of Another Tree"
// Refer to
// https://leetcode.com/problems/linked-list-in-binary-tree/discuss/524881/Python-Recursive-Solution-O(N)-Time
/**
Solution 1: Brute DFS
Time O(N * min(L,H))
Space O(H)
where N = tree size, H = tree height, L = list length.
*/
// https://leetcode.com/problems/linked-list-in-binary-tree/discuss/630094/Java-recursive-DFS-beats-100-time-and-space-easy-solution
// https://leetcode.com/problems/linked-list-in-binary-tree/discuss/525249/java-exactly-the-same-as-%22572.-Subtree-of-Another-Tree%22
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isSubPath(ListNode head, TreeNode root) {
        ListNode iter = head;
        if(head == null) {
            return true;
        }
        if(root == null) {
            return false;
        }
        // First section: helper(iter, root) --> Check if a downward path is 
        // found starting from the root node.
        // Second and third section: --> If path is not found, check in the 
        // left and right subtrees.
        return helper(iter, root) || isSubPath(head, root.left) || isSubPath(head, root.right);
    }
    
    private boolean helper(ListNode iter, TreeNode node) {
        // If the iter is null, we've reached the end of the list where all 
        // values match the ones in the tree. This means we've found a path.
        if(iter == null) {
            return true;
        }
        // If the TreeNode is null, but the iter is not, we've reached 
        // the end of the subtree but not the list, so, return False.
        if(node == null) {
            return false;
        }
        // If the value of the current tree node not matches the next node of the list, 
        // return false, otherwise continue to find a path in the left or right subtree
        if(iter.val != node.val) {
            return false;
        }
        boolean left = helper(iter.next, node.left);
        boolean right = helper(iter.next, node.right);
        return left || right;
    }
}
