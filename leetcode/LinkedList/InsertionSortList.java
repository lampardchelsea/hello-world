/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode insertionSortList(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        // Set up dummy head
        ListNode dummy = new ListNode(-1);
        // Insert node between pre and pre.next
        ListNode pre = dummy;
        // 'curr' is the node will be inserted
        ListNode curr = head;
        // 'next' is the next node will be inserted
        ListNode next = null;
        // Not the end of input list
        while(curr != null) {
            // Find node need to insert in next loop
            next = curr.next;
            // Find the right place to insert
            while(pre.next != null && pre.next.val < curr.val) {
                pre = pre.next;
            }
            // Insert 'curr' between pre and pre.next
            curr.next = pre.next;
            pre.next = curr;
            // Reset 'pre' to dummy for next loop insert
            pre = dummy;
            // Update 'curr' to 'next' that need to insert in next loop
            curr = next;
        }
        return dummy.next;
    }
}

// Better Solution
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode insertionSortList(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        // Set up dummy head
        ListNode dummy = new ListNode(-1);
        // Insert node between pre and pre.next
        ListNode pre = dummy;
        // 'curr' is the node will be inserted
        ListNode curr = head;
        // 'next' is the next node will be inserted
        ListNode next = null;
        // Not the end of input list
        while(curr != null) {
            // Find node need to insert in next loop
            next = curr.next;
            // Refer to
            // https://discuss.leetcode.com/topic/8570/an-easy-and-clear-way-to-sort-o-1-space/23
            // Before insert, the 'pre' is at the last node of the sorted list. 
            // Only the last node's value is larger than the current inserting node 
            // should we move the temp back to the head
            if(pre.val > curr.val) {
            	pre = dummy;
            }
            // Find the right place to insert
            while(pre.next != null && pre.next.val < curr.val) {
                pre = pre.next;
            }
            // Insert 'curr' between pre and pre.next
            curr.next = pre.next;
            pre.next = curr;
            // Reset 'pre' to dummy for next loop insert
            //pre = dummy;
            // Update 'curr' to 'next' that need to insert in next loop
            curr = next;
        }
        return dummy.next;
    }
}
