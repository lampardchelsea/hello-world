
/**
 * Refer to
 * http://www.lintcode.com/en/problem/reorder-list/
 * Given a singly linked list L: L0 → L1 → … → Ln-1 → Ln
    reorder it to: L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …
    Have you met this question in a real interview? Yes
    Example
    Given 1->2->3->4->null, reorder it to 1->4->2->3->null.
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/reorder-list/
 * https://discuss.leetcode.com/topic/13869/java-solution-with-3-steps
 * https://discuss.leetcode.com/topic/13869/java-solution-with-3-steps/19
 * https://discuss.leetcode.com/topic/335/help-time-limited-exceed/2
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
     * @param head: The head of linked list.
     * @return: void
     */
    public void reorderList(ListNode head) {
        if(head == null || head.next == null) {
            return;
        }
        ListNode mid = findMiddle(head);
        ListNode newStart = mid.next;
        // Must cut off the original list between mid node
        // mid.next node, otherwise will throw
        // Memory Limit Exceeded exception
        // e.g input = 2->-1->0->null
        //     expected = 2->0->-1->null
        // if no 'mid.next = null', -1 and 0 will mutually pointed as a loop
        // Refer to
        // https://discuss.leetcode.com/topic/13869/java-solution-with-3-steps/19
        // https://discuss.leetcode.com/topic/335/help-time-limited-exceed/2
        // http://www.jiuzhang.com/solutions/reorder-list/
        mid.next = null;
        ListNode tail = reverse(newStart);
        merge(head, tail);
    }
    
    private ListNode findMiddle(ListNode head) {
        ListNode walker = head;
        ListNode runner = head;
        while(runner != null && runner.next != null) {
            walker = walker.next;
            runner = runner.next.next;
        }
        return walker;
    }
    
    // Reverse the half after middle  1->2->3->4->5->6 to 1->2->3->6->5->4
    // Same way as Reverse Linked List
    // Refer to
    // http://www.jiuzhang.com/solutions/reverse-linked-list/
    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        while(head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
    
    // Start reorder one by one  1->2->3->6->5->4 to 1->6->2->5->3->4
    // Same way as Merge Two Sorted List
    // Refer to
    // https://github.com/lampardchelsea/hello-world/blob/master/lintcode/LinkedList_Array/VideoExamples/MergeTwoSortedList.java
    private void merge(ListNode head1, ListNode head2) {
        // Create dummy node as we have to reconstruct the node list structure
        ListNode dummy = new ListNode(0);
        int index = 0;
        while(head1 != null && head2 != null) {
            if(index % 2 == 0) {
                dummy.next = head1;
                head1 = head1.next;
            } else {
                dummy.next = head2;
                head2 = head2.next;
            }
            dummy = dummy.next;
            index++;
        }
        if(head1 != null) {
            dummy.next = head1;
        }
        if(head2 != null) {
            dummy.next = head2;
        }
    }
    
}


