/**
 * Refer to
 * https://leetcode.com/problems/add-two-numbers/#/description
 * You are given two non-empty linked lists representing two non-negative integers. 
 * The digits are stored in reverse order and each of their nodes contain a single digit. 
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * 
 * Solution:
 * https://discuss.leetcode.com/topic/799/is-this-algorithm-optimal-or-what/2
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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode itr = dummy;
        // Initial a value to store current position sum value
        int temp = 0;
        // Use '||' as optimal than '&&' to handle separately
        // termination
        while(l1 != null || l2 != null) {
            // Get current position carry_over(in 1st postion
            // as 'temp' initial = 0, will set as 0, but later
            // positions depend on what real sum is)
            temp = temp / 10;
            if(l1 != null) {
                temp += l1.val;
                l1 = l1.next;
            }
            if(l2 != null) {
                temp += l2.val;
                l2 = l2.next;
            }
            itr.next = new ListNode(temp % 10);
            itr = itr.next;
        }
        // Handle the possible additional most significant digit
        if(temp / 10 == 1) {
            itr.next = new ListNode(1);
        }
        return dummy.next;
    }
}
