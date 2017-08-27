/**
 * Refer to
 * http://www.lintcode.com/en/problem/sort-list/
 *
 * Solution
 * http://www.jiuzhang.com/solutions/sort-list/
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

// Merge Sort Way
public class Solution {
    /*
     * @param head: The head of linked list.
     * @return: You should return the head of the sorted linked list, using constant space complexity.
     */
    private ListNode findMiddle(ListNode head) {
        ListNode walker = head;
        ListNode runner = head;
        // ListNode runner = head.next;
        // If set up runner as 'head.next', the condition of
        // while loop can also change to 
        // while(runner != null && runner.next != null) {...}
        // if set up runner as 'head', condition must be this way
        while(runner.next != null && runner.next.next != null) {
            walker = walker.next;
            runner = runner.next.next;
        }
        return walker;
    }

	private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(0);
        ListNode itr = dummy;
        while(head1 != null && head2 != null) {
            if(head1.val < head2.val) {
                itr.next = head1;
                head1 = head1.next;
            } else {
                itr.next = head2;
                head2 = head2.next;
            }
            itr = itr.next;
        }
        if(head1 != null) {
            itr.next = head1;
        }
        if(head2 != null) {
            itr.next = head2;
        }
        return dummy.next;
    }

    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        // Should not sort first half before cut off at 'mid'
        //ListNode left = sortList(head); --> This is wrong
        // Find middle
        ListNode mid = findMiddle(head);
        ListNode temp = mid.next;
        mid.next = null;
        ListNode right = sortList(temp);
        // Important: After cut off during 'mid', then sort first half
        ListNode left = sortList(head);
        return merge(left, right);
    }

}
