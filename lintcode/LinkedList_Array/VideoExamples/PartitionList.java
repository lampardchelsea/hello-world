/**
 * Refer to
 * http://www.lintcode.com/en/problem/partition-list/
 * Given a linked list and a value x, partition it such that all nodes less than x 
 * come before nodes greater than or equal to x.
 * You should preserve the original relative order of the nodes in each of the two partitions.
    Have you met this question in a real interview? Yes
    Example
    Given 1->4->3->2->5->2->null and x = 3,
    return 1->2->2->4->3->5->null.
 *
 * Solution
 * https://leetcode.com/problems/partition-list/description/
 *
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
    /*
     * @param head: The first node of linked list
     * @param x: An integer
     * @return: A ListNode
     */
    public ListNode partition(ListNode head, int x) {
        if(head == null) {
            return null;
        }
        // Use dummy1 and dummy2 as we need to reconstruct the list
        ListNode dummy1 = new ListNode(0);
        // Similar to 'Merge Two Sorted List', in final return
        // we need to keep 'dummy1' and 'dummy2' as original
        // position, so we create two same node reference 'itr1'
        // and 'itr2' which help to traverse and rebuild the list
        ListNode itr1 = dummy1;
        ListNode dummy2 = new ListNode(0);
        ListNode itr2 = dummy2;
        while(head != null) {
            if(head.val < x) {
                itr1.next = head;
                itr1 = head;
            } else {
                itr2.next = head;
                itr2 = head;
            }
            head = head.next;
        }
        // Concatenate two sub lists
        // Fix the end of list as null
        itr2.next = null;
        // Get rid of dummy2
        itr1.next = dummy2.next;
        return dummy1.next;
    }
}
