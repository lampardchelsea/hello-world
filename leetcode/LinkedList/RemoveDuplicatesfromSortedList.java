/**
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 * For example,
 * Given 1->1->2, return 1->2.
 * Given 1->1->2->3->3, return 1->2->3.
 *
 * Refer to
 * https://discuss.leetcode.com/topic/8345/my-pretty-solution-java
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
    public ListNode deleteDuplicates(ListNode head) {
        // As check for duplicates, no need to create fakeHeader,
        // as current header is first to check, not possible as
        // duplicate to remove
        // For fakeHeader setup check on
        // https://leetcode.com/problems/remove-linked-list-elements/?tab=Description
        ListNode iterator = head;
        while(iterator != null) {
            if(iterator.next == null) {
                break;
            } else if(iterator.next.val == iterator.val) {
                iterator.next = iterator.next.next;
            } else {
                iterator = iterator.next;
            }
        }
        return head;
    }
}
