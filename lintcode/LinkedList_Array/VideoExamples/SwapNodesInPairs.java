/**
 * Refer to
 * http://www.lintcode.com/en/problem/swap-nodes-in-pairs/
 * Given a linked list, swap every two adjacent nodes and return its head.
    Have you met this question in a real interview? Yes
    Example
    Given 1->2->3->4, you should return the list as 2->1->4->3.
 *
 * Solution 
 * http://www.jiuzhang.com/solutions/swap-nodes-in-pairs/
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    /*
     * @param head: a ListNode
     * @return: a ListNode
     */
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode itr = dummy;
        while(itr.next != null && itr.next.next != null) {
            ListNode n1 = itr.next;
            ListNode n2 = itr.next.next;
            // itr -> n1 -> n2 -> n2.next
            // => itr -> n2 -> n1 -> n2.next
            itr.next = n2;
            n1.next = n2.next;
            n2.next = n1;
            itr = n1;
        }
        return dummy.next;
    }
}
