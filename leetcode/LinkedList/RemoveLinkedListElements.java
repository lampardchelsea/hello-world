/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
// Refer to
// http://www.programcreek.com/2014/04/leetcode-remove-linked-list-elements-java/
public class Solution {
    public ListNode removeElements(ListNode head, int val) {
        if(head == null) {
            return null;
        }
        // Set up a fakeHead which also check on current head
        ListNode fakeHead = new ListNode(-1);
        // Link fakeHead to head
        fakeHead.next = head;
        // Use iterator to loop through list (Should not only
        // use original fakeHead to loop, the loop pointer
        // must always set up individually)
        ListNode itr = fakeHead;
        while(itr.next != null) {
            if(itr.next.val == val) {
                itr.next = itr.next.next;
            } else {
                itr = itr.next;
            }
        }
        // Important: Should not return head;
        // Refer to
        // https://discuss.leetcode.com/topic/12725/ac-java-solution/2
        // The reason is that head may also need to be removed from the list.
        // which means head may be abandoned... such as head.val == val
        // The test case as ([1], 1) that gave the wrong answer of this kind
        return fakeHead.next;
    }
}
