/**
 * Refer to
 * http://www.lintcode.com/en/problem/rotate-list/
 * Given a list, rotate the list to the right by k places, where k is non-negative.
  Have you met this question in a real interview? Yes
  Example
  Given 1->2->3->4->5 and k = 2, return 4->5->1->2->3.
 *
 * Solution
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/RotateLinkedList.java
*/
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param head: the List
     * @param k: rotate to the right k places
     * @return: the list after rotation
     */
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int len = 0;
        ListNode tail = dummy;
        // Careful: Must use 'tail.next' as condition to calculate length
        while(tail.next != null) {
            tail = tail.next;
            len++;
        }
        if(k % len == 0) {
            return head;
        } else {
            ListNode newTail = dummy;
            for(int i = 0; i < len - k % len; i++) {
                newTail = newTail.next;
            }
            ListNode newHead = newTail.next;
            newTail.next = null;
            tail.next = dummy.next;
            return newHead;
        }
    }
}
