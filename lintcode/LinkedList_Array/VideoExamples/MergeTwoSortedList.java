/**
 * Refer to 
 * http://www.lintcode.com/en/problem/merge-two-sorted-lists/
 * Merge two sorted (ascending) linked lists and return it as a new sorted list. 
 * The new sorted list should be made by splicing together the nodes of the two 
 * lists and sorted in ascending order.

    Have you met this question in a real interview? Yes
    Example
    Given 1->3->8->11->15->null, 2->null , return 1->2->3->8->11->15->null.
 *
 * Solution
 * http://www.jiuzhang.com/solutions/merge-two-sorted-lists/
*/
/**
 * Definition for ListNode.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int val) {
 *         this.val = val;
 *         this.next = null;
 *     }
 * }
 */ 
public class Solution {
    /**
     * @param ListNode l1 is the head of the linked list
     * @param ListNode l2 is the head of the linked list
     * @return: ListNode head of linked list
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // We need use dummy node because of re-construct the
        // original list
        ListNode dummy = new ListNode(0);
        // Create new reference 'itr' point to 'dummy' as
        // we don't have to keep 'dummy' as original position
        // when we return as 'dummy.next', as we still need
        // to traverse and rebuild the list, we use 'itr'
        // instead of 'dummy' to implement
        ListNode itr = dummy;
        while(l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                itr.next = l1;
                l1 = l1.next;
            } else {
                itr.next = l2;
                l2 = l2.next;
            }
            itr = itr.next;
        }
        if(l1 != null) {
            itr.next = l1;
        }
        if(l2 != null) {
            itr.next = l2;
        }
        return dummy.next;
    }
}
